public class Node<T extends RunnerID> {
    Node<T> parent;
    Node<T> leftChild;
    Node<T> middleChild;
    Node<T> rightChild;
    T key;
    //second key is float
    float secondKey;
    T value; // Runner or Run(???)
    int height;
    private T FastestRunnerAvg;//pointer to the runner with the minimal avg run time

    private T FastestRunnerMin;//pointer to the runner with the minimal run time

    private int size;// number of children
    private TimeTree runs;
    private float MinimalRunTime;
    private float AvgRunTime;
    private int sizeRank = 0; // FOR THE RANK IN ID TREE

    public T getFastestRunnerAvg() {
        return FastestRunnerAvg;
    }

    public T getFastestRunnerMin() {
        return FastestRunnerMin;
    }

    public int getSize() {
        return size;
    }

    public float getMinimalRunTime() {
        return MinimalRunTime;
    }

    public float getAvgRunTime() {
        return AvgRunTime;
    }

    public void setFastestRunnerAvg(T fastestRunnerAvg) {
        FastestRunnerAvg = fastestRunnerAvg;
    }

    public void setFastestRunnerMin(T fastestRunnerMin) {
        FastestRunnerMin = fastestRunnerMin;
    }

    public void setMinimalRunTime(float minimalRunTime) {
        MinimalRunTime = minimalRunTime;
    }

    public void setAvgRunTime(float avgRunTime) {
        AvgRunTime = avgRunTime;
    }

    // Constructors
    public Node(Node<T> parent, Node<T> leftChild, Node<T> middleChild, Node<T> rightChild, T key, float secondKey) {
        this.parent = parent;
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild; //In TwoThreeTree always null
        this.key = key;
        this.secondKey = secondKey;
    }

    /**
     * copy consturctor for the MinTree and AvgTree
     * @param
     */
    //public Node(Node<T> nodeToCopy){
    //   new Node<T>(null, null, null, null, null, nodeToCopy.getKey(), nodeToCopy.getSecondKey());
    // }

    public TimeTree getRuns() {
        return runs;
    }



    public Node(T key, float secondKey) {
        this(null, null, null, null, key, secondKey);
    }

    //public Node(int key, int secondKey) {
    //    this(null, null, null, null, null, key, secondKey);
    //}

    public Node() {
        this(null, null, null, null, null, Float.MIN_VALUE);
    }

    public Node(T key) {
        this.key = key;
    }


    // Getters and Setters
    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getMiddleChild() {
        return middleChild;
    }

    public void setMiddleChild(Node<T> middleChild) {
        this.middleChild = middleChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public T getKey() {
        return this.key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public float getSecondKey() {
        return this.secondKey;
    }

    public void setSecondKey(int key) {
        this.secondKey = key;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T key) {
        this.value = key;
    }

    public int getSizeRank() { //FOR RANK IN ID TREE
        return sizeRank;
    }

    public void setSizeRank(int sizeRank) { //FOR RANK IN ID TREE
        this.sizeRank = sizeRank;
    }
}
