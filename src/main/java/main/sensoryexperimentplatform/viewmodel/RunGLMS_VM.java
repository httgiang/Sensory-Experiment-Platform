package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunGLMSController;
import main.sensoryexperimentplatform.models.DataAccess;
import main.sensoryexperimentplatform.models.gLMS;

import java.io.IOException;

public class RunGLMS_VM implements RunStages {
    private gLMS stage;
    private StringProperty question, help, conducted;
    private DoubleProperty sliderValue;
    private StringProperty button;
    private BooleanProperty alert;

    public RunGLMS_VM(gLMS stage){
        this.stage = stage;
        help = new SimpleStringProperty(stage.getHelpText());
        question = new SimpleStringProperty(stage.getQuestionText());
        button = new SimpleStringProperty(stage.getButtonText());
        sliderValue = new SimpleDoubleProperty(stage.getResult());
        conducted = new SimpleStringProperty(stage.getConducted());
        alert = new SimpleBooleanProperty(stage.getAlert());

        sliderValue.addListener(((observableValue, oldValue, newValue) ->{
            setResult(newValue.intValue());
            conducted.set(DataAccess.getCurrentFormattedTime());
            setDate();
        } ));

    }

    public void setDate(){
        stage.setConducted(DataAccess.getCurrentFormattedTime());
    }

    public void setResult(int result){
        stage.setResult(result);
    }

    public StringProperty helpProperty(){
        return help;
    }
    public StringProperty questionProperty() {
        return question;
    }

    public StringProperty buttonProperty() {
        return button;
    }

    public DoubleProperty sliderValueProperty() {
        return sliderValue;
    }

    public BooleanProperty alertProperty() {
        return alert;
    }

    public StringProperty conductedTextProperty() {
        return conducted;
    }

    public void playAlertSound(){
        stage.playAlertSound();
    }

    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunGLMS.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().addAll(newContent);
        RunGLMSController controller = loader.getController();
        controller.setViewModel(this);
    }

}
