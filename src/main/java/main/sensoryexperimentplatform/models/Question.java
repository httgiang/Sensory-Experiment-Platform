package main.sensoryexperimentplatform.models;
import main.sensoryexperimentplatform.controllers.SoundSingleton;

public class Question extends Stage implements Model,containerObject {
    private String question;
    private String leftButtonText;
    private String helpText;
    private String rightButtonText;
    private String leftButtonValue;
    private String rightButtonValue;
    private boolean alert;
    private Sound sound;
    private String result;

    public Question(String title, String content) {
        super(title, content);
    }

    public Question(String question,String leftButtonText, String rightButtonText, String leftButtonValue, String rightButtonValue,
                    String helpText, boolean alert) {
        super(question,rightButtonText);
        this.question = question;
        this.leftButtonText = leftButtonText;
        this.rightButtonText = rightButtonText;
        this.leftButtonValue = leftButtonValue;
        this.rightButtonValue = rightButtonValue;
        this.helpText = helpText;
        this.alert = alert;
        result = null;
        sound = SoundSingleton.getInstance();
    }

    public Question(Question o) {
        super(o.getQuestion(),o.getRightButtonText());
        question = o.getQuestion();
        leftButtonText = o.getLeftButtonText();
        rightButtonText = o.getRightButtonText();
        leftButtonValue = o.getLeftButtonValue();
        rightButtonValue = o.getRightButtonValue();
        helpText = o.getHelpText();
        alert = o.isAlert();
        result = null;
        sound = SoundSingleton.getInstance();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }





    public String getType() {
        return "Question";
    }
    public String getRightButtonText() {
        return rightButtonText;
    }

    public void setRightButtonText(String rightButtonText) {
        this.rightButtonText = rightButtonText;
    }

    public String getLeftButtonText() {
        return leftButtonText;
    }

    public void setLeftButtonText(String leftButtonText) {
        this.leftButtonText = leftButtonText;
    }

    public String getLeftButtonValue() {
        return leftButtonValue;
    }

    public void setLeftButtonValue(String leftButtonValue) {
        this.leftButtonValue = leftButtonValue;
    }

    public String getRightButtonValue() {
        return rightButtonValue;
    }

    public void setRightButtonValue(String rightButtonValue) {
        this.rightButtonValue = rightButtonValue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void playSound(){
        sound.playSound("boop");
    }

    @Override
    public String toString() {
        return "questionStage(\"" + question + "\",\"" + leftButtonText +
                "\",\"" + rightButtonText +  "\",\"" + leftButtonValue +  "\",\"" + rightButtonValue +  "\",\"" + helpText + "\",\"" + alert + "\")";
    }
}
