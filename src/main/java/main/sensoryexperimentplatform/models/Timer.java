package main.sensoryexperimentplatform.models;

import main.sensoryexperimentplatform.controllers.SoundSingleton;

public class Timer extends Stage{
    private boolean alert;
    private boolean isRunning = true;
    private long timeToWait;
    private String instruction;
    private final long durationInMillis = timeToWait * 60 * 1000;
    private Sound sound;
    public Timer(String time, String instruction, boolean alert) {
        super(time, instruction);
        this.instruction = instruction;
        timeToWait = Long.parseLong(time);
        this.alert = alert;
        this.sound = new Sound();
    }

    public Timer(String time, String instruction) {
        super(time, instruction);
        timeToWait = Long.parseLong(time);
    }

    public Timer(Timer o) {
        super(o.getTitle(), o.getContent());
        timeToWait = o.getTimeToWait();
        alert = o.isAlert();
        instruction = o.getInstruction();
        this.sound = new Sound();
    }


    public void start(){
        if(isRunning){
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
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public String getContent() {
        return content;
    }
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public long getTimeToWait() {
        return timeToWait;
    }

    public String getInstruction()
    {
        return instruction;
    }

//setter for instruction
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public boolean isAlert() {
        return alert;
    }
// setter for setAlert
    public void setAlert(boolean alert) {
        this.alert = alert;
    }
    //setter for set time
    public void setTime(String time){
        if(time.equals("")){
            timeToWait = 0;
        } else {
            timeToWait = Long.parseLong(time);
        }

    }

    public String getFormattedElapsed() {
        return String.format(String.valueOf(timeToWait));
    }
    public String getType(){
        return "Wait";
    }

    public void playAlertSound() {
        sound.playSound("boop");
    }

    @Override
    public String toString() {
        return "wait(\""+ getFormattedElapsed() + "\",\"" + instruction  + "\",\"" + alert + "\")";
    }


}
