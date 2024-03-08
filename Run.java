public class Run extends Node{
    private float time;
    private int num;// there can be two different runs with same time

    public Run(float time) {this.time=time;}

    public void setTime(float time) {
        if (time<0) {throw new IllegalArgumentException("cannot be negative time for a run");
        } else {
            this.time = time;
        }
    }

    public float getTime() {return time;}
    public void setNum(int num) { this.num=num;}
    public int getNum() { return num;}
    public void addNum() {num=+1;}
}
