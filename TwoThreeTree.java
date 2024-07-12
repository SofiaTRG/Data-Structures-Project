public class TwoThreeTree {
    private Node root;
    private int numberOfLeaves ;

    /**
     * using the inf and -inf "towers" to save checks
     * we assume there are no two identical keys since the TwoThreeTree will be used with IDs which are unique
     */

    public TwoThreeTree() {
        this.root = new Node();//new internal node x
        RunnerID inf=new inf();
        RunnerID minusInf=new minusInf();
        Node l= new Node(minusInf);
        Node m= new Node(inf);
        l.setParent(root);
        m.setParent(root);
        root.setKey(inf);
        root.setLeftChild(l);
        root.setMiddleChild(m);
    }

    public Node getRoot() {
        return this.root;
    }


    public int getNumberOfLeaves() {
        return numberOfLeaves;
    }

    /**
     * a function in order to compare between two runnerID'S. we assume that we compare x to y by this order
     * @param x the first runner
     * @param y the second runner
     * @return if x is smaller than y
     */
    public boolean variantIsSmaller(RunnerID x,RunnerID y){
        boolean x_inf_minus=false;
        boolean y_inf_minus=false;
        if(x instanceof inf || x instanceof minusInf)
            x_inf_minus=true;
        if(y instanceof inf || y instanceof minusInf)
            y_inf_minus=true;
        if(x_inf_minus)
            return x.isSmaller(y);
        if(y_inf_minus)
            return !(y.isSmaller(x));
        return x.isSmaller(y);// if none of them are sentinals
    }
    /**
     * update the key of current parent to be the max key of his children
     *
     * @param parent
     */
    // make a same function to min key
    private void UpdateKey(Node parent) {
        if (parent.getMiddleChild() == null) {
            parent.key = parent.leftChild.key;
            parent.setSizeRank(parent.getLeftChild().getSizeRank()); // FOR RANK FUNCTION
            return;
        } //assuming we call the function not on a leaf
        if (parent.rightChild == null) {
            parent.key = parent.middleChild.key;
            parent.setSizeRank(parent.getLeftChild().getSizeRank()+parent.getMiddleChild().getSizeRank()); // FOR RANK FUNCTION
            return;
        }
        parent.key = parent.rightChild.key;
        parent.secondKey = parent.rightChild.secondKey;
        parent.setSizeRank(parent.getLeftChild().getSizeRank()+parent.getMiddleChild().getSizeRank()+parent.getRightChild().getSizeRank()); // FOR RANK FUNCTION
    }


    /**
     * set the children received as parameters to be children of the parent(also parameter) in order
     * @param parent
     * @param left
     * @param middle
     * @param right
     */
    private void SetChildren(Node parent, Node left, Node middle, Node right) {
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
    private void updateNodeRunners(Node parent){
        Node fastestRunner;
        Node fastestAvgRunner;
        fastestRunner= findMinRunner(parent);
        fastestAvgRunner=minRunnerAvgRunTime(parent);
        parent.setFastestRunnerMin(fastestRunner.getFastestRunnerMin());
        parent.setFastestRunnerAvg(fastestAvgRunner.getFastestRunnerAvg());
        parent.setMinimalRunTime(fastestRunner.getMinimalRunTime());
        parent.setAvgRunTime(fastestAvgRunner.getAvgRunTime());
    }
    /**
     * activated after the node's destination is found
     * inserts the node or calls split if the current parent node is full.
     * @param parent
     * @param newChild
     * @return new node created after spilt or null if there was no split
     */
    private Node Insert_And_Split(Node parent, Node newChild) {
        Node l = parent.leftChild;
        Node m = parent.middleChild;
        Node r = parent.rightChild;
        if (r == null) {//deg(parent)=2
            if (variantIsSmaller(newChild.getKey(),l.getKey()))
                SetChildren(parent, newChild, l, m);
            else if (variantIsSmaller(newChild.getKey(),m.getKey()))
                SetChildren(parent, l, newChild, m);
            else SetChildren(parent, l, m, newChild);
            return null;
        }
        Node y = new Node();
        if (variantIsSmaller(newChild.getKey(),l.getKey())){
            SetChildren(parent, newChild, l, null);
            SetChildren(y, m, r, null);
        } else if (variantIsSmaller(newChild.getKey(),m.getKey())) {
            SetChildren(parent, l, newChild, null);
            SetChildren(y, m, r, null);
        } else if (variantIsSmaller(newChild.getKey(),r.getKey())){
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
    public void Insert(Node node) {
        node.setSizeRank(1); //FOR RANK IN ID TREE
        //if (!isBalanced) {
           // InitInsert(node);

        //}
        Node y=this.root;
        while (y.leftChild != null  ) {//while y is not a leaf
            if (!(y.leftChild.getKey() instanceof minusInf) && node.getKey().isSmaller(y.leftChild.getKey()))
                y = y.leftChild;
            else if (y.middleChild.getKey() instanceof inf)
                y = y.middleChild;
            else if(node.getKey().isSmaller(y.middleChild.getKey()))
                y = y.middleChild;
            else y = y.rightChild;
        }
        Node x;
        x = y.parent;
        Node z = Insert_And_Split(x, node);
        while (x != root) {
            x = x.parent;
            if (z != null)
                z = Insert_And_Split(x, z);
            else {
                UpdateKey(x);
                updateNodeRunners(x);}///update total min and avg
        }
        if (z != null) {
            Node w = new Node();
            SetChildren(w, x, z, null);
            this.root = w;
        }
        numberOfLeaves++;
    }

    /**
     * removes a node from the tree - removes link to the parent and link from the parent
     * @param node
     */

    private Node BorrowOrMerge(Node node) {
        Node parent = node.parent;
        Node x;
        if (node == parent.leftChild) {
            x = parent.middleChild;
            if (x.rightChild != null) { //borrow from middle
                SetChildren(node, node.leftChild, x.leftChild, null);
                SetChildren(x, x.middleChild, x.rightChild, null);
            } else { // can't borrow from middle. pass the problem to higher level
                SetChildren(x, node.leftChild, x.leftChild, x.middleChild);
                // deleteNode(node);
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
                // deleteNode(node);
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
                //deleteNode(node);
                SetChildren(parent, parent.leftChild, x, null);
            }
            return parent;
        }
    }

    /**
     * working under the assumption that only leaves are getting deleted
     *
     * @param node
     */
    public void DeleteLeaf(Node node) {
        Node parent = node.getParent();
        if (node == parent.leftChild)
            SetChildren(parent, parent.middleChild, parent.rightChild, null);
        else if (node == parent.middleChild) {
            SetChildren(parent, parent.leftChild, parent.rightChild, null);
        } else SetChildren(parent, parent.leftChild, parent.middleChild, null);
        //deleteNode(node);
        numberOfLeaves--;
        while (parent != null) {
            if (parent.middleChild != null) {
                UpdateKey(parent);
                updateNodeRunners(parent);// UPDATE THE TOTAL MIN AND AVG
                parent = parent.parent;
            } else {
                if (parent != this.root) {
                    parent = BorrowOrMerge(parent);
                } else {//it means we need to delete the root
                    this.root = parent.leftChild;
                    parent.leftChild.parent = null;
                    //deleteNode(parent);
                    return;
                }
            }
        }
    }

    /**
     * search a runner in the tree
     *
     * @param x  the root of the tree
     * @param key the key we eat to find
     * @return the node that represents the runner(can be modify to the runner id itseld easily)
     */
    public Node Search(Node x, RunnerID key) {
        if (x.leftChild == null) {//x is a leaf
            if(x.getKey() instanceof minusInf || x.getKey() instanceof inf)
                return null;
            else if (!(x.getKey().isSmaller(key) || key.isSmaller(x.getKey())))
                return x;
            else return null;
        }
        if (!(x.leftChild.getKey() instanceof minusInf)) {
            if (key.isSmaller(x.leftChild.getKey())
                    || !(x.leftChild.getKey().isSmaller(key) || key.isSmaller(x.leftChild.getKey()))) {
                return Search(x.leftChild, key);
            }
        } if (x.middleChild.getKey() instanceof inf) {
            return Search(x.middleChild, key);
        } else if (key.isSmaller(x.middleChild.getKey())
                || !(x.middleChild.getKey().isSmaller(key) || key.isSmaller(x.middleChild.getKey()))) {
            return Search(x.middleChild, key);
        } else {
            return Search(x.rightChild, key);
        }
    }

    /**
     * find the runner with the minmial run time from the children of the node
     *breakes ties with the runnerID
     * @param node the node we check
     * @return the runner with the minimal run time from the children of the node
     */
    public Node findMinRunner(Node node) { //TODO: FIXES THE CHILDREN PROBLEMS
        Node minimalRunner;
        RunnerID x=node.leftChild.getKey();
        if(node.middleChild==null)
            return node.leftChild;
        RunnerID y=node.middleChild.getKey();
//        RunnerID z=node.rightChild.getKey();
        if(node.leftChild.getMinimalRunTime()==node.middleChild.getMinimalRunTime())
            if(variantIsSmaller(x,y) && !(x instanceof minusInf))
                minimalRunner=node.leftChild;
            else minimalRunner=node.middleChild;
        else if (node.leftChild.getMinimalRunTime() < node.middleChild.getMinimalRunTime())
            minimalRunner = node.leftChild;
        else minimalRunner = node.middleChild;
        if (node.rightChild != null){
            if(node.rightChild.getMinimalRunTime()==minimalRunner.getMinimalRunTime()) {
                if (variantIsSmaller(node.rightChild.getKey(),minimalRunner.getKey()) && !(node.rightChild.getKey() instanceof minusInf))
                    minimalRunner = node.rightChild;
            }
            else if (node.rightChild.getMinimalRunTime() < minimalRunner.getMinimalRunTime())
                    minimalRunner = node.rightChild;
        }
        return minimalRunner;
    }
    /**
     * find the runner with the minmial AVG run time from the children of the node
     *breakes ties with the runnerID
     * @param node the node we check
     * @return the runner with the minimal AVG run time from the children of the node
     */
    public Node minRunnerAvgRunTime(Node node) {
        Node minimalAvgRunner;
        RunnerID x=node.leftChild.getKey();
        if(node.middleChild==null)
            return node.leftChild;
        RunnerID y=node.middleChild.getKey();
//        RunnerID z=node.rightChild.getKey();
        if(node.leftChild.getAvgRunTime()==node.middleChild.getAvgRunTime())
            if(variantIsSmaller(x,y) && !(x instanceof minusInf))
                minimalAvgRunner =node.leftChild;
            else minimalAvgRunner =node.middleChild;
        else if (node.leftChild.getAvgRunTime() < node.middleChild.getAvgRunTime())
            minimalAvgRunner = node.leftChild;
        else minimalAvgRunner = node.middleChild;
        if (node.rightChild != null){
            if(node.rightChild.getAvgRunTime()== minimalAvgRunner.getAvgRunTime()) {
                if (variantIsSmaller(node.rightChild.getKey(),minimalAvgRunner.getKey()) && !(node.rightChild.getKey() instanceof minusInf)) {
                    minimalAvgRunner = node.rightChild;
                }
                else if (node.rightChild.getAvgRunTime() < minimalAvgRunner.getAvgRunTime())
                    minimalAvgRunner = node.rightChild;
            }
        }
        return minimalAvgRunner;
    }

    public int Rank(Node x) {
        int rank =1;
        Node y = x.getParent();
        while (y != null) {
            if (x == y.getMiddleChild()) {
                rank = rank + y.getLeftChild().getSizeRank();
            } else if (x == y.getRightChild()) {
                rank = rank + y.getLeftChild().getSizeRank() +y.getMiddleChild().getSizeRank();
            }
            x = y;
            y = y.getParent();
        }
        return rank;
    }

    /**
     * a function in order to update the IDTree (all the runners anccestors)
     * after add/delete run from runner.called only from the race
     * @param node the runner we updated
     */
    public void updateWhen_Add_Or_Delete_Run(Node node){
        Node parent=node.getParent();
        while(parent!=null){
            updateNodeRunners(parent);
            parent=parent.parent;
        }
    }
    public void printTree(){
        recursivePrint(this.root);
    }
    private void recursivePrint(Node node){
        if (node.getLeftChild() == null){System.out.print(node.getKey() + " "); return;}
        recursivePrint(node.getLeftChild());
        if(node.getMiddleChild() !=null){recursivePrint(node.getMiddleChild());}
        if(node.getRightChild() !=null){recursivePrint(node.getRightChild());}
    }
}