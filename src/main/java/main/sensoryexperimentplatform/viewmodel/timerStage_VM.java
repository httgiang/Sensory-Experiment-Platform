package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.TimerController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Timer;

import java.io.IOException;
import java.util.Stack;

public class timerStage_VM implements Stages {
    private Timer timer;
    private StringProperty txt_instruction;
    private StringProperty txt_timewait;
    private BooleanProperty cb_alertSound;
    private Experiment experiment;
    public timerStage_VM(Experiment experiment){
        this.experiment = experiment;
        timer = new Timer("0", "Please Wait",false);
        txt_instruction = new SimpleStringProperty(timer.getInstruction());
        txt_timewait = new SimpleStringProperty(timer.getFormattedElapsed());
        cb_alertSound = new SimpleBooleanProperty(timer.isAlert());
        cb_alertSound.addListener((observableValue, oldValue, newValue) -> onAlert(newValue));
        txt_timewait.addListener((observableValue, oldValue, newValue) -> onTimeWait(newValue));
        txt_instruction.addListener((observableValue, oldValue, newValue) -> onInstruction(newValue));
        experiment.addTimerStage(timer);
    }
    public timerStage_VM(Timer timer){
        this.timer = timer;
        txt_instruction = new SimpleStringProperty(timer.getInstruction());
        txt_timewait = new SimpleStringProperty(timer.getFormattedElapsed());
        cb_alertSound = new SimpleBooleanProperty(timer.isAlert());
        cb_alertSound.addListener((observableValue, oldValue, newValue) -> onAlert(newValue));
        txt_timewait.addListener((observableValue, oldValue, newValue) -> onTimeWait(newValue));
        txt_instruction.addListener((observableValue, oldValue, newValue) -> onInstruction(newValue));
    }

    private void onAlert(Boolean newValue) {
        timer.setAlert(newValue);
    }

    private void onInstruction(String newValue) {
        timer.setInstruction(newValue);
    }

    private void onTimeWait(String newValue) {
        timer.setTime(newValue);
    }

    public String getTxt_instruction() {
        return txt_instruction.get();
    }

    public StringProperty txt_instructionProperty() {
        return txt_instruction;
    }

    public boolean isCb_alertSound() {
        return cb_alertSound.get();
    }

    public BooleanProperty cb_alertSoundProperty() {
        return cb_alertSound;
    }

    public String getTxt_timewait() {
        return txt_timewait.get();
    }

    public StringProperty txt_timewaitProperty() {
        return txt_timewait;
    }

    public Timer getTimer() {
        return timer;
    }

    @Override
    public void loadInterface(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("TimerStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        TimerController controller = fxmlLoader.getController();
        controller.setViewModel (this);
    }

    @Override
    public void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Stack<ratingContainer_VM> ratingContainerVm) throws IOException {

    }

    @Override
    public String toString() {
        return "[Waiting] "+ txt_instruction.get();
    }

}