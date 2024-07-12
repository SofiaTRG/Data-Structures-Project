/**
 * Represents a 2-3 tree for storing runners' times.
 * Each node in the tree holds a minimum time value among its children.
 */
public class TimeTree {
    //TREE FOR EACH RUNNER FOR THEIR TIMES
    //THE KEY IS TIME
    //EACH NODE HAS THE MIN TIME OF THEIR CHILDREN
    private Run root;

    // Init tree
    public TimeTree() {
        // Initialize the root node with sentinel values
        this.root = new Run(null, new Run(Float.MIN_VALUE),new Run(Float.MAX_VALUE),Float.MAX_VALUE);

        // Set parent links for left and middle children
        this.root.getLeftChild().setParent(root);
        this.root.getMiddleChild().setParent(root);
    }

    // Find a node with a specific time value
    public Run find(float time) { //TODO: FIX SO IT'LL RETURN LEAF
        Run current = root;
        while (current != null) {
            if (current.getTime() == time) {
                return current;
            }
            if (time <= current.getLeftChild().getTime()) {
                current=current.getLeftChild();
            } else if (time <= current.getMiddleChild().getTime()) {
                current=current.getMiddleChild();
            } else {
                current=current.getRightChild();
            }
//            current = current.getTime() < time ? current.getRightChild() : current.getLeftChild();
        }
        return null; //should return exception?
    }
    public Run findNode(Run root, float time) {
        if (root.getLeftChild()== null ) {
            if (root.getTime() == time) {
                return root;
            }
            else return null;
        } if (time<=root.getLeftChild().getTime()) {
            return findNode(root.getLeftChild(),time);
        } else if (time<=root.getMiddleChild().getTime()) {
            return findNode(root.getMiddleChild(),time);
        } else return findNode(root.getRightChild(),time);
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
     * Update the key of the current parent to be the max key of its children.
     *
     * @param parent The parent node to update.
     */
    private void UpdateKey(Run parent){
        parent.setMin(parent.getLeftChild().getMin());
//        if(parent.getLeftChild().getTime()==Float.MIN_VALUE)
//            parent.setMin(parent.getMiddleChild().getTime());
        if (parent.getMiddleChild() == null) {
            parent.setTime(parent.getLeftChild().getTime());
            return;
        } //assuming we call the function not on a leaf
        if(parent.getRightChild() == null) {
            parent.setTime(parent.getMiddleChild().getTime());
            if(parent.getLeftChild().getTime()==Float.MIN_VALUE)
                parent.setMin(parent.getMiddleChild().getTime());
            return;
        }
        parent.setTime(parent.getRightChild().getTime());
//        parent.setMin(parent.getLeftChild().getMin());
        if(parent.getLeftChild().getTime()==Float.MIN_VALUE)
            parent.setMin(parent.getMiddleChild().getTime());
    }


    /**
     * Set the children received as parameters to be children of the parent in order.
     * Update the parent's key after setting children.
     *
     * @param parent  The parent node.
     * @param left    The left child.
     * @param middle  The middle child.
     * @param right   The right child.
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
     * Split the parent node and create a new parent node.
     * Adjust children accordingly.
     *
     * @param parent   The parent node to split.
     * @param newChild The new child node.
     * @return The new parent node created after the split.
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
        } else if(newChild.getTime() < parent.getRightChild().getTime()) {
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

    // Insert and split nodes
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
            else {
                SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(), newChild);
                return null;
            }
        }
        // No condition was met so far - Need a split since tree is full
        return Split(parent,newChild);
    }


    /**
     * Insert a node into the tree and split if necessary.
     * Update keys throughout the tree after insertion.
     *
     * @param node The node to insert.
     */
    public void Insert(Run node){
        if(this.root.getLeftChild() == null) {
            this.root.setLeftChild(node);
            return;
        } //in case we have an empty tree
        Run temp = this.root;
        while(temp.getLeftChild() != null){ //stop when found a leaf
            if(node.getTime() < temp.getLeftChild().getTime()){
                temp= temp.getLeftChild();
            }
            else if(node.getTime() < temp.getMiddleChild().getTime()){
                temp= temp.getMiddleChild();
            }
            else{temp = temp.getRightChild();}
        }

        Run parentSave = temp.getParent();
        Run newNode = Insert_And_Split(temp,node); // found place in tree - Insert
        while(parentSave != this.root){ //update the keys of the tree
            temp = parentSave.getParent();
            if(newNode != null){
                newNode = Insert_And_Split(parentSave,newNode);
            }
            UpdateKey(parentSave);
            parentSave = temp;
        }
        if(newNode != null){ //need a new root
            Run newRoot = new Run();
            SetChildren(newRoot,parentSave,newNode,null);
            this.root = newRoot;
        }
        UpdateKey(root);
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

    /**
     * Borrow or merge a node in the tree.
     * This method is called when a node is removed, and the tree structure needs to be adjusted.
     * It tries to borrow a node from siblings or merges nodes if borrowing is not possible.
     *
     * @param node The node to borrow from or merge with its siblings.
     * @return The node after the borrowing or merging operation.
     */
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
     * Delete a node from the tree.
     * This method removes a node from the tree structure and adjusts the tree accordingly.
     * It also handles the cases where nodes need to be borrowed or merged after deletion.
     *
     * @param run The node to be deleted from the tree.
     */
    public void Delete(Run run){
        if(run == this.root){this.root=null; return;} // Create an empty tree

        Run parent = run.getParent();
        if(parent.getRightChild() != null) {
            if (parent.getLeftChild() == run) {
                SetChildren(parent, parent.getMiddleChild(), parent.getRightChild(), null);
            } else if (parent.getMiddleChild() == run) {
                SetChildren(parent, parent.getLeftChild(), parent.getRightChild(), null);
            } else {// node is right child
                SetChildren(parent, parent.getLeftChild(), parent.getMiddleChild(), null);
            }
            run.setParent(null); // Deleting node from the tree, assuming node is a leaf
        }
        // Can't rearrange. Need to borrow or merge. Delete node first
        else if(parent.getLeftChild() == run) {
            SetChildren(parent, parent.getMiddleChild(),null,null);
        }
        else{SetChildren(parent, parent.getLeftChild(),null,null);}

        // Updating up the tree
        while(parent!=null) {
            if(parent.getMiddleChild() == null) { // We need a Borrow/Merge
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
    }


    /**
     * Update a node's time in the tree.
     * This method updates the time of a specified node in the tree and adjusts the tree structure accordingly.
     * It ensures that the tree remains in a valid state after the update operation.
     *
     * @param run  The node whose time needs to be updated.
     * @param time The new time value to be assigned to the node.
     */
    public void UpdateNode(Run run, float time){
        float oldTime = run.getTime();
        run.setTime(time);
        if(run.getParent().getLeftChild() == run){ // the node is the left child
            if(run.getParent().getMiddleChild() != null){
                if(run.getParent().getRightChild() != null){ // we have 3 children
                    if(time > run.getParent().getRightChild().getTime()){
                        SetChildren(run.getParent(), run.getParent().getMiddleChild(), run.getParent().getRightChild(), run);
                    }
                    if(time > run.getParent().getMiddleChild().getTime()) {
                        SetChildren(run.getParent(), run.getParent().getMiddleChild(), run, run.getParent().getRightChild());
                    }
                }
                else{ // we have only the left and the middle
                    if(time > run.getParent().getMiddleChild().getTime()){
                        SetChildren(run.getParent(), run.getParent().getMiddleChild(), run, null);
                    }
                }
            }
        }
        else if(run.getParent().getMiddleChild() == run){ // the node is the middle child
            if(run.getParent().getRightChild() != null){
                if(time > run.getParent().getRightChild().getTime()){
                    SetChildren(run.getParent(), run.getParent().getLeftChild(), run.getParent().getRightChild(), run);
                }
                if(time < run.getParent().getLeftChild().getTime()){
                    SetChildren(run.getParent(), run, run.getParent().getLeftChild(), run.getParent().getRightChild());
                }
            }
            else{ // only left child exists besides the node itself
                if(time < run.getParent().getLeftChild().getTime()){
                    SetChildren(run.getParent(), run, run.getParent().getLeftChild(), null);
                }
            }
        }
        else{ // the node is the right child
            if(time < oldTime){
                if(run.getParent().getLeftChild().getTime() > time){
                    SetChildren(run.getParent(), run, run.getLeftChild(), run.getMiddleChild());
                }
                else if(time < run.getParent().getMiddleChild().getTime()){
                    SetChildren(run.getParent(), run.getParent().getLeftChild(), run, run.getParent().getMiddleChild());
                }
            }
        }
        UpdateKey(run.getParent()); // either way we will have to update
    }


    /**
     * Search for a node with the specified time value in the tree.
     * This method recursively searches for a node with the specified time value in the tree structure.
     *
     * @param wantedTime The time value to search for in the tree.
     * @param root       The root node of the subtree to search in.
     * @return The node with the specified time value if found, otherwise null.
     */
    public Run Search(float wantedTime, Run root) {
        if (root == null) {
            return null;
        } if (root.getTime() == wantedTime) {
            if (root.getLeftChild() == null) { // it is a leaf or the root has no children
                return root; // if its a leaf with the same value it is the right one
            }
            if(root.getRightChild() != null) {
                return Search(wantedTime,root.getRightChild());
            }
            else { // root has the max (so max == wanted.key) than we search in the middle
                return Search(wantedTime, root.getMiddleChild());
            }
        } else { // right sub tree is not relevant
            if(root.getLeftChild() == null){return null;}
            if (wantedTime <= root.getLeftChild().getTime()) {
                return Search(wantedTime, root.getLeftChild());
            } else if(wantedTime <= root.getMiddleChild().getTime()){
                return Search(wantedTime,root.getMiddleChild());
            }
            else{
                return Search(wantedTime,root.getRightChild());
            }
        }
    }

    /** Prints functions for testings **/

    private void recursivePrint(Run node){
        if (node.getLeftChild() == null){System.out.print(node.getTime() + " "); return;}
        recursivePrint(node.getLeftChild());
        if(node.getMiddleChild() !=null){recursivePrint(node.getMiddleChild());}
        if(node.getRightChild() !=null){recursivePrint(node.getRightChild());}
    }
    public void printTree(){
        recursivePrint(this.root);
    }
//
//
//
//    private Run insert(Run root, float key) {
//        if (root == null) {
//            return new Run(key);
//        } else if (root.key > key) {
//            root.leftChild = insert(root.leftChild, key);
//        } else if (root.key < key) {
//            root.rightChild = insert(root.rightChild, key);
//        } else if (root.key == key) {
//            root.addNum();
//        }
//        return rebalance(root);
//    }

    // Test the 2-3 tree
    // Test the 2-3 tree
//    public static void main(String[] args) {
//        TimeTree tree = new TimeTree();
//
//        float[] keysToInsert = {10, 5, 15, 2, 7, 12};
//
//        for (float key : keysToInsert) {
//            tree.Insert(new Run(key));
//        }
//
//        float a = 20;
//        tree.Insert(new Run(a));
//
//        System.out.println("Original Tree:");
//        tree.printTree();
//
//        // Test search
//        float searchKey = 7;
//        //Run result = tree.Search(searchKey, tree.getRoot());
//        Run result = tree.findNode(tree.getRoot(), searchKey);
//
//        if (result != null) {
//            System.out.println("Key " + searchKey + " found in the tree.");
//            tree.Delete(result);
//            System.out.println("After Delete Tree:");
//            tree.printTree();
//        } else {
//            System.out.println("Key " + searchKey + " not found in the tree.");
//        }
//
//        System.out.println(tree.root.getMin());
//        // Additional tests and operations can be added based on your implementation
//    }

}