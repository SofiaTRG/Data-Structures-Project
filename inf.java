public class inf extends RunnerID{
    public inf() {
    }
    @Override
    public boolean isSmaller(RunnerID other) {
        return false;//every other key is little than inf
    }

    @Override
    public String toString() {
        return null;
    }
}
