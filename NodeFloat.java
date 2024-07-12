public class NodeFloat {
    private float Key;
    private TwoThreeTree tree=new TwoThreeTree();
    private NodeFloat parent;
    private NodeFloat leftChild;
    private NodeFloat middleChild;
    private NodeFloat rightChild;
    private int size=0;//**

    public NodeFloat(float key, NodeFloat parent, NodeFloat leftChild, NodeFloat middleChild, NodeFloat rightChild) {
        Key = key;
        this.parent = parent;
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild;
    }
    public NodeFloat(float key) {
        new NodeFloat(key,null,null,null,null);}

    public NodeFloat(float key, TwoThreeTree tree) {
        Key = key;
        this.tree = tree;
    }

    public float getKey() {
        return Key;
    }

    public TwoThreeTree getTree() {
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

    public void setTree(TwoThreeTree tree) {
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

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}