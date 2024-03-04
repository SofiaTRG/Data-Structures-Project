public class Run extends Node{
    private float time;

    public Run(float time) {this.time=time;}

    public void setTime(float time) {
        if (time<0) {throw new IllegalArgumentException("cannot be negative time for a run");
        } else {
            this.time = time;
        }
    }

    public float getTime() {return time;}
}
