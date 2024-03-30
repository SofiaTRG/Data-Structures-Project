public class MinTree {
    private NodeFloat root;
    private float minNodeFloat;

    // Init tree
    public MinTree() {
        this.root = new NodeFloat(Float.MAX_VALUE, null, new NodeFloat(Float.MIN_VALUE),new NodeFloat(Float.MAX_VALUE), null );
        this.root.getLeftChild().setParent(root);
        this.root.getMiddleChild().setParent(root);
        this.root.getLeftChild().setKey(Float.MIN_VALUE);
        this.root.getMiddleChild().setKey(Float.MAX_VALUE);

        //minNodeFloat = Float.MAX_VALUE;
    }

    public NodeFloat find(float time) {
        NodeFloat current = root;
        while (current != null) {
            if (current.getKey() == time) {
                return current;
            }
            if (current.getLeftChild()==null) {
                return null;
            }
            else if (time <= current.getLeftChild().getKey()) {
                current=current.getLeftChild();
            } else if (time <= current.getMiddleChild().getKey()) {
                current=current.getMiddleChild();
            } else {
                current=current.getRightChild();
            }

//                current = current.getKey() < time ? current.getRightChild() : current.getLeftChild();
        }
        return null; //should return exception?
    }

    public NodeFloat findNode(NodeFloat root, float time) {
        if (root.getLeftChild()== null ) {
            if (root.getKey() == time) {
                return root;
            }
            else return null;
        } if (time<=root.getLeftChild().getKey()) {
            return findNode(root.getLeftChild(),time);
        } else if (time<=root.getMiddleChild().getKey()) {
            return findNode(root.getMiddleChild(),time);
        } else return findNode(root.getRightChild(),time);
    }

    public NodeFloat getRoot(){
        return this.root;
    }

    public boolean isEmpty(){
        if(this.root.getLeftChild().getKey() == Integer.MIN_VALUE &&
                this.root.getMiddleChild().getKey() == Integer.MAX_VALUE){return true;}
        return false;
    }

    /**
     * update the key of current parent to be the max key of his children
     * @param parent
     */
    // think about away to update minNodeFloat
    private void UpdateKey(NodeFloat parent){
        if (parent.getMiddleChild() == null) {
            parent.setKey(parent.getLeftChild().getKey());
            return;
        } //assuming we call the function not on a leaf
        if(parent.getRightChild() == null){
            parent.setKey(parent.getMiddleChild().getKey());
            return;
        }
        parent.setKey(parent.getRightChild().getKey());
    }


    /**
     * set the children received as parameters to be children of the parent(also parameter) in order
     * @param parent
     * @param left
     * @param middle
     * @param right
     */
    private void SetChildren(NodeFloat parent, NodeFloat left, NodeFloat middle, NodeFloat right){
        parent.setLeftChild(left);
        parent.setMiddleChild(middle);
        parent.setRightChild(right);
        if(left != null) left.setParent(parent);
        if(middle != null) middle.setParent(parent);
        if(right != null) right.setParent(parent);
        UpdateKey(parent);
    }

    /**
     * activated when a split is needed.
     * creates a new parent node and splits the children between the two parents
     * @param parent
     * @param newChild
     * @return new parent node
     */
    private NodeFloat Split(NodeFloat parent, NodeFloat newChild){
        NodeFloat splitNode = new NodeFloat(0);//TODO:CHECK
        if(newChild.getKey() < parent.getLeftChild().getKey()) {
            // need to insert child to leftChild and shift everyone right
            SetChildren(splitNode, parent.getMiddleChild(), parent.getRightChild(),null);
            SetChildren(parent,newChild, parent.getLeftChild(),null);
        }
        else if(newChild.getKey() < parent.getMiddleChild().getKey()) {
            //insert second and shift others right
            SetChildren(splitNode, parent.getMiddleChild(), parent.getRightChild(),null);
            SetChildren(parent, parent.getLeftChild(),newChild,null);
        } else if(newChild.getKey() < parent.getRightChild().getKey()) {
            //if we are in the split then the parent has three children - we can check right child
            // insert third and shift one right
            SetChildren(splitNode, newChild, parent.getRightChild(), null);
            SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(), null);

        }else{
            // insert rightest
            SetChildren(splitNode, parent.getRightChild(),newChild,null);
            SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(),null);
        }
        return splitNode;
    }

    /// CHECK
    /**
     * activated after the node's destination is found
     * inserts the node or calls split if the current parent node is full.
     * @param parent
     * @param newChild
     * @return new node created after spilt or null if there was no split
     */
    private NodeFloat Insert_And_Split(NodeFloat parent, NodeFloat newChild){
        if (parent.getParent() !=null){
            parent= parent.getParent();
        }
        if(parent.getRightChild() == null){ // can insert without split
            if(newChild.getKey() < parent.getLeftChild().getKey()) {
                SetChildren(parent,newChild, parent.getLeftChild(), parent.getMiddleChild());
                return null;
            }
            if(parent.getMiddleChild() == null){
                SetChildren(parent, parent.getLeftChild(),newChild,null);
                return null;
            }
            if(newChild.getKey() < parent.getMiddleChild().getKey() && parent.getRightChild() == null) {
                SetChildren(parent, parent.getLeftChild(),newChild, parent.getMiddleChild());
                return null;
            }
            // לבדוק את התנאי
            else {
                SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(), newChild);
                return null;
            }
        }
        // No condition was met so far - Need a split since tree is full
        return Split(parent,newChild);
    }


    /// CHECK
    /**
     * finds the place in the tree where node should be inserted
     * calls Insert_And_Split() to insert the node there
     * updates the keys throughout the tree after insertion
     * @param
     */
    public void Insert(RunnerID ID, float key){
//            NodeFloat x= SearchTmin(key,root);
        NodeFloat x= findNode(root, key);
        if(x!=null)
            x.getTree().Insert(new Node<>(ID,key));

        else {
            if (this.root.getLeftChild() == null) {
                TwoThreeTree<RunnerID> tree=new TwoThreeTree<RunnerID>();
                tree.Insert(new Node<>(ID));
                this.root.setLeftChild(new NodeFloat(key,tree));
                return;
            } //in case we have an empty tree
            NodeFloat temp = this.root;
            while (temp.getLeftChild() != null) { //stop when found a leaf
                if (key < temp.getLeftChild().getKey()) {
                    temp = temp.getLeftChild();
                } else if (key < temp.getMiddleChild().getKey()) {
                    temp = temp.getMiddleChild();
                } else {
                    temp = temp.getRightChild();
                }
            }

            NodeFloat parentSave = temp.getParent();
            //check (20, inf)
            TwoThreeTree<RunnerID> tree=new TwoThreeTree<RunnerID>();
            tree.Insert(new Node<>(ID));
            NodeFloat newNode = Insert_And_Split(temp, new NodeFloat(key,tree)); // found place in tree - Insert
            while (parentSave != this.root) { //update the keys of the tree
                temp = parentSave.getParent();
                if (newNode != null) {
                    newNode = Insert_And_Split(parentSave, newNode);
                }
                UpdateKey(parentSave);
                parentSave = temp;
            }
            if (newNode != null) { //need a new root
                NodeFloat newRoot = new NodeFloat(0);//TODO:CHECK
                SetChildren(newRoot, parentSave, newNode, null);
                this.root = newRoot;
            }
        }
    }


    /// CHECK
    /**
     * removes a node from the tree - removes link to the parent and link from the parent
     * @param node
     */
    private void Remove(NodeFloat node){
        NodeFloat parent = node.getParent();
        if(node == parent.getLeftChild()){
            SetChildren(parent, parent.getMiddleChild(), parent.getRightChild(), null);
            return;
        }
        if(node == parent.getMiddleChild()){
            SetChildren(parent, parent.getLeftChild(), parent.getRightChild(), null);
            return;
        }
        SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(), null);
    }

    private NodeFloat BorrowOrMerge(NodeFloat node){
        NodeFloat parent = node.getParent();
        if(node == parent.getLeftChild()){
            if(parent.getMiddleChild().getRightChild() != null){
                SetChildren(node, node.getLeftChild(), parent.getMiddleChild().getLeftChild(), null);
                SetChildren(parent.getMiddleChild(), parent.getMiddleChild().getMiddleChild(), parent.getMiddleChild().getRightChild(), null);
            } else {
                SetChildren(parent.getMiddleChild(), node.getLeftChild(), parent.getMiddleChild().getLeftChild(), parent.getMiddleChild().getMiddleChild());
                Remove(node);
            }
        } else if(node == parent.getMiddleChild()){
            if(parent.getLeftChild().getRightChild() != null){
                SetChildren(node, parent.getLeftChild().getRightChild(), node.getLeftChild(), null);
                SetChildren(parent.getLeftChild(), parent.getLeftChild().getLeftChild(), parent.getLeftChild().getMiddleChild(), null);
            } else {
                SetChildren(parent.getLeftChild(), parent.getLeftChild().getLeftChild(), parent.getLeftChild().getMiddleChild(), node.getLeftChild());
                Remove(node);
            }
        } else { // node is right child
            if(parent.getMiddleChild().getRightChild() != null){
                SetChildren(node, parent.getMiddleChild().getRightChild(), node.getLeftChild(), null);
                SetChildren(parent.getMiddleChild(), parent.getMiddleChild().getLeftChild(), parent.getMiddleChild().getMiddleChild(), null);
            } else {
                SetChildren(parent.getMiddleChild(), parent.getMiddleChild().getLeftChild(), parent.getMiddleChild().getMiddleChild(), node.getLeftChild());
                Remove(node);
            }
        }
        UpdateKey(parent.getLeftChild());
        if(parent.getMiddleChild() != null) {
            UpdateKey(parent.getMiddleChild());
        }
        if(parent.getRightChild() != null) {
            UpdateKey(parent.getRightChild());
        }
        return node;
    }


    /**
     * working under the assumption that only leaves are getting deleted
     * @param
     */
    public void Delete(RunnerID ID, float key){ //TODO: CHECK IF WORKING
//            NodeFloat x= SearchTmin(key,root);
        NodeFloat x= findNode(root, key);
        if(x!=null){
            if(x.getTree().getNumberOfLeaves()<=1) {
                NodeFloat parent = x.getParent();
                if (parent.getRightChild() != null) {
                    if (parent.getLeftChild() == x) {
                        SetChildren(parent, parent.getMiddleChild(), parent.getRightChild(), null);
                    } else if (parent.getMiddleChild() == x) {
                        SetChildren(parent, parent.getLeftChild(), parent.getRightChild(), null);
                    } else {// node is right child
                        SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(), null);
                    }
                    x.setParent(null); // Deleting node from the tree, assuming node is a leaf
                }
                // Can't rearrange. Need to borrow or merge. Delete node first
                else if (parent.getLeftChild() == x) {
                    SetChildren(parent, parent.getMiddleChild(), null, null);
                } else {
                    SetChildren(parent, parent.getLeftChild(), null, null);
                }

                // Updating up the tree
                while (parent != null) {
                    if (parent.getMiddleChild() == null) { // We need a Borrow/Merge
                        if (parent != this.root) {
                            parent = BorrowOrMerge(parent);
                        } else {
                            this.root = parent.getLeftChild();
                            this.root.setParent(null);
                            parent.setLeftChild(null);
                            parent = this.root;
                        }
                    }
                    UpdateKey(parent);
                    parent = parent.getParent();
                }
            }else {
                TwoThreeTree<RunnerID> temp=x.getTree();
                Node<RunnerID> y= temp.Search(temp.getRoot(), ID);
                if (y!=null)
                    temp.DeleteLeaf(y);
            }
        }
    }




