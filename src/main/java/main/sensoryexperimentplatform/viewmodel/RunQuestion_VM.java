package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunQuestionController;
import main.sensoryexperimentplatform.models.Question;

import java.io.IOException;

public class RunQuestion_VM implements RunStages {
    private Question question;
    private StringProperty questionText,leftButtonText,rightButtonText,leftButtonValue,rightButtonValue,helpText;
    private BooleanProperty playAlert;
    private StringProperty result;



    public RunQuestion_VM(Question question) {
        this.question = question;
        questionText = new SimpleStringProperty(question.getQuestion());
        leftButtonText = new SimpleStringProperty(question.getLeftButtonText());
        rightButtonText = new SimpleStringProperty(question.getRightButtonText());
        helpText = new SimpleStringProperty(question.getHelpText());
        playAlert = new SimpleBooleanProperty(question.isAlert());
        leftButtonValue = new SimpleStringProperty(question.getLeftButtonValue());
        rightButtonValue = new SimpleStringProperty(question.getRightButtonValue());
        result = new SimpleStringProperty(question.getResult());
    }

    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunQuestionStage.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);

        RunQuestionController controller = loader.getController();
        controller.setViewModel(this);
    }

    public StringProperty questionTextProperty() {
        return questionText;
    }
    public StringProperty leftButtonTextProperty() {
        return leftButtonText;

    }
    public StringProperty leftButtonValueProperty() {
        return leftButtonValue;
    }
    public StringProperty rightButtonValueProperty() {
        return rightButtonValue;
    }
    public StringProperty resultProperty() {
        return result;
    }

    public void setResult(String result) {
        question.setResult(result);
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
