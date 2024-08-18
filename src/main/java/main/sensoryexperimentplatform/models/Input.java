package main.sensoryexperimentplatform.models;

import main.sensoryexperimentplatform.controllers.SoundSingleton;

public class Input extends Stage{
    private String buttonText;
    private String helpText;
    private String questionText;
    private Sound sound;
    private boolean alert;


    private String result;

    public Input(String title, String content) {
        super(title, content);
    }
    public Input(String questionText, String buttonText, String helpText, boolean alert) {
        super(questionText, buttonText);
        this.buttonText = buttonText;
        this.helpText = helpText;
        this.questionText = questionText;
        this.alert = alert;
        result = null;
        this.sound = SoundSingleton.getInstance();
    }

    public Input(Input o) {
        super(o.getQuestionText(), o.getButtonText());
        buttonText = o.getButtonText();
        helpText = o.getHelpText();
        questionText = o.getQuestionText();
        alert = o.isAlert();
        result = null;
        this.sound = SoundSingleton.getInstance();
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
    public String getType(){
        return "Input";
    }

    public String getHelpText() {return helpText;}

    public void setHelpText(String helpText) {this.helpText = helpText;}

    public String getQuestionText() {return questionText;}

    public void setQuestionText(String questionText) {this.questionText = questionText;}

    public void playSound(){
        sound.playSound("boop");
    }


    @Override
    public String toString() {
        return "inputStage(\"" + questionText + "\",\"" + buttonText + "\",\"" +
                helpText + "\",\""+ alert + "\")";
    }

}