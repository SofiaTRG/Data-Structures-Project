public class TimeTree {
    //TREE FOR EACH RUNNER FOR THEIR TIMES
    //THE KEY IS TIME
    //EACH NODE HAS THE MIN TIME OF THEIR CHILDREN
    private Run root;
    private float minRun;

    // Init tree
    public TimeTree() {
        this.root = new Run(null, new Run(Float.MIN_VALUE),new Run(Float.MAX_VALUE),Float.MAX_VALUE);
        minRun = Float.MAX_VALUE;
    }

    public Run find(float time) {
        Run current = root;
        while (current != null) {
            if (current.getTime() == time) {
                return current;
            }
            current = current.getTime() < time ? current.getRightChild() : current.getLeftChild();
        }
        return null; //should return exception?
    }

    public Run getRoot(){
        return this.root;
    }

    public boolean isEmpty(){
        if(this.root.getLeftChild().getTime() == Integer.MIN_VALUE &&
                this.root.getMiddleChild().getTime() == Integer.MAX_VALUE){return true;}
        return false;
    }

    /**
     * update the key of current parent to be the max key of his children
     * @param parent
     */
    // think about away to update mintime
    private void UpdateKey(Run parent){
        if (parent.getMiddleChild() == null) {
            parent.setTime(parent.getLeftChild().getTime());
            return;
        } //assuming we call the function not on a leaf
        if(parent.getRightChild() == null){
            parent.setTime(parent.getMiddleChild().getTime());
            return;
        }
        parent.setTime(parent.getRightChild().getTime());
    }


    /**
     * set the children received as parameters to be children of the parent(also parameter) in order
     * @param parent
     * @param left
     * @param middle
     * @param right
     */
    private void SetChildren(Run parent, Run left, Run middle, Run right){
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
    private Run Split(Run parent, Run newChild){
        Run splitNode = new Run();
        if(newChild.getTime() < parent.getLeftChild().getTime()) {
            // need to insert child to leftChild and shift everyone right
            SetChildren(splitNode, parent.getMiddleChild(), parent.getRightChild(),null);
            SetChildren(parent,newChild, parent.getLeftChild(),null);
        }
        else if(newChild.getTime() < parent.getMiddleChild().getTime()) {
            //insert second and shift others right
            SetChildren(splitNode, parent.getMiddleChild(), parent.getRightChild(),null);
            SetChildren(parent, parent.getLeftChild(),newChild,null);
        } else{
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
    private Run Insert_And_Split(Run parent, Run newChild){
        if (parent.getParent() !=null){
            parent= parent.getParent();
        }
        if(parent.getRightChild() == null){ // can insert without split
            if(newChild.getTime() < parent.getLeftChild().getTime()) {
                SetChildren(parent,newChild, parent.getLeftChild(), parent.getMiddleChild());
                return null;
            }
            if(parent.getMiddleChild() == null){
                SetChildren(parent, parent.getLeftChild(),newChild,null);
                return null;
            }
            if(newChild.getTime() < parent.getMiddleChild().getTime() && parent.getRightChild() == null) {
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
     * @param node
     */
    public void Insert(Run node, boolean whichKey){
        if(this.root.getLeftChild() == null) {
            this.root.setLeftChild(node);
            return;
        } //in case we have an empty tree
        Node<T> temp = this.root;
        while(temp.leftChild != null){ //stop when found a leaf
            if(node.key < temp.leftChild.key ||
                    (node.key == temp.leftChild.key && node.secondKey > temp.leftChild.secondKey)){
                temp=temp.leftChild;
            }
            else if(node.key < temp.middleChild.key ||
                    (node.key == temp.middleChild.key && node.secondKey > temp.middleChild.secondKey)){
                temp=temp.middleChild;
            }
            else{temp = temp.rightChild;}
        }

        Node<T> parentSave = temp.parent;
        Node<T> newNode = Insert_And_Split(temp,node); // found place in tree - Insert
        while(parentSave != this.root){ //update the keys of the tree
            temp = parentSave.parent;
            if(newNode != null){
                newNode = Insert_And_Split(parentSave,newNode);
            }
            UpdateKey(parentSave);
            parentSave = temp;
        }
        if(newNode != null){ //need a new root
            Node<T> newRoot = new Node<T>();
            SetChildren(newRoot,parentSave,newNode,null);
            this.root = newRoot;
        }

    }


    /// CHECK
    /**
     * removes a node from the tree - removes link to the parent and link from the parent
     * @param node
     */
    private void Remove(Run node){
        Run parent = node.getParent();
        if(node == parent.getLeftChild()){SetChildren(parent, parent.getMiddleChild(), parent.getRightChild(),null);return;}
        if(node == parent.getMiddleChild()){SetChildren(parent, parent.getLeftChild(), parent.getRightChild(),null);return;}
        SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(),null);
        return;
    }


    private Run BorrowOrMerge(Run node){
        Run parent = node.getParent();
        if(node == parent.getLeftChild()){
            if(parent.getMiddleChild().getRightChild() != null){ //borrow from middle
                SetChildren(node, node.getLeftChild(), parent.getMiddleChild().getLeftChild(),null);
                SetChildren(parent.getMiddleChild(), parent.getMiddleChild().getMiddleChild(), parent.getMiddleChild().getRightChild(),null);
            }
            else { // can't borrow from middle. pass the issue higher
                SetChildren(parent.getMiddleChild(), node.getLeftChild(), parent.getMiddleChild().getLeftChild(), parent.getMiddleChild().getMiddleChild());
                Remove(node);
            }
        }
        else if(node == parent.getMiddleChild()){
            if(parent.getLeftChild().getRightChild() != null){ //borrow from left
                SetChildren(node, parent.getLeftChild().getRightChild(), node.getLeftChild(),null);
                SetChildren(parent.getLeftChild(), parent.getLeftChild().getLeftChild(), parent.getLeftChild().getMiddleChild(),null);
            }
            else { // can't borrow from middle. pass the issue higher
                SetChildren(parent.getLeftChild(), parent.getLeftChild().getLeftChild(), parent.getLeftChild().getMiddleChild(), node.getLeftChild());
                Remove(node);
            }
        }
        else{ // node is right child
            if(parent.getMiddleChild().getRightChild() != null){ //borrow from middle
                SetChildren(node, parent.getMiddleChild().getRightChild(), node.getLeftChild(),null);
                SetChildren(parent.getMiddleChild(), parent.getMiddleChild().getLeftChild(), parent.getMiddleChild().getMiddleChild(),null);
            }
            else { // can't borrow from middle. pass the issue higher
                SetChildren(parent.getMiddleChild(), parent.getMiddleChild().getLeftChild(), parent.getMiddleChild().getMiddleChild(), node.getLeftChild());
                Remove(node);
            }
        }
        // since we might have borrowed the keys might have been changed
        UpdateKey(parent.getLeftChild());
        if(parent.getMiddleChild() != null){UpdateKey(parent.getMiddleChild());}
        if(parent.getRightChild() != null){UpdateKey(parent.getMiddleChild());}
        return node;
    }

    /**
     * working under the assumption that only leaves are getting deleted
     * @param node
     */
    public void Delete(Run run){
        if(run == this.root){this.root=null; return;} // Create an empty tree
        Node<T> parent = node.parent;
        if(parent.rightChild != null) {
            if (parent.leftChild == node) {
                SetChildren(parent, parent.middleChild, parent.rightChild, null);
            } else if (parent.middleChild == node) {
                SetChildren(parent, parent.leftChild, parent.rightChild, null);
            } else {// node is right child
                SetChildren(parent, parent.leftChild, parent.middleChild, null);
            }
            node.parent = null; // Deleting node from the tree, assuming node is a leaf
        }
        // Can't rearrange. Need to borrow or merge. Delete node first
        else if(parent.leftChild == node) {
            SetChildren(parent,parent.middleChild,null,null);
        }
        else{SetChildren(parent,parent.leftChild,null,null);}

        // Updating up the tree
        while(parent!=null) {
            if(parent.middleChild == null) { // We need a Borrow/Merge
                if (parent != this.root) {
                    parent = BorrowOrMerge(parent);
                } else {
                    this.root = parent.leftChild;
                    this.root.parent = null;
                    parent.leftChild = null;
                    parent = this.root;
                }
            }
            UpdateKey(parent);
            parent = parent.parent;
        }
    }




// we get the node that we want to update

    public void UpdateNode(Node<T> Node,  int key){
        int oldKey = Node.getKey();
        Node.setKey(key);
        if(Node.getParent().getLeftChild() == Node){ // the node is the left child
            if(Node.getParent().getMiddleChild() != null){
                if(Node.getParent().getRightChild() != null){ // we have 3 children
                    if(key > Node.getParent().getRightChild().getKey()){
                        SetChildren(Node.getParent(), Node.getParent().getMiddleChild(), Node.getParent().getRightChild(), Node);
                    }
                    if(key > Node.parent.getMiddleChild().getKey()) {
                        SetChildren(Node.getParent(), Node.getParent().getMiddleChild(), Node, Node.getParent().getRightChild());
                    }
                }
                else{ // we have only the left and the middle
                    if(key > Node.getParent().getMiddleChild().getKey()){
                        SetChildren(Node.getParent(), Node.getParent().getMiddleChild(), Node, null);
                    }
                }
            }
        }
        else if(Node.getParent().getMiddleChild() == Node){ // the node is the middle child
            if(Node.getParent().getRightChild() != null){
                if(key > Node.getParent().getRightChild().getKey()){
                    SetChildren(Node.getParent(), Node.getParent().getLeftChild(), Node.getParent().getRightChild(), Node);
                }
                if(key < Node.getParent().getLeftChild().getKey()){
                    SetChildren(Node.getParent(), Node, Node.getParent().getLeftChild(), Node.getParent().getRightChild());
                }
            }
            else{ // only left child exists besides the node itself
                if(key < Node.getParent().getLeftChild().getKey()){
                    SetChildren(Node.getParent(), Node, Node.getParent().getLeftChild(), null);
                }
            }
        }
        else{ // the node is the right child
            if(key < oldKey){
                if(Node.parent.getLeftChild().getKey() > key){
                    SetChildren(Node.parent, Node, Node.getLeftChild(), Node.getMiddleChild());
                }
                else if(key < Node.parent.getMiddleChild().getKey()){
                    SetChildren(Node.parent, Node.parent.getLeftChild(), Node, Node.parent.getMiddleChild());
                }
            }
        }
        UpdateKey(Node.parent); // either way we will have to update
    } //Ilan



    public Node<T> Search(int wantedKey, Node<T> root, Boolean isKey) {
        if (root == null) {
            return null;
        }
        //if(isKey){
        if (root.getKey() == wantedKey) {
            if (root.leftChild == null) { // it is a leaf or the root has no children
                return root; // if its a leaf with the same value it is the right one
            }
            if(root.getRightChild() != null) {
                return Search(wantedKey,root.getRightChild(),true);
            }
            else { // root has the max (so max == wanted.key) than we search in the middle
                return Search(wantedKey, root.getMiddleChild(),true);
            }
        } else { // right sub tree is not relevant
            if(root.getLeftChild() == null){return null;}
            if (wantedKey <= root.getLeftChild().getKey()) {
                return Search(wantedKey, root.getLeftChild(),true );
            } else if(wantedKey <= root.getMiddleChild().getKey()){
                return Search(wantedKey,root.getMiddleChild(),true);
            }
            else{
                return Search(wantedKey,root.getRightChild(),true);
            }
        }
    }


    /**
     *
     * @param first - points/goals
     * @param second - id
     * @param root
     * @return
     */
    public Node<T> searchBySecond(int first, int second, Node<T> root){
        if (root == null) {return null;}
        if(root.getLeftChild() == null) { // Leaf
            if(root.getSecondKey() == second){return root;}
            return null; // there is no child that fits
        }

        // Not a leaf
        if(root.getLeftChild().getKey() > first ||
                (root.getLeftChild().getKey() == first && root.getLeftChild().getSecondKey() <= second)){
            // bigger than the left - go left
            return searchBySecond(first,second,root.getLeftChild());
        }
        else if(root.getRightChild() == null){ // no right child - go mid
            return searchBySecond(first, second, root.getMiddleChild());
        }
        else if(root.getMiddleChild().getKey() > first ||
                (root.getMiddleChild().getKey() == first && root.getMiddleChild().getSecondKey() <= second)){
            // bigger than middle and smaller than left - go mid
            return searchBySecond(first, second, root.getMiddleChild());
        }
        // bigger than left and mid - go right
        return searchBySecond(first, second, root.getRightChild());
    }
    public void updateLeaf(int key, int seconderyKey, int newKey){
        Node<T> found = null;//Search(this.root,seconderyKey);
        Delete(found,true);
        found.setKey(newKey);
        Insert(found,true);
    }

    /** Prints functions for testings **/

    private void recursivePrint(Node<T> node){
        if (node.leftChild == null){System.out.print(node.key + " "); return;}
        recursivePrint(node.leftChild);
        if(node.middleChild !=null){recursivePrint(node.middleChild);}
        if(node.rightChild !=null){recursivePrint(node.rightChild);}
    }
    public void printTree(){
        recursivePrint(this.root);
    }



    private Run insert(Run root, float key) {
        if (root == null) {
            return new Run(key);
        } else if (root.key > key) {
            root.leftChild = insert(root.leftChild, key);
        } else if (root.key < key) {
            root.rightChild = insert(root.rightChild, key);
        } else if (root.key == key) {
            root.addNum();
        }
        return rebalance(root);
    }
}
