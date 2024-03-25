public class ConcreteRunnerID extends RunnerID {
    private String name;

    public ConcreteRunnerID(String name) {
        this.name = name;
    }

    @Override
    public boolean isSmaller(RunnerID other) {
        // Assume ConcreteRunnerID is smaller than other if the names are in alphabetical order
        return this.name.compareTo(((ConcreteRunnerID) other).name) < 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
