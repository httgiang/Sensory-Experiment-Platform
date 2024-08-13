package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.sensoryexperimentplatform.models.Question;

public class RunQuestion_VM {
    private Question question;
    private StringProperty questionText,leftButtonText,rightButtonText,leftButtonValue,rightButtonValue,helpText;
    private BooleanProperty playAlert;


    public RunQuestion_VM(Question question) {
        this.question = question;
        questionText = new SimpleStringProperty(question.getQuestion());
        leftButtonText = new SimpleStringProperty(question.getLeftButtonText());
        rightButtonText = new SimpleStringProperty(question.getRightButtonText());
        helpText = new SimpleStringProperty(question.getHelpText());
        playAlert = new SimpleBooleanProperty(question.isAlert());

    }

    public StringProperty questionTextProperty() {

        return questionText;
    }
    public StringProperty leftButtonTextProperty() {
        return leftButtonText;

    }
    public StringProperty rightButtonTextProperty() {
        return rightButtonText;
    }
    public StringProperty helpTextProperty() {
        return helpText;
    }
    public BooleanProperty playAlertProperty() {
        return playAlert;
    }
    public void playAlert(){
        question.playSound();
    }
}
