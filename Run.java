public class Run {
    private float time;//key
    private Run leftChild;
    private Run middleChild;
    private Run rightChild;
    private Run parent;


    // Constructors
    public Run(){
        this(null, null, null, null, 0);
    }

    public Run(Run parent, Run leftChild, Run middleChild, Run rightChild, float time) {
        this.parent = parent;
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild; //In TwoThreeTree always null
        this.time=time;
    }

    // no right child
    public Run(Run parent, Run leftChild, Run middleChild, float time) {
        this(parent, leftChild, middleChild, null, time);
    }

    // no children
    public Run(Run parent, float time) {
        this(parent, null, null, null, time);
    }

    public Run(float time) {
        this(null, null, null, null, time);
    }


    // Getters and Setters
    public Run getParent() {
        return parent;
    }

    public void setParent(Run parent) {
        this.parent = parent;
    }

    public Run getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Run leftChild) {
        this.leftChild = leftChild;
    }

    public Run getMiddleChild() {
        return middleChild;
    }

    public void setMiddleChild(Run middleChild) {
        this.middleChild = middleChild;
    }

    public Run getRightChild() {
        return rightChild;
    }

    public void setRightChild(Run rightChild) {
        this.rightChild = rightChild;
    }

    public float getTime() {
        return this.time;
    }

    public void setTime(float time) {
        if (time<0) {throw new IllegalArgumentException("cannot be negative time for a run");
        } else {
            this.time = time;
        }
    }

}
