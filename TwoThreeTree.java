public class TwoThreeTree<T extends RunnerID> {
    private Node<T> root;
    private boolean isBalanced;// a flag indicates the tree is 2-3 TREE
    private int numberOfLeaves ;

    /**
     * using the inf and -inf "towers" to save checks
     * we assume there are no two identical keys since the TwoThreeTree will be used with IDs which are unique
     */

    public TwoThreeTree() {
        this.root = new Node<>();
        isBalanced = false;
    }

    public Node<T> getRoot() {
        return this.root;
    }


    public int getNumberOfLeaves() {
        return numberOfLeaves;
    }

    /**
     * update the key of current parent to be the max key of his children
     *
     * @param parent
     */
    // make a same function to min key
    private void UpdateKey(Node<T> parent) {
        if (parent.middleChild == null) {
            parent.key = parent.leftChild.key;
            parent.secondKey = parent.leftChild.secondKey;
            return;
        } //assuming we call the function not on a leaf
        if (parent.rightChild == null) {
            parent.key = parent.middleChild.key;
            parent.secondKey = parent.middleChild.secondKey;
            return;
        }
        parent.key = parent.rightChild.key;
        parent.secondKey = parent.rightChild.secondKey;
    }


    /**
     * set the children received as parameters to be children of the parent(also parameter) in order
     *
     * @param parent
     * @param left
     * @param middle
     * @param right
     */
    private void SetChildren(Node<T> parent, Node<T> left, Node<T> middle, Node<T> right) {
        parent.leftChild = left;
        parent.middleChild = middle;
        parent.rightChild = right;
        if (left != null) left.parent = parent;
        if (middle != null) middle.parent = parent;
        if (right != null) right.parent = parent;
        UpdateKey(parent);
        updateNodeRunners(parent);
    }

    /**
     * updates the minialRunner and minimalAVG runner of the parent and their times
     * @param parent
     */
    private void updateNodeRunners(Node<T> parent){
        Node<T> fastestRunner;
        Node<T> fastestAvgRunner;
        fastestRunner= findMinRuner(parent);
        fastestAvgRunner=minRunnerAvgRunTime(parent);
        parent.setFastestRunnerMin(fastestRunner.getKey());
        parent.setFastestRunnerAvg(fastestAvgRunner.getKey());
        parent.setMinimalRunTime(fastestRunner.getMinimalRunTime());
        parent.setAvgRunTime(fastestRunner.getAvgRunTime());
    }
    /**
     * activated after the node's destination is found
     * inserts the node or calls split if the current parent node is full.
     * @param parent
     * @param newChild
     * @return new node created after spilt or null if there was no split
     */
    private Node<T> Insert_And_Split(Node<T> parent, Node<T> newChild) {
        Node<T> l = parent.leftChild;
        Node<T> m = parent.middleChild;
        Node<T> r = parent.rightChild;
        if (r == null) {//deg(parent)=2
            if (newChild.getKey().isSmaller(l.getKey()))
                SetChildren(parent, newChild, l, m);
            else if (newChild.getKey().isSmaller(m.getKey()))
                SetChildren(parent, l, newChild, m);
            else SetChildren(parent, l, m, newChild);
            return null;
        }
        Node<T> y = new Node<>();
        if (newChild.getKey().isSmaller(l.getKey())) {
            SetChildren(parent, newChild, l, null);
            SetChildren(y, m, r, null);
        } else if (newChild.getKey().isSmaller(m.getKey())) {
            SetChildren(parent, l, newChild, null);
            SetChildren(y, m, r, null);
        } else if (newChild.getKey().isSmaller(r.getKey())) {
            SetChildren(parent, l, m, null);
            SetChildren(y, newChild, r, null);
        } else {
            SetChildren(parent, l, m, null);
            SetChildren(y, r, newChild, null);
        }
        return y;
    }


    /**
     * finds the place in the tree where node should be inserted
     * calls Insert_And_Split() to insert the node there
     * updates the keys throughout the tree after insertion
     * @param node
     */
    public void Insert(Node<T> node) {
        if (!isBalanced)
            InitInsert(node);
        Node<T> y = root;
        while (root.leftChild != null && root.rightChild != null)//while y is not a leaf
            if (node.getKey().isSmaller(y.leftChild.getKey()))
                y = y.leftChild;
            else if (node.getKey().isSmaller(y.middleChild.getKey()))
                y = y.middleChild;
            else y = y.rightChild;

        Node<T> x = y.parent;
        Node<T> z = Insert_And_Split(x, node);
        while (x != root) {
            x = x.parent;
            if (z != null)
                z = Insert_And_Split(x, z);
            else UpdateKey(x);
        }
        if (z != null) {
            Node<T> w = new Node<>();
            SetChildren(w, x, z, null);
            this.root = w;
        }
        numberOfLeaves++;
    }

    /**
     * first insertion if the tree has less than 2 leaves
     *
     * @param node a new node we want to insert a a leaf
     */
    public void InitInsert(Node<T> node) {
        if (root.leftChild == null) {//it means that it is the first leaf we insert to the tree
            root.leftChild = node;
            node.setParent(root);
            UpdateKey(root);
        } else {//else we need to decide who is the left and the right.
            RunnerID leftRunner = root.leftChild.getKey();
            RunnerID rightRunner = node.getKey();
            if (leftRunner.isSmaller(rightRunner)) {
                root.rightChild = node;
                node.setParent(root);
                UpdateKey(root);
                updateNodeRunners(root);
            } else {
                root.rightChild = root.leftChild;
                root.leftChild = node;
                node.setParent(root);
                UpdateKey(root);
                updateNodeRunners(root);
            }
            isBalanced = true;
        }
    }

    /**
     * removes a node from the tree - removes link to the parent and link from the parent
     * @param node
     */
    private void deleteNode(Node<T> node) {
        Node<T> parent = node.parent;
        if (node == parent.leftChild) {
            SetChildren(parent, parent.middleChild, parent.rightChild, null);
            return;
        }
        if (node == parent.middleChild) {
            SetChildren(parent, parent.leftChild, parent.rightChild, null);
            return;
        }
        SetChildren(parent, parent.leftChild, parent.middleChild, null);
    }


    private Node<T> BorrowOrMerge(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> x;
        if (node == parent.leftChild) {
            x = parent.middleChild;
            if (x.rightChild != null) { //borrow from middle
                SetChildren(node, node.leftChild, x.leftChild, null);
                SetChildren(x, x.middleChild.middleChild, x.rightChild, null);
            } else { // can't borrow from middle. pass the problem to higher level
                SetChildren(x, node.leftChild, x.leftChild, x.middleChild);
                deleteNode(node);
                SetChildren(parent, x, parent.rightChild, null);
            }
            return parent;
        } else if (node == parent.middleChild) {
            x = parent.leftChild;
            if (x.rightChild != null) { //borrow from left
                SetChildren(node, x.rightChild, node.leftChild, null);
                SetChildren(x, x.leftChild, x.middleChild, null);
            } else { // can't borrow from middle. pass the issue higher
                SetChildren(x, x.leftChild, x.middleChild, node.leftChild);
                deleteNode(node);
                SetChildren(parent, x, parent.rightChild, null);
            }
            return parent;
        } else { // node is right child
            x = parent.middleChild;
            if (x.rightChild != null) { //borrow from middle
                SetChildren(node, x.rightChild, node.leftChild, null);
                SetChildren(x, x.leftChild, x.middleChild, null);
            } else { // can't borrow from middle. pass the issue higher
                SetChildren(x, x.leftChild, x.middleChild, node.leftChild);
                deleteNode(node);
                SetChildren(parent, parent.leftChild, x, null);
            }
            return parent;
        }
    }

    public void deleteLast(){
        numberOfLeaves--;
        this.root=null;//it means that we removed the last node in the tree and because of that the root points to null
    }
    /**
     * working under the assumption that only leaves are getting deleted
     *
     * @param node
     */
    public void DeleteLeaf(Node<T> node) {
        if(!isBalanced)
            deleteLast(node);
        Node<T> parent = node.parent;
        if (node == parent.leftChild)
            SetChildren(parent, parent.middleChild, parent.rightChild, null);
        else if (node == parent.middleChild) {
            SetChildren(parent, parent.leftChild, parent.rightChild, null);
        } else SetChildren(parent, parent.leftChild, parent.middleChild, null);
        deleteNode(node);
        while (parent != null) {
            if (parent.middleChild != null) {
                UpdateKey(parent);
                parent = parent.parent;
            } else {
                if (parent != this.root) {
                    parent = BorrowOrMerge(parent);
                } else {
                    this.root = parent.leftChild;
                    parent.leftChild.parent = null;
                    deleteNode(parent);
                    return;
                }
            }
        }
        numberOfLeaves--;
        if(numberOfLeaves==1)
            isBalanced=false;
    }

    /**
     * search a runner in the tree
     *
     * @param x   the root of the tree
     * @param key the key we eat to find
     * @return the node that represents the runner(can be modify to the runner id itseld easily)
     */
    public  Node<T> Search(Node<T> x, T key) {
        if (x == null) {
            return null;
        }
        if (x.leftChild == null && x.rightChild == null) {//means that x is a leaf
            if (!(x.getKey().isSmaller(key)) && !(key.isSmaller(x.getKey())))
                return x;
            else return null;
        }
        if (key.isSmaller(x.leftChild.getKey())
                || x.leftChild.getKey().isSmaller(key) && key.isSmaller(x.leftChild.getKey())) {
            return Search(x.leftChild, key);
        } else if (key.isSmaller(x.middleChild.getKey())
                || x.middleChild.getKey().isSmaller(key) && key.isSmaller(x.middleChild.getKey())) {
            return Search(x.middleChild, key);
        } else return Search(x.rightChild, key);
    }

    /**
     * find the runner with the minmial run time from the children of the node
     *breakes ties with the runnerID
     * @param node the node we check
     * @return the runner with the minimal run time from the children of the node
     */
    public Node<T> findMinRuner(Node<T> node) {
        Node<T> minimalRunner;
        RunnerID x=node.leftChild.getKey();
        RunnerID y=node.middleChild.getKey();
        RunnerID z=node.rightChild.getKey();
        if(node.leftChild.getMinimalRunTime()==node.rightChild.getMinimalRunTime())
            if(x.isSmaller(y))
                minimalRunner=node.leftChild;
            else minimalRunner=node.middleChild;
        else if (node.leftChild.getMinimalRunTime() < node.rightChild.getMinimalRunTime())
            minimalRunner = node.leftChild;
        else minimalRunner = node.middleChild;
        if (node.rightChild != null)
            if(node.rightChild.getMinimalRunTime()==minimalRunner.getMinimalRunTime())
                if(z.isSmaller(minimalRunner.getKey()))
                    minimalRunner=node.rightChild;
            else if (node.rightChild.getMinimalRunTime() < minimalRunner.getMinimalRunTime())
                 minimalRunner = node.rightChild;
        return minimalRunner;
    }
    /**
     * find the runner with the minmial AVG run time from the children of the node
     *breakes ties with the runnerID
     * @param node the node we check
     * @return the runner with the minimal AVG run time from the children of the node
     */
    public Node<T> minRunnerAvgRunTime(Node<T> node) {
        Node<T> minimalAvgRunner;
        RunnerID x=node.leftChild.getKey();
        RunnerID y=node.middleChild.getKey();
        RunnerID z=node.rightChild.getKey();
        if(node.leftChild.getAvgRunTime()==node.rightChild.getAvgRunTime())
            if(x.isSmaller(y))
                minimalAvgRunner =node.leftChild;
            else minimalAvgRunner =node.middleChild;
        else if (node.leftChild.getAvgRunTime() < node.rightChild.getAvgRunTime())
            minimalAvgRunner = node.leftChild;
        else minimalAvgRunner = node.middleChild;
        if (node.rightChild != null)
            if(node.rightChild.getAvgRunTime()== minimalAvgRunner.getAvgRunTime())
                if(z.isSmaller(minimalAvgRunner.getKey()))
                    minimalAvgRunner =node.rightChild;
                else if (node.rightChild.getAvgRunTime() < minimalAvgRunner.getAvgRunTime())
                    minimalAvgRunner = node.rightChild;
        return minimalAvgRunner;
    }
}




