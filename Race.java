public class Race {
     TwoThreeTree RaceTree;
     MinTree minTree;
     MinTree AVGTree;

    public Race() {
        init();
    }
    public void init()
    {
        RaceTree = new TwoThreeTree();
        minTree = new MinTree();
        AVGTree = new MinTree();
    }
    public void addRunner(RunnerID id)
    {
        if(id==null)
            throw new IllegalArgumentException();
        Node temp = RaceTree.Search(RaceTree.getRoot(), id);
        if(temp==null) {// it means it is a new runner and is not already exist
            Node temp2=new Node(id);
            RaceTree.Insert(temp2);
            RaceTree.updateWhen_Add_Or_Delete_Run(temp2);//update the Race ID tree after changing runner
            minTree.Insert(id, Float.MAX_VALUE);
            AVGTree.Insert(id, Float.MAX_VALUE);
        }else throw new IllegalArgumentException();
    }

    public void removeRunner(RunnerID id) {
        if(id==null)
            throw new IllegalArgumentException();
        Node temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {//it means the runner is already exists in the race
            RaceTree.DeleteLeaf(temp);
            minTree.Delete(id, temp.getMinimalRunTime());
            AVGTree.Delete(id, temp.getAvgRunTime());
        } else throw new IllegalArgumentException();
    }

    public void addRunToRunner(RunnerID id, float time) {
        Node temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {// it means that the runner is already exists in the race
            Run runTemp = temp.getRuns().findNode(temp.getRuns().getRoot(), time);
            if (runTemp==null) {//it means that it is a new run we add to the runner and isnt already exists
                float Pmin = temp.getMinimalRunTime();
                float PAVG = temp.getAvgRunTime();
                temp.getRuns().Insert(new Run(time));
                temp.calculateMin();
                temp.calculateAverage(time);
                RaceTree.updateWhen_Add_Or_Delete_Run(temp);//update the Race ID tree after changing runner
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
            }else throw new IllegalArgumentException();
        }else throw new IllegalArgumentException();
    }

    public void removeRunFromRunner(RunnerID id, float time) {
        Node temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {// it means that the runner is already exists in the race
            Run runTemp = temp.getRuns().findNode(temp.getRuns().getRoot(), time);
            if (runTemp!=null) {// it means we are trying to remove a run that exists
                float Pmin = temp.getMinimalRunTime();
                float PAVG = temp.getAvgRunTime();
                temp.getRuns().Delete(runTemp);
                temp.calculateMin();
                temp.calculateAverageAfterDelete(time);
                RaceTree.updateWhen_Add_Or_Delete_Run(temp);//update the Race ID tree after changing runner
                // delete the id in min and avg trees and insert again
                // (the min and avg can be changed when we put new times)
                // we find the ids by their previous keys
                minTree.Delete(id, Pmin);
                minTree.Insert(id, temp.getMinimalRunTime());
                AVGTree.Delete(id, PAVG);
                AVGTree.Insert(id, temp.getAvgRunTime());

                // find the leaves in min and AVG tree in the prev tree(if we removed run or runner)
                NodeFloat prevMIN = minTree.findNode(minTree.getRoot(), Pmin);
                NodeFloat prevAVG = AVGTree.findNode(AVGTree.getRoot(), PAVG);

                // update the sizes of ancestors in the prev tree(if we removed run or runner)
                if(prevMIN!=null)//maybe we deleted this node!!!
                  minTree.updateSizeFathers(prevMIN);
                if(prevAVG!=null)//maybe we deleted this node!!!
                  AVGTree.updateSizeFathers(prevAVG);


                // find the leaves in min and AVG tree
                NodeFloat newMIN = minTree.findNode(minTree.getRoot(), temp.getMinimalRunTime());
                NodeFloat newAVG = AVGTree.findNode(AVGTree.getRoot(), temp.getAvgRunTime());

                // update the sizes of ancestors
                minTree.updateSizeFathers(newMIN);
                AVGTree.updateSizeFathers(newAVG);

            } else throw new IllegalArgumentException();

        } else throw new IllegalArgumentException();
    }


    public RunnerID getFastestRunnerAvg()
    {
        if(RaceTree.getNumberOfLeaves()==0)// it means that the race is empty
            throw new IllegalArgumentException();
        else return RaceTree.getRoot().getFastestRunnerAvg();

    }

    public RunnerID getFastestRunnerMin()
    {
        if(RaceTree.getNumberOfLeaves()==0)// it means that the race is empty
            throw new IllegalArgumentException();
        else return RaceTree.getRoot().getFastestRunnerMin();
    }

    public float getMinRun(RunnerID id)
    {
        if(RaceTree.getNumberOfLeaves()==0)// it means that the race is empty
            throw new IllegalArgumentException();
        // find the node of runner in ID tree, then get his min time
        Node tempRoot = RaceTree.getRoot();
        Node tempNode = RaceTree.Search(tempRoot, id);
        return tempNode.getMinimalRunTime();
    }
    public float getAvgRun(RunnerID id) {
        if(RaceTree.getNumberOfLeaves()==0)// it means that the race is empty
            throw new IllegalArgumentException();
        // find the node of runner in ID tree, then get his ang time
        return RaceTree.Search(RaceTree.getRoot(), id).getAvgRunTime();
    }

    public int getRankAvg(RunnerID id)
    {
        if(RaceTree.getNumberOfLeaves()==0)// it means that the race is empty
            throw new IllegalArgumentException();
        Node temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {
            float tempAVG = temp.getAvgRunTime();
            NodeFloat tempFloat = AVGTree.findNode(AVGTree.getRoot(), tempAVG);
            Node tempRoot = tempFloat.getTree().getRoot();;
            Node tempID = tempFloat.getTree().Search(tempRoot, id);
            return AVGTree.Rank(tempFloat) + tempFloat.getTree().Rank(tempID)-1 ;
        }else throw new IllegalArgumentException();
    }

    public int getRankMin(RunnerID id)
    {
        if(RaceTree.getNumberOfLeaves()==0)// it means that the race is empty
            throw new IllegalArgumentException();
        Node temp = RaceTree.Search(RaceTree.getRoot(), id);
        if (temp!=null) {
            float tempMIN = temp.getMinimalRunTime();
            NodeFloat tempFloat = minTree.findNode(minTree.getRoot(), tempMIN);
            Node tempRoot = tempFloat.getTree().getRoot();
            Node tempID = tempFloat.getTree().Search(tempRoot, id);
            return minTree.Rank(tempFloat) + tempFloat.getTree().Rank(tempID) -1;
        }throw new IllegalArgumentException();
    }
}