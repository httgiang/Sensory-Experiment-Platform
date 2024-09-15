package main.sensoryexperimentplatform.models;

import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class gLMS extends Stage implements Model{
    private String title;
    private String questionText;
    private String buttonText;
    private boolean response;
    private String helpText, conducted;
    private int result;
    private boolean alert;
    private String chosenVariable;
    private Sound sound;
    private Variable variable;

    public gLMS(String title, String content){
        super(title,content);
        //default
        this.questionText = title;
        buttonText = "";
        response = false;
        this.helpText = "";
    }

    public gLMS(String title, String buttonText, String content, String helpText, boolean alert, String chosenVariable){
        super(title,content);
        this.title = title;
        this.questionText = title;
        this.buttonText = buttonText;
        this.helpText = helpText;
        this.alert = alert;
        this.chosenVariable = chosenVariable;
        this.sound = SoundSingleton.getInstance();
        this.variable = VariableSingleton.getInstance();
        result=0;

    }

    public gLMS(gLMS stage) {
        super(stage.getTitle(), stage.getContent());
        this.title = stage.getTitle();
        this.questionText = stage.getTitle();
        this.buttonText = stage.getButtonText();
        this.helpText = stage.getHelpText();
        this.alert = stage.getAlert();
        this.sound= SoundSingleton.getInstance();
        this.variable = VariableSingleton.getInstance();
        this.chosenVariable = stage.getChosenVariable();
        result=0;
    }

    public void setDefaultResult(){
        result = 0;
    }

    public void addVariable(String variableName){
        variable.addVariable(variableName);
    }
    public ObservableList<String> getVariable(){
        return variable.getVariable();
    }
    public String getVariableName() {
        return variable.getVariableName();
    }
    public String getChosenVariable() {
        return chosenVariable;
    }

    public void setChosenVariable(String chosenVariable) {
        this.chosenVariable = chosenVariable;
    }

    public void setVariableName(String variableName) {
        variable.setVariableName(variableName);
    }

    public void setAlert(boolean s){
        this.alert =s;
    }
    public void setResult(int result){
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public String getQuestionText() {
        return questionText;
    }
    public boolean getAlert(){
        return alert;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
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

    public void playAlertSound(){
        sound.playSound("boop");
    }
    public String getType(){
        return "GLMS";
    }
    @Override
    public String toString() {

        String variablesString = variable.getVariable()
                .stream()
                .collect(Collectors.joining(", "));

        return "glmsStage(\"" + title + "\",\"" + buttonText + "\",\"" +
                content + "\",\"" + helpText + "\",\"" + alert +  "\",\""  + chosenVariable + "\"," +
                "\"{" +  variablesString + "}\")"; }

    public void setConducted(String currentFormattedTime) {
        this.conducted = currentFormattedTime;
    }
    public String getConducted(){
        if(conducted != null){
            return conducted;
        } else return null;
    }


}




