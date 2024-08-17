package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;

public class RunTimer_VM implements RunStages{
    private Timer timer;
    private StringProperty instruction;
    private StringProperty time;
    private DoubleProperty progress;
    private BooleanProperty timerComplete;
    private BooleanProperty playAlert;
    RunTimerController controller;

    public RunTimer_VM(Timer timer) {
        this.timer = timer;
        instruction = new SimpleStringProperty(timer.getInstruction());
        time = new SimpleStringProperty(timer.getTitle());
        progress = new SimpleDoubleProperty(0);  // Initial progress set to 0
        timerComplete = new SimpleBooleanProperty(false); // Timer starts as incomplete
        playAlert = new SimpleBooleanProperty(timer.isAlert());
    }
    public int getDuration(){
        return Integer.parseInt(timer.getTitle());
    }
    public StringProperty getInstruction() {
        return instruction;
    }

    public StringProperty getTime() {
        return time;
    }

    public DoubleProperty getProgress() {
        return progress;
    }

    public BooleanProperty isAlert() {
        return playAlert;
    }

    public void playAlertSound(){
        timer.playAlertSound();
    }
    public void updateProgress(double progressValue) {
        progress.set(progressValue);
    }

    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunTimer.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);


        this.controller = loader.getController();

        this.controller.setViewModel(this);

    }

    @Override
    public void handleRunButtons(Button btn_next, Button btn_back) {
        btn_back.setVisible(controller.getTimeLineCheck());
        btn_next.setVisible(controller.getTimeLineCheck());
        btn_next.setDisable(false);
//
//        this.controller.timelineFullProperty().addListener(((observableValue, oldValue, newValue) ->{
//            btn_back.setVisible(newValue);
//            btn_next.setVisible(newValue);
//            listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);
//        } ));
    }
}
