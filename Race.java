public class Race {
    TwoThreeTree<RunnerID> RaceTree;
    MinTree minTree;
    MinTree AVGTree;
    public void init()
    {
        RaceTree = new TwoThreeTree<>();
        minTree = new MinTree();
        AVGTree = new MinTree();
    }
    public void addRunner(RunnerID id)
    {
        RaceTree.Insert(new Node<>(id));
        minTree.Insert(id, Float.MAX_VALUE);
        AVGTree.Insert(id, Float.MAX_VALUE);
    }

    public void removeRunner(RunnerID id)
    {
        Node<RunnerID> temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {
            RaceTree.DeleteLeaf(temp);
            minTree.Delete(id, temp.getMinimalRunTime());
            AVGTree.Delete(id, temp.getAvgRunTime());
        } // TODO: ERROR MASSAGE
    }

    public void addRunToRunner(RunnerID id, float time)
    {
        Node<RunnerID> temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {

        }
    }

    public void removeRunFromRunner(RunnerID id, float time)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public RunnerID getFastestRunnerAvg()
    {

        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public RunnerID getFastestRunnerMin()
    {

        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public float getMinRun(RunnerID id)
    {

        throw new java.lang.UnsupportedOperationException("not implemented");
    }
    public float getAvgRun(RunnerID id){
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public int getRankAvg(RunnerID id)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public int getRankMin(RunnerID id)
    {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }
}
