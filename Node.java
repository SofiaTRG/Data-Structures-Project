public class Node {
     Node parent;
     Node leftChild;
     Node middleChild;
     Node rightChild;
     RunnerID key;
    float secondKey;

    private RunnerID FastestRunnerAvg;//pointer to the runner with the minimal avg run time

    private RunnerID FastestRunnerMin;//pointer to the runner with the minimal run time

    private int size;// number of children
    private TimeTree runs;
    private float MinimalRunTime;
    private float AvgRunTime;
    private int numRuns;
    private int sizeRank = 0; // FOR THE RANK IN ID TREE

    // Constructors
    public Node(Node parent, Node leftChild, Node middleChild, Node rightChild, RunnerID key) {
        this.parent = parent;
        this.leftChild = leftChild;
        this.middleChild = middleChild;
        this.rightChild = rightChild; //In TwoThreeTree always null
        this.key = key;
        this.numRuns = 0;
        this.runs = new TimeTree();
        this.MinimalRunTime = Float.MAX_VALUE;
        this.AvgRunTime = Float.MAX_VALUE;
        this.FastestRunnerMin=this.key;
        this.FastestRunnerAvg=this.key;
    }
    public Node() {
        this(null, null, null, null, null);
    }
    public Node(RunnerID key) {
        this(null,null,null,null,key);
    }

    // Getters and Setters
    public TimeTree getRuns() {
        return runs;
    }
    public RunnerID getFastestRunnerAvg() {
        return FastestRunnerAvg;
    }

    public RunnerID getFastestRunnerMin() {
        return FastestRunnerMin;
    }


    public float getMinimalRunTime() {
        return MinimalRunTime;
    }

    public float getAvgRunTime() {
        return AvgRunTime;
    }

    public void setFastestRunnerAvg(RunnerID fastestRunnerAvg) {
        FastestRunnerAvg = fastestRunnerAvg;
    }

    public void setFastestRunnerMin(RunnerID fastestRunnerMin) {
        FastestRunnerMin = fastestRunnerMin;
    }

    public void setMinimalRunTime(float minimalRunTime) {
        MinimalRunTime = minimalRunTime;
    }

    public void setAvgRunTime(float avgRunTime) {
        AvgRunTime = avgRunTime;
    }
    public Node getParent() {
        return parent;
    }
    public void setParent(Node parent) {
        this.parent = parent;
    }
    public Node getLeftChild() {
        return leftChild;
    }
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getMiddleChild() {
        return middleChild;
    }

    public void setMiddleChild(Node middleChild) {
        this.middleChild = middleChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public RunnerID getKey() {
        return this.key;
    }

    public void setKey(RunnerID key) {
        this.key = key;
    }

    public int getSizeRank() { //FOR RANK IN ID TREE
        return sizeRank;
    }

    public void setSizeRank(int sizeRank) { //FOR RANK IN ID TREE
        this.sizeRank = sizeRank;
    }

    public void calculateAverage(float time) {
        if (AvgRunTime == Float.MAX_VALUE) this.setAvgRunTime((float) 0.0);
        this.AvgRunTime =  ((AvgRunTime * numRuns) + time) / (numRuns + 1);
        this.numRuns += 1;
    }

    public void calculateAverageAfterDelete(float time) {
        this.AvgRunTime = ((AvgRunTime * numRuns) - time) / (numRuns - 1);
        this.numRuns -= 1;
        if (numRuns == 0) this.setAvgRunTime(Float.MAX_VALUE);
    }

    public void calculateMin() {
        this.MinimalRunTime =this.runs.getRoot().getMin();
    }
}