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
            temp.calculateMin();
            temp.calculateAverage(time);
            // delete the id in min and avg trees and insert again
            // (the min and avg can be changed when we put new times)
            // we find the ids by their previous keys
            minTree.Delete(id, Pmin);
            minTree.Insert(id, temp.getMinimalRunTime());
            AVGTree.Delete(id, PAVG);
            AVGTree.Insert(id, temp.getAvgRunTime());

            // find the leaves in min and AVG tree
            NodeFloat newMIN = minTree.findNode(minTree.getRoot(), temp.getMinimalRunTime());
            NodeFloat newAVG = AVGTree.findNode(AVGTree.getRoot(), temp.getAvgRunTime());

            // update the sizes of ancestors
            minTree.updateSizeFathers(newMIN);
            AVGTree.updateSizeFathers(newAVG);
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
                temp.calculateMin();
                temp.calculateAverageAfterDelete(time);
                // delete the id in min and avg trees and insert again
                // (the min and avg can be changed when we put new times)
                // we find the ids by their previous keys
                minTree.Delete(id, Pmin);
                minTree.Insert(id, temp.getMinimalRunTime());
                AVGTree.Delete(id, PAVG);
                AVGTree.Insert(id, temp.getAvgRunTime());

                // find the leaves in min and AVG tree
                NodeFloat newMIN = minTree.findNode(minTree.getRoot(), temp.getMinimalRunTime());
                NodeFloat newAVG = AVGTree.findNode(AVGTree.getRoot(), temp.getAvgRunTime());

                // update the sizes of ancestors
                minTree.updateSizeFathers(newMIN);
                AVGTree.updateSizeFathers(newAVG);

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
        Node<RunnerID> temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {
            float tempAVG = temp.getAvgRunTime();
            NodeFloat tempFloat = AVGTree.findNode(AVGTree.getRoot(), tempAVG);
            Node <RunnerID> tempRoot = tempFloat.getTree().getRoot();;
            Node <RunnerID> tempID = tempFloat.getTree().Search(tempRoot, id);
            return AVGTree.Rank(tempFloat) + tempFloat.getTree().Rank(tempID);
        }
        return 0; //TODO: ERROR MASSAGE NO SUCH ID
    }

    public int getRankMin(RunnerID id)
    {
        Node<RunnerID> temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {
            float tempMIN = temp.getMinimalRunTime();
            NodeFloat tempFloat = minTree.findNode(minTree.getRoot(), tempMIN);
            Node <RunnerID> tempRoot = tempFloat.getTree().getRoot();;
            Node <RunnerID> tempID = tempFloat.getTree().Search(tempRoot, id);
            return minTree.Rank(tempFloat) + tempFloat.getTree().Rank(tempID);
        }
        return 0; //TODO: ERROR MASSAGE NO SUCH ID
    }
}
