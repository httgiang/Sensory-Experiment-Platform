package main.sensoryexperimentplatform.models;

import main.sensoryexperimentplatform.controllers.SoundSingleton;

public class Notice extends Stage implements Model {
    private String buttonText;
    private String content;
    private String helpText;
    private boolean alert;
    private Sound sound;

    public Notice(String title, String content) {
        super(title,content);

    }
    public Notice(String title, String content, String buttonText, String helpText, boolean alert){
        super(title,content);
        this.content = content;
        this.buttonText = buttonText;
        this.helpText = helpText;
        this.alert = alert;
        this.sound = SoundSingleton.getInstance();
    }

    public Notice(Notice o) {
        super(o.title,o.content);
        this.content = o.getContent();
        this.buttonText = o.getButtonText();
        this.helpText = o.getHelpText();
        this.alert = o.isAlert();
        this.sound = SoundSingleton.getInstance();
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void playSound(){
        sound.playSound("boop");
    }


    public String getType(){
        return "Notice";
    }
    @Override
    public String toString() {
        return "noticeStage(\"" + title + "\",\"" + content + "\",\"" +
                buttonText + "\",\"" + helpText + "\",\"" + alert+ "\")";
    }


}


