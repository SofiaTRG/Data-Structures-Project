public class MinTree {
    private NodeFloat root;

    // Init tree
    public MinTree() {
        NodeFloat maxValue = new NodeFloat(Float.MAX_VALUE);
        maxValue.setTree(new TwoThreeTree());
        this.root = new NodeFloat(Float.MAX_VALUE, null, new NodeFloat(Float.MIN_VALUE),maxValue, null );
        this.root.getLeftChild().setParent(root);
        this.root.getMiddleChild().setParent(root);
        this.root.getLeftChild().setKey(Float.MIN_VALUE);
        this.root.getMiddleChild().setKey(Float.MAX_VALUE);

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

    /**
     * update the key of current parent to be the max key of his children
     * @param parent
     */
    // think about away to update minNodeFloat
    private void UpdateKey(NodeFloat parent){
        if (parent.getMiddleChild() == null) {
            parent.setKey(parent.getLeftChild().getKey());
            parent.setSize(parent.getLeftChild().getSize()); // TODO:check for rank
            return;
        } //assuming we call the function not on a leaf
        if(parent.getRightChild() == null){
            parent.setKey(parent.getMiddleChild().getKey());
            parent.setSize(parent.getLeftChild().getSize()+parent.getMiddleChild().getSize()); // TODO:check for rank
            return;
        }
        parent.setKey(parent.getRightChild().getKey());
        parent.setSize(parent.getLeftChild().getSize()+parent.getMiddleChild().getSize()+parent.getRightChild().getSize()); // TODO:check for rank
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
        //update the size value of the original parent //PREVIUS FOR RANK
//        int currSize ;
//        currSize=parent.getLeftChild().getSize()+parent.getMiddleChild().getSize();
//        parent.setSize(currSize);
//        //update the size value of the new node we created
//        currSize=splitNode.getLeftChild().getSize()+splitNode.getMiddleChild().getSize();
//        splitNode.setSize(currSize);
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

        if(parent.getRightChild() == null){ // can insert without split and deg_in(parent)==2
            if(newChild.getKey() < parent.getLeftChild().getKey()) {
                SetChildren(parent,newChild, parent.getLeftChild(), parent.getMiddleChild());
//                parent.setSize(parent.getSize()+1);
                return null;
            }
            if(parent.getMiddleChild() == null){
                SetChildren(parent, parent.getLeftChild(),newChild,null);
//                parent.setSize(parent.getSize()+1); //TODO: check for rank
                return null;
            }
            if(newChild.getKey() < parent.getMiddleChild().getKey() && parent.getRightChild() == null) {
                SetChildren(parent, parent.getLeftChild(),newChild, parent.getMiddleChild());
//                parent.setSize(parent.getSize()+1);//TODO: check for rank
                return null;
            }
            // לבדוק את התנאי
            else {
                SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(), newChild);
//                parent.setSize(parent.getSize()+1);//TODO: check for rank
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
        NodeFloat x= findNode(root, key);
        if(x!=null) {
            x.getTree().Insert(new Node(ID));
        } else {
            //if (this.root.getLeftChild() == null) {
            //    TwoThreeTree tree=new TwoThreeTree();
             //   tree.Insert(new Node(ID));
             //   NodeFloat y=new NodeFloat(key,tree);
             //   y.setSize(1);//insterting a new leaf with size 1
             //   this.root.setLeftChild(y);
             //   return;
             //in case we have an empty tree
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
            NodeFloat parentSave = temp.getParent();//current suceccor that we look at
            TwoThreeTree tree=new TwoThreeTree();
            tree.Insert(new Node(ID));
            NodeFloat y=new NodeFloat(key,tree);
            y.setSize(1);//inserting a new leaf with size 1
            NodeFloat newNode = Insert_And_Split(temp, y); // found place in tree - Insert
            while (parentSave != this.root) { //update the keys of the tree
                temp = parentSave.getParent();
                if (newNode != null) {
                    newNode = Insert_And_Split(parentSave, newNode);
                }
                //if (newNode==null){// we inserted the new node without split then deg_in(parentsave)==3
                    //parentSave.setSize(parentSave.getSize()+1);//we should update the parent size value by 1
                UpdateKey(parentSave);
                parentSave = temp;
            }
            if (newNode != null) { //need a new root
                NodeFloat newRoot = new NodeFloat(0);
                SetChildren(newRoot, parentSave, newNode, null);
                int currSize ;
                currSize=parentSave.getSize()+newNode.getSize();//updating the new root size value if needed
                newRoot.setSize(currSize);
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
    public void Delete(RunnerID ID, float key){ //TODO: CHECK IF WORKING// need to verify it supports updating the size value
//            NodeFloat x= SearchTmin(key,root);
        NodeFloat x= findNode(root, key);
        if(x!=null ){
            if(x.getTree().getNumberOfLeaves()<=1 && x.getKey() != Float.MAX_VALUE ) {
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
                    parent.setSize(parent.getSize()-1);//because we want to delete the leaf x  //the parent of the leaf
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
                TwoThreeTree temp=x.getTree();
                Node y= temp.Search(temp.getRoot(), ID);
                if (y!=null) {
                    temp.DeleteLeaf(y);
                    updateSizeFathers(x);
                }
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


    public int Rank(NodeFloat x) {
        int rank = 1;
        NodeFloat y = x.getParent();
        while (y != null) {
            if (x == y.getMiddleChild()) {
                rank = rank + y.getLeftChild().getSize();
//                System.out.println(rank);
            } else if (x == y.getRightChild()) {
                rank = rank + y.getLeftChild().getSize() + y.getMiddleChild().getSize();
//                System.out.println(rank);
            }
            x = y;
            y = y.getParent();
        }
        return rank;
    }


    public void updateSize(NodeFloat parent) {
        if (parent.getMiddleChild() == null) {
            parent.setSize(parent.getLeftChild().getSize()); // TODO:check for rank
            return;
        } //assuming we call the function not on a leaf
        if(parent.getRightChild() == null){
            parent.setSize(parent.getLeftChild().getSize()+parent.getMiddleChild().getSize()); // TODO:check for rank
            return;
        }
        parent.setSize(parent.getLeftChild().getSize()+parent.getMiddleChild().getSize()+parent.getRightChild().getSize()); // TODO:check for rank
    }

    public void updateSizeFathers(NodeFloat x) {
        x.setSize(x.getTree().getNumberOfLeaves());
        NodeFloat parent = x.getParent();
        while (parent!=null) {
            this.updateSize(parent);
            parent = parent.getParent();
        }
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
}