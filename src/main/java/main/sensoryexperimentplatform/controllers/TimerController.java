package main.sensoryexperimentplatform.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import main.sensoryexperimentplatform.viewmodel.TimerStage_VM;

import java.io.IOException;

public class TimerController {
    private TimerStage_VM timerVM;

    @FXML
    private CheckBox cb_alertSound;


    @FXML
    private TextField txt_instruction;

    @FXML
    private TextField txt_timewait;

    @FXML
    private StackPane TimerStackPane;


    public void setViewModel(TimerStage_VM timerVM){
        this.timerVM = timerVM;
        bind();
    }

    private void bind() {
        txt_instruction.textProperty().bindBidirectional(timerVM.txt_instructionProperty());
        txt_timewait.textProperty().bindBidirectional(timerVM.txt_timewaitProperty());
        cb_alertSound.selectedProperty().bindBidirectional(timerVM.cb_alertSoundProperty());

        txt_instruction.textProperty().addListener((observable, oldValue, newValue) ->{
            timerVM.onInstruction(newValue);
        });

        txt_timewait.textProperty().addListener((observable, oldValue, newValue) ->{
            timerVM.onTimeWait(newValue);
        });

        cb_alertSound.selectedProperty().addListener((observable, oldValue, newValue) ->{
            timerVM.onAlert(newValue);
        });



    }
}
