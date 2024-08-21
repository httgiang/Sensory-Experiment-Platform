package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunInputController;
import main.sensoryexperimentplatform.models.Input;

import java.io.IOException;

public class RunInputVM {
    private Input input;
    private StringProperty questionText,buttonText,HelpText,result;
    private BooleanProperty playAlert;

    public RunInputVM(Input input) {
        this.input = input;
        questionText = new SimpleStringProperty(input.getQuestionText());
        buttonText = new SimpleStringProperty(input.getButtonText());
        HelpText = new SimpleStringProperty(input.getHelpText());
        playAlert = new SimpleBooleanProperty(input.isAlert());
        result = new SimpleStringProperty(input.getResult());

        result.addListener((observableValue, oldValue, newValue)->setResult(newValue));

    }


    public StringProperty getQuestionText() {
        return questionText;
    }
    public StringProperty getButtonText() {
        return buttonText;
    }
    public StringProperty getHelpText() {
        return HelpText;
    }
    public StringProperty getResult() {
        return result;
    }
   public void setResult(String result) {
       input.setResult(result);
   }
    public BooleanProperty getPlayAlert() {
        return playAlert;
    }
    public void playAlert(){
        input.playSound();
    }
}
