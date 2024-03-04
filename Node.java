public class Node<T> {
    private Node father;
    private Node left;
    private Node middle;
    private Node right;

    public void Node() {
        setFather(null);
        setLeft(null);
        setMiddle(null);
        setRight(null);
    }
    public void Node(Node father, Node left, Node middle, Node right) {
        setFather(father);
        setLeft(left);
        setMiddle(middle);
        setRight(right);
    }
    public Node getFather() {
        return father;
    }

    public int getNUMSons() {
        if (left == null) {
            return 0;
        }
        if (middle == null) {
            return 1;
        }
        if (right == null) {
            return 2;
        } else {
            return 3;
        }
    }

    public void setFather(Node father) {
        this.father=father;
    }

    public void setLeft(Node left) {
        this.left=left;
    }

    public void setMiddle(Node middle) {
        this.middle=middle;
    }

    public void setRight(Node right) {
        this.right=right;
    }

}