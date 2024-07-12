public class minusInf extends RunnerID{

    @Override
    public boolean isSmaller(RunnerID other) {
        return true;//every other key is bigger than minusInf
    }

    @Override
    public String toString() {
        return null;
    }
}
