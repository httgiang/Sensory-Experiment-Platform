package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.sensoryexperimentplatform.viewmodel.VasStage_VM;

public class VasController {
    private VasStage_VM vasStageVM;

    @FXML
    private CheckBox checkB_sound;

    @FXML
    private CheckBox checkB_swap;



    @FXML
    private TextField txt_BtnTxt;

    @FXML
    private TextField txt_HighAncTxt;

    @FXML
    private TextField txt_HighAncVal;

    @FXML
    private TextField txt_LowAncTxt;

    @FXML
    private TextField txt_LowAncVal;

    @FXML
    private TextArea txt_help;

    @FXML
    private TextField txt_question;

    @FXML
    private TextField txt_yes;



    private void bind(){
        txt_question.textProperty().bindBidirectional(vasStageVM.questionTextProperty());
        txt_LowAncTxt.textProperty().bindBidirectional(vasStageVM.lowAnchorTextProperty());
        txt_HighAncTxt.textProperty().bindBidirectional(vasStageVM.highAnchorTextProperty());
        txt_LowAncTxt.textProperty().bindBidirectional(vasStageVM.lowAnchorTextProperty());
        txt_LowAncVal.textProperty().bindBidirectional(vasStageVM.lowAnchorValueProperty());
        txt_HighAncVal.textProperty().bindBidirectional(vasStageVM.highAnchorValueProperty());
        txt_BtnTxt.textProperty().bindBidirectional(vasStageVM.buttonTextProperty());
        txt_help.textProperty().bindBidirectional(vasStageVM.helpTextProperty());
        //txt_yes.textProperty().bindBidirectional(vasStageVM.txt_yesProperty());
        checkB_sound.selectedProperty().bindBidirectional(vasStageVM.checkB_soundProperty());
        checkB_swap.selectedProperty().bindBidirectional(vasStageVM.checkB_swapProperty());
    }
    public void setViewModel(VasStage_VM vas){
        this.vasStageVM = vas;
        bind();

    }

}