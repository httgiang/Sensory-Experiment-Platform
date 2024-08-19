package main.sensoryexperimentplatform.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import main.sensoryexperimentplatform.viewmodel.RunTimer_VM;
import main.sensoryexperimentplatform.viewmodel.TimerStage_VM;

public class RunTimerController {
    public Label title_lbl;
    public ProgressBar myProgress;
    public BooleanProperty isTimeLineFull;
    TimerStage_VM viewModel;
    @FXML
    Label instruction_lbl;

    private Timeline timeline;
    private int totalTimeInSeconds;
    public void setViewModel(TimerStage_VM viewModel){
        this.viewModel = viewModel;
        totalTimeInSeconds = viewModel.getDuration() * 60;
        isTimeLineFull = new SimpleBooleanProperty(false);
        bindViewModel();
        startTimer();
        if(viewModel.isCb_alertSound()){
            viewModel.playAlertSound();
        }
    }

    private void bindViewModel(){

        instruction_lbl.textProperty().bind(viewModel.txt_instructionProperty());
        myProgress.progressProperty().bind(viewModel.getProgress());
    }

    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateTimer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void updateTimer() {
        double currentProgress = viewModel.getProgress().get();
        double increment = 1.0 / totalTimeInSeconds;
        if (currentProgress + increment >= 1.0) {
            viewModel.updateProgress(1.0);
            isTimeLineFull.set(true);
            timeline.stop();
        } else {
            viewModel.updateProgress(currentProgress + increment);
        }
    }
    public BooleanProperty timelineFullProperty() {
        return isTimeLineFull;
    }

    public Boolean getTimeLineCheck() {
        return isTimeLineFull.get();
    }
}
