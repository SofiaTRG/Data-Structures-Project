class RunnerIDInt extends RunnerID{
    private int id;
    public RunnerIDInt(int id){
        super();
        this.id = id;
    }
    @Override
    public boolean isSmaller(RunnerID other) {
        return this.id < ((RunnerIDInt)other).id;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }


}


public class Main {
    public static void main(String[] args) {
        TwoThreeTree tree=new TwoThreeTree();
        Node runner1=new Node(new RunnerIDInt(1));
        Node runner2=new Node(new RunnerIDInt(2));
        tree.Insert(runner1);
        tree.Insert(runner2);
        tree.printTree();
        System.out.println(" ");



        RunnerIDInt id1 = new RunnerIDInt(3);
        RunnerIDInt id2 = new RunnerIDInt(5);
        Race race = new Race();
        try {
            race.getRankMin(id1);
        } catch(Exception e){
            System.out.println("exception caught");
        }
        race.addRunner(id1);
        race.addRunner(id2);
        race.addRunToRunner(id1, (float)100.0);
        race.addRunToRunner(id1, (float)20.0);
        System.out.println("The min running time of" + id2.toString() + "is " + race.getMinRun(id2));
        System.out.println("The avg running time of" + id1.toString() + "is " + race.getAvgRun(id1));
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());


        /// our tests
        RunnerIDInt id3 = new RunnerIDInt(4);
        RunnerIDInt id4 = new RunnerIDInt(-5);
        RunnerIDInt id5 = new RunnerIDInt(0);
        RunnerIDInt id6 = new RunnerIDInt(100);
        RunnerIDInt id7 = new RunnerIDInt(99);
        RunnerIDInt id8 = new RunnerIDInt(-10000000);
        RunnerIDInt id9 = new RunnerIDInt(90000000);
        RunnerIDInt id10 = new RunnerIDInt(90000001);

        race.addRunner(id3);
        race.addRunner(id4);
        race.addRunner(id5);
        race.addRunToRunner(id2, (float)60.0);
        race.addRunToRunner(id3, (float)100.0);
        race.addRunToRunner(id3, (float)10.0);
        race.addRunToRunner(id4, (float)200.0);
        race.addRunToRunner(id4, (float)200000.0);
        System.out.println("the mintree is:" );
        race.minTree.printTree();
        System.out.println("the avgtree is:" );
        race.AVGTree.printTree();
        race.addRunToRunner(id5, (float)1.0);
        System.out.println("the mintree is:" );
        race.minTree.printTree();
        System.out.println("the avgtree is:" );
        race.AVGTree.printTree();
        race.addRunner(id6);
        race.addRunner(id7);
        race.addRunner(id8);
        race.addRunner(id9);
        race.addRunToRunner(id7, (float)1.0);
        race.addRunToRunner(id7, (float)0.5);
        race.addRunToRunner(id8, (float)60.0);
        race.addRunToRunner(id9, (float)60.0);
        race.addRunner(id10);
        race.addRunToRunner(id10, (float)1.0);


        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());
        System.out.println("The runner with the smallest AVG time  is "+ race.getFastestRunnerAvg());
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());
        System.out.println("the rank of id9 "+ race.getRankMin(id9));
        System.out.println("the rank of id3  "+ race.getRankMin(id3));
        System.out.println("the rank of id4 "+ race.getRankMin(id4));
        System.out.println("the rank of id9 after adding id10 "+ race.getRankMin(id9));
        System.out.println("the rank of id4 after adding id10 "+ race.getRankMin(id4));
        System.out.println("the rank of id3 after remove run from runner id3+id4 "+ race.getRankMin(id3));
        System.out.println("the rank of id1   "+ race.getRankMin(id10));
        System.out.println("the rank of id1   "+ race.getRankAvg(id1));
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());
        System.out.println("The runner with the smallest AVG time is "+ race.getFastestRunnerAvg());
        //race.removeRunner(id7);
        //race.removeRunner(id5);
        System.out.println("The runner with the smallest minimum time after delete id7 is "+ race.getFastestRunnerMin());
        System.out.println("The runner with the smallest AVG time after delete id7 is "+ race.getFastestRunnerAvg());
        System.out.println("id3 AVG"+ race.RaceTree.Search(race.RaceTree.getRoot(),id3).getAvgRunTime());
        System.out.println("id10 AVG"+ race.RaceTree.Search(race.RaceTree.getRoot(),id10).getAvgRunTime());
        System.out.println("the avgtree is:" );
        race.AVGTree.printTree();
        System.out.println("the rank of id1 "+ race.getRankMin(id1));
        System.out.println("the rank of id2 "+ race.getRankMin(id2));
        System.out.println("the rank of id3 "+ race.getRankMin(id3));
        System.out.println("the rank of id4 "+ race.getRankMin(id4));
        System.out.println("the rank of id5 "+ race.getRankMin(id5));
        System.out.println("the rank of id6 "+ race.getRankMin(id6));
        System.out.println("the rank of id7 "+ race.getRankMin(id7));
        System.out.println("the rank of id8 "+ race.getRankMin(id8));
        System.out.println("the rank of id9 "+ race.getRankMin(id9));
        System.out.println("the rank of id10 "+ race.getRankMin(id10));
        System.out.println("the rank of id1 "+ race.getRankAvg(id1));
        System.out.println("the rank of id2 "+ race.getRankAvg(id2));
        System.out.println("the rank of id3 "+ race.getRankAvg(id3));
        System.out.println("the rank of id4 "+ race.getRankAvg(id4));
        System.out.println("the rank of id5 "+ race.getRankAvg(id5));
        System.out.println("the rank of id6 "+ race.getRankAvg(id6));
        System.out.println("the rank of id7 "+ race.getRankAvg(id7));
        System.out.println("the rank of id8 "+ race.getRankAvg(id8));
        System.out.println("the rank of id9 "+ race.getRankAvg(id9));
        System.out.println("the rank of id10 "+ race.getRankAvg(id10));
        race.removeRunFromRunner(id7,(float)0.5);
        System.out.println("the rank of id10 after remove run from runner1 "+ race.getRankMin(id10));
        System.out.println("the rank of id7 after remove run from id7 " + race.getRankMin(id7));
        race.removeRunFromRunner(id7,(float)1.0);
        System.out.println("the rank of id7 after remove run from id7 " + race.getRankMin(id7));
        race.removeRunner(id5);
        RunnerIDInt id11 = new RunnerIDInt(11);
        race.addRunner(id11);
        race.addRunToRunner(id11,(float)0.001);
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());
        try {
            race.getRankMin(id5);
        } catch(Exception e){
            System.out.println("exception caught");
        }
        System.out.println("the rank of id10 after remove run from runner1 "+ race.getRankMin(id10));
        try {
            race.removeRunFromRunner(id1,(float)7.0);
        } catch(Exception e){
            System.out.println("exception caught");
        }



    }
}
