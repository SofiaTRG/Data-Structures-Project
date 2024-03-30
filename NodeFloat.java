public class NodeFloat {
    private float Key;
    private TwoThreeTree<RunnerID> tree;
    NodeFloat parent;
    NodeFloat leftChild;
    NodeFloat middleChild;
    NodeFloat rightChild;

    public NodeFloat(float key, NodeFloat parent, NodeFloat leftChild, NodeFloat middleChild, NodeFloat rightChild) {
        Key = key;
        this.parent = parent;
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild;
    }
    public NodeFloat(float key) {
        new NodeFloat(key,null,null,null,null);}

    public NodeFloat(float key, TwoThreeTree<RunnerID> tree) {
        Key = key;
        this.tree = tree;
    }

    public float getKey() {
        return Key;
    }

    public TwoThreeTree<RunnerID> getTree() {
        return tree;
    }

    public NodeFloat getParent() {
        return parent;
    }

    public NodeFloat getLeftChild() {
        return leftChild;
    }

    public NodeFloat getMiddleChild() {
        return middleChild;
    }

    public NodeFloat getRightChild() {
        return rightChild;
    }

    public void setKey(float key) {
        Key = key;
    }

    public void setTree(TwoThreeTree<RunnerID> tree) {
        this.tree = tree;
    }

    public void setParent(NodeFloat parent) {
        this.parent = parent;
    }

    public void setLeftChild(NodeFloat leftChild) {
        this.leftChild = leftChild;
    }

    public void setMiddleChild(NodeFloat middleChild) {
        this.middleChild = middleChild;
    }

    public void setRightChild(NodeFloat rightChild) {
        this.rightChild = rightChild;
    }

    public int getNumberOfChildren() {//TODO: CHECK IF WORKING
        int count = 0;
        if (this.leftChild != null) {
            count++;
            count += this.leftChild.getNumberOfChildren();
        }
        if (this.middleChild != null) {
            count++;
            count += this.middleChild.getNumberOfChildren();
        }
        if (this.rightChild != null) {
            count++;
            count += this.rightChild.getNumberOfChildren();
        }
        return count;
    }

}
