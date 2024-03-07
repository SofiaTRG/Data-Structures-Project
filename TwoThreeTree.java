public class TwoThreeTree<T> {
    private Node<T> root;

    /**
     * using the inf and -inf "towers" to save checks
     * we assume there are no two identical keys since the TwoThreeTree will be used with IDs which are unique
     * @param root
     */
    // for min, avg and time tree can be duplicates
    // minmax is key?
    public TwoThreeTree(Node<T> root,boolean minmax){
        this.root = root;
        if(minmax) {
            this.root.leftChild = new Node<>(root, null, Integer.MIN_VALUE, Integer.MIN_VALUE);
            this.root.middleChild = new Node<>(root, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        else{
            this.root.leftChild = new Node<>(root, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.root.middleChild = new Node<>(root, null, Integer.MAX_VALUE, Integer.MIN_VALUE);
        }
        this.root.key = Integer.MAX_VALUE;
    }
    public TwoThreeTree(boolean minmax){
        this(new Node<>(Integer.MAX_VALUE,Integer.MAX_VALUE),minmax);
    }
    public Node<T> getRoot(){
        return this.root;
    }

    public boolean isEmpty(){
        if(this.root.leftChild.getKey() == Integer.MIN_VALUE &&
                this.root.middleChild.getKey() == Integer.MAX_VALUE){return true;}
        return false;
    }

    /**
     * update the key of current parent to be the max key of his children
     * @param parent
     */
    // make a same function to min key
    private void UpdateKey(Node<T> parent){
        if (parent.middleChild == null) {
            parent.key = parent.leftChild.key;
            parent.secondKey = parent.leftChild.secondKey;
            return;
        } //assuming we call the function not on a leaf
        if(parent.rightChild == null){
            parent.key = parent.middleChild.key;
            parent.secondKey = parent.middleChild.secondKey;
            return;
        }
        parent.key = parent.rightChild.key;
        parent.secondKey = parent.rightChild.secondKey;
    }


    /**
     * set the children received as parameters to be children of the parent(also parameter) in order
     * @param parent
     * @param left
     * @param middle
     * @param right
     */
    private void SetChildren(Node<T> parent, Node<T> left, Node<T> middle, Node<T> right){
        parent.leftChild = left;
        parent.middleChild = middle;
        parent.rightChild = right;
        if(left != null) left.parent = parent;
        if(middle != null) middle.parent = parent;
        if(right != null) right.parent = parent;
        UpdateKey(parent);
    }

    /**
     * activated when a split is needed.
     * creates a new parent node and splits the children between the two parents
     * @param parent
     * @param newChild
     * @return new parent node
     */
    private Node<T> Split(Node<T> parent, Node<T> newChild){
        Node<T> splitNode = new Node<>();
        if(newChild.key < parent.leftChild.key ||
                (parent.leftChild.key == newChild.key && parent.leftChild.secondKey < newChild.secondKey)) {
            // need to insert child to leftChild and shift everyone right
            SetChildren(splitNode,parent.middleChild,parent.rightChild,null);
            SetChildren(parent,newChild,parent.leftChild,null);
        }
        else if(newChild.key < parent.middleChild.key ||
                (parent.middleChild.key == newChild.key && parent.middleChild.secondKey < newChild.secondKey)) {
            //insert second and shift others right
            SetChildren(splitNode,parent.middleChild,parent.rightChild,null);
            SetChildren(parent,parent.leftChild,newChild,null);
        }
        else if(newChild.key < parent.rightChild.key ||
                (parent.rightChild.key == newChild.key && parent.rightChild.secondKey < newChild.secondKey)) {
            //if we are in the split then the parent has three children - we can check right child
            // insert third and shift one right
            SetChildren(splitNode,newChild,parent.rightChild,null);
            SetChildren(parent,parent.leftChild,parent.middleChild,null);
        }
        else{
            // insert rightest
            SetChildren(splitNode,parent.rightChild,newChild,null);
            SetChildren(parent,parent.leftChild,parent.middleChild,null);
        }
        return splitNode;
    }


    /**
     * activated after the node's destination is found
     * inserts the node or calls split if the current parent node is full.
     * @param parent
     * @param newChild
     * @return new node created after spilt or null if there was no split
     */
    private Node<T> Insert_And_Split(Node<T> parent, Node<T> newChild){
        if (parent.parent!=null){
            parent=parent.parent;
        }
        if(parent.rightChild == null){ // can insert without split
            if(newChild.key < parent.leftChild.key  ||
                    (parent.leftChild.key == newChild.key && parent.leftChild.secondKey < newChild.secondKey)) {
                SetChildren(parent,newChild,parent.leftChild,parent.middleChild);
                return null;
            }
            if(parent.middleChild == null){
                SetChildren(parent,parent.leftChild,newChild,null);
                return null;
            }
            if(newChild.key < parent.middleChild.key  ||
                    (parent.middleChild.key == newChild.key && parent.middleChild.secondKey < newChild.secondKey)) {
                SetChildren(parent,parent.leftChild,newChild,parent.middleChild);
                return null;
            }
            else {
                SetChildren(parent, parent.leftChild, parent.middleChild, newChild);
                return null;
            }
        }
        // No condition was met so far - Need a split since tree is full
        return Split(parent,newChild);
    }


    /**
     * finds the place in the tree where node should be inserted
     * calls Insert_And_Split() to insert the node there
     * updates the keys throughout the tree after insertion
     * @param node
     */
    public void Insert(Node<T> node, boolean whichKey){
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

    /**
     * removes a node from the tree - removes link to the parent and link from the parent
     * @param node
     */
    private void Remove(Node<T> node){
        Node<T> parent = node.parent;
        if(node == parent.leftChild){SetChildren(parent,parent.middleChild,parent.rightChild,null);return;}
        if(node == parent.middleChild){SetChildren(parent,parent.leftChild,parent.rightChild,null);return;}
        SetChildren(parent,parent.leftChild,parent.middleChild,null);
        return;
    }


    private Node<T> BorrowOrMerge(Node<T> node){
        Node<T> parent = node.parent;
        if(node == parent.leftChild){
            if(parent.middleChild.rightChild != null){ //borrow from middle
                SetChildren(node,node.leftChild,parent.middleChild.leftChild,null);
                SetChildren(parent.middleChild,parent.middleChild.middleChild,parent.middleChild.rightChild,null);
            }
            else { // can't borrow from middle. pass the issue higher
                SetChildren(parent.middleChild, node.leftChild, parent.middleChild.leftChild, parent.middleChild.middleChild);
                Remove(node);
            }
        }
        else if(node == parent.middleChild){
            if(parent.leftChild.rightChild != null){ //borrow from left
                SetChildren(node,parent.leftChild.rightChild,node.leftChild,null);
                SetChildren(parent.leftChild,parent.leftChild.leftChild,parent.leftChild.middleChild,null);
            }
            else { // can't borrow from middle. pass the issue higher
                SetChildren(parent.leftChild, parent.leftChild.leftChild, parent.leftChild.middleChild, node.leftChild);
                Remove(node);
            }
        }
        else{ // node is right child
            if(parent.middleChild.rightChild != null){ //borrow from middle
                SetChildren(node,parent.middleChild.rightChild,node.leftChild,null);
                SetChildren(parent.middleChild,parent.middleChild.leftChild,parent.middleChild.middleChild,null);
            }
            else { // can't borrow from middle. pass the issue higher
                SetChildren(parent.middleChild, parent.middleChild.leftChild, parent.middleChild.middleChild, node.leftChild);
                Remove(node);
            }
        }
        // since we might have borrowed the keys might have been changed
        UpdateKey(parent.leftChild);
        if(parent.middleChild != null){UpdateKey(parent.middleChild);}
        if(parent.rightChild != null){UpdateKey(parent.middleChild);}
        return node;
    }

    /**
     * working under the assumption that only leaves are getting deleted
     * @param node
     */
    public void Delete(Node<T> node){
        if(node == this.root){this.root=null; return;} // Create an empty tree
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
}