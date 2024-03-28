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
        } //TODO: ERROR MASSAGE NOT SUCH ID
    }

    public void addRunToRunner(RunnerID id, float time)
    {
        Node<RunnerID> temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {
            float Pmin = temp.getMinimalRunTime();
            float PAVG = temp.getAvgRunTime();
            temp.getRuns().Insert(new Run(time));
            // delete the id in min and avg trees and insert again
            // (the min and avg can be changed when we put new times)
            // we find the ids by their previous keys
            minTree.Delete(id, Pmin);
            minTree.Insert(id, temp.getMinimalRunTime());
            AVGTree.Delete(id, PAVG);
            AVGTree.Insert(id, temp.getAvgRunTime());
        }
        //TODO: ERROR MASSAGE NO SUCH ID
    }

    public void removeRunFromRunner(RunnerID id, float time)
    {
        Node<RunnerID> temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {
            Run runTemp = temp.getRuns().findNode(temp.getRuns().getRoot(), time);
            if (runTemp!=null) { //TODO: SHOULD WE DELETE RUNNER WHEN WE DELETED HIS LAST RUN???
                float Pmin = temp.getMinimalRunTime();
                float PAVG = temp.getAvgRunTime();
                temp.getRuns().Delete(runTemp);

                // delete the id in min and avg trees and insert again
                // (the min and avg can be changed when we put new times)
                // we find the ids by their previous keys
                minTree.Delete(id, Pmin);
                minTree.Insert(id, temp.getMinimalRunTime());
                AVGTree.Delete(id, PAVG);
                AVGTree.Insert(id, temp.getAvgRunTime());

            } //TODO: ERROR MASSAGE RUN NOT FOUND

        }
        //TODO: ERROR MASSAGE
    }


    public RunnerID getFastestRunnerAvg()
    {
        return RaceTree.getRoot().getFastestRunnerAvg();

    }

    public RunnerID getFastestRunnerMin()
    {
        return RaceTree.getRoot().getFastestRunnerMin();
    }

    public float getMinRun(RunnerID id)
    {
        // find the node of runner in ID tree, then get his min time
        return RaceTree.Search(RaceTree.getRoot(), id).getMinimalRunTime();
    }
    public float getAvgRun(RunnerID id) {
        // find the node of runner in ID tree, then get his ang time
        return RaceTree.Search(RaceTree.getRoot(), id).getAvgRunTime();
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
