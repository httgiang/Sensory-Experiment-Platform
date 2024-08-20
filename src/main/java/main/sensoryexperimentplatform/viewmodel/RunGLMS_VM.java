package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunGLMSController;
import main.sensoryexperimentplatform.models.DataAccess;
import main.sensoryexperimentplatform.models.gLMS;

import java.io.IOException;

public class RunGLMS_VM {
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

        alert = new SimpleBooleanProperty(stage.getAlert());

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

//    @Override
//    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
//        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunGLMS.fxml"));
//        AnchorPane newContent = loader.load();
//        anchorPane.getChildren().addAll(newContent);
//        RunGLMSController controller = loader.getController();
//        controller.setViewModel(this);
//    }
//
//    @Override
//    public void handleRunButtons(Button btn_next, Button btn_back) {
//        btn_back.setDisable(false);
//        btn_next.textProperty().bind(this.buttonProperty());
//
//        if (this.conductedTextProperty().get() == null){
//            btn_next.setDisable(true);
//        }else btn_next.setDisable(false);
//
//        this.conductedTextProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                btn_next.setDisable(false);
//            }
//        });
//    }

}