// we get the node that we want to update

    public void UpdateNode(NodeFloat node, float time){
        float oldTime = node.getKey();
        node.setKey(time);
        if(node.getParent().getLeftChild() == node){ // the node is the left child
            if(node.getParent().getMiddleChild() != null){
                if(node.getParent().getRightChild() != null){ // we have 3 children
                    if(time > node.getParent().getRightChild().getKey()){
                        SetChildren(node.getParent(), node.getParent().getMiddleChild(), node.getParent().getRightChild(), node);
                    }
                    if(time > node.getParent().getMiddleChild().getKey()) {
                        SetChildren(node.getParent(), node.getParent().getMiddleChild(), node, node.getParent().getRightChild());
                    }
                }
                else{ // we have only the left and the middle
                    if(time > node.getParent().getMiddleChild().getKey()){
                        SetChildren(node.getParent(), node.getParent().getMiddleChild(), node, null);
                    }
                }
            }
        }
        else if(node.getParent().getMiddleChild() == node){ // the node is the middle child
            if(node.getParent().getRightChild() != null){
                if(time > node.getParent().getRightChild().getKey()){
                    SetChildren(node.getParent(), node.getParent().getLeftChild(), node.getParent().getRightChild(), node);
                }
                if(time < node.getParent().getLeftChild().getKey()){
                    SetChildren(node.getParent(), node, node.getParent().getLeftChild(), node.getParent().getRightChild());
                }
            }
            else{ // only left child exists besides the node itself
                if(time < node.getParent().getLeftChild().getKey()){
                    SetChildren(node.getParent(), node, node.getParent().getLeftChild(), null);
                }
            }
        }
        else{ // the node is the right child
            if(time < oldTime){
                if(node.getParent().getLeftChild().getKey() > time){
                    SetChildren(node.getParent(), node, node.getLeftChild(), node.getMiddleChild());
                }
                else if(time < node.getParent().getMiddleChild().getKey()){
                    SetChildren(node.getParent(), node.getParent().getLeftChild(), node, node.getParent().getMiddleChild());
                }
            }
        }
        UpdateKey(node.getParent()); // either way we will have to update
    }

    public NodeFloat SearchTmin(float wantedTime, NodeFloat root) {
        if (root == null) {
            return null;
        }
        if (root.getKey() == wantedTime) {
            if (root.getLeftChild() == null) { // it is a leaf or the root has no children
                return root; // if it's a leaf with the same value, it is the right one
            }
            if (root.getRightChild() != null) {
                return SearchTmin(wantedTime, root.getRightChild());
            } else { // root has the max (so max == wanted.key) then we search in the middle
                return SearchTmin(wantedTime, root.getMiddleChild());
            }
        } else { // right subtree is not relevant
            if (root.getLeftChild() == null) {
                return null;
            }
            if (wantedTime <= root.getLeftChild().getKey()) {
                return SearchTmin(wantedTime, root.getLeftChild());
            } else if (wantedTime <= root.getMiddleChild().getKey()) {
                return SearchTmin(wantedTime, root.getMiddleChild());
            } else {
                return SearchTmin(wantedTime, root.getRightChild());
            }
        }
    }

    public int Rank(NodeFloat x) {//TODO:CHECK IF WORKING
        int rank = 1;
        NodeFloat y = x.getParent();
        while (y != null) {
            if (x == y.getMiddleChild()) {
                rank += y.getLeftChild() != null ? y.getLeftChild().getNumberOfChildren() + 1 : 1;
            } else if (x == y.getRightChild()) {
                rank += (y.getLeftChild() != null ? y.getLeftChild().getNumberOfChildren() + 1 : 1) + (y.getMiddleChild() != null ? y.getMiddleChild().getNumberOfChildren() + 1 : 1);
            }
            x = y;
            y = y.getParent();
        }
        return rank;
    }

    /** Prints functions for testing **/

    private void recursivePrint(NodeFloat node) {
        if (node.getLeftChild() == null) {
            System.out.print(node.getKey() + " ");
            return;
        }
        recursivePrint(node.getLeftChild());
        if (node.getMiddleChild() != null) {
            recursivePrint(node.getMiddleChild());
        }
        if (node.getRightChild() != null) {
            recursivePrint(node.getRightChild());
        }
    }

    public void printTree() {
        recursivePrint(this.root);
    }

    public static void main(String[] args) {
        MinTree minTree = new MinTree();
        System.out.println("Init Tree:");
        minTree.printTree();

        // Insert some nodes
        minTree.Insert(new ConcreteRunnerID("Runner1"), 10);
        minTree.Insert(new ConcreteRunnerID("Runner2"), 15);
        minTree.Insert(new ConcreteRunnerID("Runner3"), 8);

        System.out.println("Original Tree:");
        minTree.printTree();

        minTree.Insert(new ConcreteRunnerID("Runner5"), 15);
        System.out.println("After Adding Runner5 with Time 15 Tree:");
        minTree.printTree();

        ConcreteRunnerID R = new ConcreteRunnerID("Runner4");
        minTree.Insert(R, 15);
        System.out.println("After Adding Runner4 with Time 15 Tree:");
        minTree.printTree();


        // Test search
        float searchKey = 10;
        NodeFloat result = minTree.findNode(minTree.getRoot(),searchKey);

        if (result != null) {
            System.out.println("Key " + searchKey + " found in the tree.");
            minTree.Delete(result.getTree().getRoot().getKey(), searchKey);
            System.out.println("After Deleting 10 Tree:");
            minTree.printTree();
        } else {
            System.out.println("Key " + searchKey + " not found in the tree.");
        }
        float searchKey2 = 10;
        NodeFloat result2 = minTree.findNode(minTree.getRoot(),searchKey2);

        if (result2 != null) {
            System.out.println("Key " + searchKey2 + " found in the tree.");
        } else {
            System.out.println("Key " + searchKey2 + " not found in the tree.");
        }

        float searchKey3 = 15;
        NodeFloat result3 = minTree.findNode(minTree.getRoot(),searchKey3);

        if (result3 != null) {
            System.out.println("Key " + searchKey3 + " found in the tree.");
            Node<RunnerID> test3 = result3.getTree().Search(result3.getTree().getRoot(), R);
            minTree.Delete(R, searchKey3);
            System.out.println("After Deleting Runner4 Tree:");
            minTree.printTree();

        } else {
            System.out.println("Key " + searchKey3 + " not found in the tree.");
        }

        // Additional tests and operations can be added based on your implementation
    }

    // Additional tests and operations can be added based on your implementation
}
