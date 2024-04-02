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
        // The ids which we will check will not necessarily be RunnerIDInt
        // This is just for the example
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
        race.addRunner(id3);
        race.addRunner(id4);
        race.addRunner(id5);
        race.addRunToRunner(id1, (float)62.0);
        race.addRunToRunner(id2, (float)20.0);
        race.addRunToRunner(id2, (float)32.0);
        race.addRunToRunner(id3, (float)1.0);
        race.addRunToRunner(id4, (float)1.0);
        race.addRunToRunner(id5, (float)37.0);
        race.addRunToRunner(id5, (float)42.0);

        // Testing all functions
        System.out.println("\nTest Results:");
        System.out.println("The min running time of " + id2.toString() + " is " + race.getMinRun(id2));
        System.out.println("The avg running time of " + id1.toString() + " is " + race.getAvgRun(id1));
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());

        System.out.println("The rank (min) of " + id1.toString() + " is " + race.getRankMin(id1));
        System.out.println("The rank (avg) of " + id1.toString() + " is " + race.getRankAvg(id1));

        System.out.println("The rank (min) of " + id5.toString() + " is " + race.getRankMin(id5));
        System.out.println("The rank (avg) of " + id5.toString() + " is " + race.getRankAvg(id5));

        race.removeRunFromRunner(id1, (float)62.0); // Removing a run
        System.out.println("The min running time of " + id1.toString() + " after removing a run is " + race.getMinRun(id1));
        System.out.println("The avg running time of " + id1.toString() + " after removing a run is " + race.getAvgRun(id1));

        race.removeRunner(id1); // Removing a runner
        System.out.println("The min running time of " + id1.toString() + " after removing the runner is " + race.getMinRun(id1));
        System.out.println("The avg running time of " + id1.toString() + " after removing the runner is " + race.getAvgRun(id1));

    }
}
