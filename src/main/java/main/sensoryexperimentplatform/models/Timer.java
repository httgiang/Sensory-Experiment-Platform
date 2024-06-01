package main.sensoryexperimentplatform.models;

public class Timer extends Stage {
    private boolean alert;
    private boolean isRunning = true;
    private long timeToWait;
    private String instruction;
    private final long durationInMillis = timeToWait * 60 * 1000;
    public Timer(String time, String instruction, boolean alert) {
        super(time, instruction);
        this.instruction = instruction;
        timeToWait = Long.parseLong(time);
        this.alert = alert;
    }
    public Timer(String instruction, String time) {
        super(instruction, time);
        timeToWait = Long.parseLong(time);
    }

    public void start(){
        if (isRunning){
            System.out.println(instruction +" Please wait for"+ timeToWait);
            try {
                Thread.sleep(durationInMillis);
                isRunning = false;
                System.out.println("Ended counting");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isRunning = true;
        }
    }

    public long getTimeToWait() {
        return timeToWait;
    }

    public void setTimeToWait(long timeToWait) {
        this.timeToWait = timeToWait;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getFormattedElapsed() {
        return String.format(String.valueOf(timeToWait));
    }
    public String getType(){
        return "Wait";
    }

    @Override
    public String toString() {
        return "wait(\""+ getFormattedElapsed() + "\",\"" + this.content  + "\",\"" + alert + "\")";
    }
}
