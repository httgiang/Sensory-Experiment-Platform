package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunTimerController;
import main.sensoryexperimentplatform.controllers.TimerController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Timer;

import java.io.IOException;

public class TimerStage_VM implements ViewModel{
    private Timer timer;
    private StringProperty txt_instruction;
    private StringProperty txt_timewait;
    private BooleanProperty cb_alertSound;
    private DoubleProperty progress;
    private BooleanProperty timerComplete;
    private Experiment experiment;

    private RunTimerController runController;

    public TimerStage_VM(Experiment experiment){
        this.experiment = experiment;
        timer = new Timer("0", "Please Wait",false);
        initBinding(timer);

        experiment.addTimerStage(timer);
    }
    public TimerStage_VM(Timer timer){
        this.timer = timer;
        initBinding(timer);
    }
    public TimerStage_VM(IfConditionalStatementVM ifConditionalStatementVM){
        timer = new Timer("0", "Please Wait",false);
        initBinding(timer);
       ifConditionalStatementVM.addIf(timer);
    }
    public TimerStage_VM(ElseConditionalStatementVM elseConditionalStatementVM){
        timer = new Timer("0", "Please Wait",false);
        initBinding(timer);
        elseConditionalStatementVM.addElse(timer);
    }

    private void initBinding(Timer timer){
        txt_instruction = new SimpleStringProperty(timer.getInstruction());
        txt_timewait = new SimpleStringProperty(timer.getFormattedElapsed());
        cb_alertSound = new SimpleBooleanProperty(timer.isAlert());
        this.progress = new SimpleDoubleProperty(0.0);
        timerComplete = new SimpleBooleanProperty(false); // Timer starts as incomplete

        cb_alertSound.addListener((observableValue, oldValue, newValue) -> onAlert(newValue));
        txt_timewait.addListener((observableValue, oldValue, newValue) -> onTimeWait(newValue));
        txt_instruction.addListener((observableValue, oldValue, newValue) -> onInstruction(newValue));
    }
    public RunTimerController getRunController() {
        return runController;
    }

    public void onAlert(Boolean newValue) {
        timer.setAlert(newValue);
    }

    public void onInstruction(String newValue) {
        timer.setInstruction(newValue);
    }

    public void onTimeWait(String newValue) {
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
    public DoubleProperty getProgress() {
        return progress;
    }
    public int getDuration(){
        return Integer.parseInt(timer.getTitle());
    }

    public Timer getTimer() {
        return timer;
    }
    public void playAlertSound(){
        timer.playAlertSound();
    }
    public void updateProgress(double progressValue) {
        progress.set(progressValue);
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunTimer.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);
        runController = loader.getController();
        runController.setViewModel(this);
    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("TimerStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        TimerController controller = fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleEditButtons(Button button1, Button button2, Button button3,
                                  Button button4, Button button5, Button button6,
                                  Button button7, Button button8, Button button9,
                                  Button button10, Button button11, Button button12)
            throws IOException {
        button1.setDisable(true);
        button3.setDisable(true);
        button4.setDisable(true);
        button7.setDisable(false);
        button5.setDisable(false);
        button2.setDisable(false);
        button11.setDisable(false);
        button6.setDisable(false);
        button8.setDisable(false);
        button12.setDisable(false);
        button10.setDisable(false);
        button9.setDisable(false);

    }

    @Override
    public void handleRunButtons(Button btn_next, Button btn_back) {
        btn_back.setVisible(runController.getTimeLineCheck());
        btn_next.setVisible(runController.getTimeLineCheck());
        btn_next.setDisable(false);

        this.runController.timelineFullProperty().addListener(((observableValue, oldValue, newValue) ->{
            btn_back.setVisible(newValue);
            btn_next.setVisible(newValue);
        } ));
    }



    @Override
    public String toString() {
        return "[Waiting] "+ txt_instruction.get();
    }

}