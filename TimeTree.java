public class TimeTree extends TwoThreeTree{
    //TREE FOR EACH RUNNER FOR THEIR TIMES
    //THE KEY IS TIME
    //EACH NODE HAS THE MIN TIME OF THEIR CHILDREN
    public TimeTree(Run root, boolean minmax) {
        super(root, minmax);
    }

}
