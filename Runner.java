public class Runner extends Node {
    //NEED TO FIX AFTER WE HAD THE TREE OF RUNTIME
    private RunnerID runnerID;//key for runners
    private float AvgTime;
    private TimeTree Runs=null;
    private int NUMRuns;
    private int height;

    public Runner(RunnerID runnerID){
        this.runnerID=runnerID;
        AvgTime=Float.MAX_VALUE;
    }

    public RunnerID getRunnerID() {
        return runnerID;
    }

    public float getAvgTime() {
        return AvgTime;
    }

    public float getMinTime(){
        return (Runs.getRoot().getTime());//מחזיר את הזמן של השורש שבו שמור הזמן הקטן ביותר
    }

    public void addRun(float time){//סיבוכיות log(m)
        //if the tree is null
        if(Runs.getRoot() == null) {
            AvgTime = time;
        } else {
        AvgTime = calculateAverage(AvgTime, NUMRuns, time);
        NUMRuns = NUMRuns + 1;
        }
        //time is a key and a value
        Runs.Insert(new Run(time));
    }

    private static float calculateAverage(float PAVRTime, int NUMRuns, float time) {
        return ((PAVRTime * NUMRuns) + time) / (NUMRuns + 1);
    }

    //
    public void removeRun(Run run){//סיבוכיות Log(m)
        if (Runs.Search(run.getTime())){
            Runs.Delete(run.getTime());
            AvgTime = calculateAverageAfterDelete(AvgTime, NUMRuns, time);
        }else
            throw new IllegalArgumentException("WE DONT HAVE THIS RUN IN THE DATE BASE");
    }

    private static float calculateAverageAfterDelete(float PAVRTime, int NUMRuns, float time) {
        return ((PAVRTime * NUMRuns) - time) / (NUMRuns - 1);
    }

    private setHeight(int height) {
        this.height=height;
    }

}
