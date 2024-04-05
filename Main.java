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
        TwoThreeTree<RunnerIDInt> tree=new TwoThreeTree<>();
        Node<RunnerIDInt> runner1=new Node<>(new RunnerIDInt(1));
        Node<RunnerIDInt> runner2=new Node<>(new RunnerIDInt(2));
        Node<RunnerIDInt> runner3=new Node<>(new RunnerIDInt(5));
        Node<RunnerIDInt> runner4=new Node<>(new RunnerIDInt(-19));
        Node<RunnerIDInt> runner5=new Node<>(new RunnerIDInt(3));
        Node<RunnerIDInt> runner6=new Node<>(new RunnerIDInt(900));
        Node<RunnerIDInt> runner7=new Node<>(new RunnerIDInt(9));
        Node<RunnerIDInt> runner8=new Node<>(new RunnerIDInt(-100));
        Node<RunnerIDInt> runner9=new Node<>(new RunnerIDInt(4));
        Node<RunnerIDInt> runner10=new Node<>(new RunnerIDInt(-99));
        tree.Insert(runner1);
        tree.Insert(runner2);
        tree.Insert(runner3);
        tree.Insert(runner4);
        tree.Insert(runner5);
        tree.Insert(runner6);
        tree.Insert(runner7);
        tree.Insert(runner8);
        tree.Insert(runner9);
        tree.Insert(runner10);
        tree.printTree();
        tree.DeleteLeaf(runner1);
        System.out.println(" ");
        tree.printTree();
        runner1.getRuns().Insert(new Run((float)1.0));


       RunnerIDInt id1 = new RunnerIDInt(3);
       RunnerIDInt id2 = new RunnerIDInt(5);
        Race race = new Race();
        race.addRunner(id1);
        race.addRunner(id2);
        race.addRunToRunner(id1, (float)118.0);
        race.addRunToRunner(id2, (float)12.0);

        //System.out.println("The min running time of" + id2.toString() + "is " + race.getMinRun(id2));
        //System.out.println("The avg running time of" + id1.toString() + "is " + race.getAvgRun(id1));
        //System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());


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
        race.addRunToRunner(id7, (float)1000000.0);
        race.addRunToRunner(id7, (float)3.0);
        race.addRunToRunner(id8, (float)100.0);
        race.addRunToRunner(id8, (float)170.0);
        System.out.println("The runner with the smallest minimum time is "+ race.getFastestRunnerMin());
        System.out.println("the rank of id9 "+ race.getRankMin(id9));
        race.addRunner(id10);
        System.out.println("the rank of id9 after adding id10 "+ race.getRankMin(id9));
        System.out.println("the rank of id4 after adding id10 "+ race.getRankMin(id4));
        race.removeRunFromRunner(id4,(float)1.0);
        race.removeRunFromRunner(id3,(float)1.0);
        System.out.println("the rank of id3 after remove run from runner id3+id4 "+ race.getRankMin(id3));
        race.removeRunner(id1);
        race.removeRunner(id4);
        race.addRunToRunner(id3,(float)1.0);
        System.out.println("the rank of id3 after remove runner id1 "+ race.getRankMin(id3));




    }
}
