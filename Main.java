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
        RunnerIDInt id1 = new RunnerIDInt(3);
        RunnerIDInt id2 = new RunnerIDInt(5);
        Race race = new Race();
        race.addRunner(id1);
        race.addRunner(id2);
        race.addRunToRunner(id1, (float)118.0);
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

        race.addRunner(id3);
        race.addRunner(id4);
        race.addRunner(id5);
        race.addRunToRunner(id1, (float)62.0);
        race.addRunToRunner(id2, (float)20.0);
        race.addRunToRunner(id2, (float)32.0);
        race.addRunToRunner(id3, (float)1.0);
        race.addRunToRunner(id4, (float)1.0);
        System.out.println("the mintree is:" );
        race.minTree.printTree();
        System.out.println("the avgtree is:" );
        race.AVGTree.printTree();
        race.addRunToRunner(id5, (float)37.0);
        race.addRunToRunner(id5, (float)42.0);
        System.out.println("the mintree is:" );
        race.minTree.printTree();
        System.out.println("the avgtree is:" );
        race.AVGTree.printTree();
        race.addRunner(id6);
        race.addRunner(id7);
        race.addRunner(id8);
        race.addRunner(id9);
        race.addRunToRunner(id6, (float)10.0);
        race.addRunToRunner(id6, (float)12.0);
        race.addRunToRunner(id7, (float)0.0);
        race.addRunToRunner(id7, (float)1000000.0);
        race.addRunToRunner(id7, (float)3.0);
        race.addRunToRunner(id8, (float)100.0);
        race.addRunToRunner(id8, (float)170.0);
        race.addRunToRunner(id8, (float)0.0);

        // Testing all functions
        System.out.println("\nTest Results:");
        System.out.println("The min running time of " + id2.toString() + " is " + race.getMinRun(id2));
        System.out.println("The avg running time of " + id1.toString() + " is " + race.getAvgRun(id1));
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());

        System.out.println("The rank (min) of " + id1.toString() + " is " + race.getRankMin(id1));
        System.out.println("The rank (avg) of " + id1.toString() + " is " + race.getRankAvg(id1));
        System.out.println("The rank (avg) of " + id6.toString() + " is " + race.getRankAvg(id6));
        System.out.println("The rank (avg) of " + id8.toString() + " is " + race.getRankAvg(id8));
        System.out.println("The rank (avg) of " + id2.toString() + " is " + race.getRankAvg(id2));
        System.out.println("The rank (avg) of " + id7.toString() + " is " + race.getRankMin(id7));
        System.out.println("The rank (min) of " + id5.toString() + " is " + race.getRankMin(id5));
        System.out.println("The rank (avg) of " + id5.toString() + " is " + race.getRankAvg(id5));

        race.removeRunFromRunner(id1, (float)62.0); // Removing a run
        System.out.println("The min running time of " + id1.toString() + " after removing a run is " + race.getMinRun(id1));
        System.out.println("The avg running time of " + id1.toString() + " after removing a run is " + race.getAvgRun(id1));

        System.out.println("Tree before delete id1:");
        race.RaceTree.printTree();

        race.removeRunner(id1); // Removing a runner

        System.out.println("Tree after delete id1:");
        race.RaceTree.printTree();

        System.out.println("The min running time of " + id2.toString() + " is " + race.getMinRun(id2));
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());

        System.out.println("The rank (min) of " + id3.toString() + " is " + race.getRankMin(id3));
        System.out.println("The rank (avg) of " + id3.toString() + " is " + race.getRankAvg(id3));
//        System.out.println("The rank (avg) of " + id1.toString() + " is " + race.getRankAvg(id9));
        System.out.println("The rank (avg) of " + id8.toString() + " is " + race.getRankAvg(id8));
        System.out.println("The rank (avg) of " + id2.toString() + " is " + race.getRankAvg(id2));
        System.out.println("The rank (avg) of " + id7.toString() + " is " + race.getRankMin(id7));

    }
}
