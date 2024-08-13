package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.sensoryexperimentplatform.viewmodel.AddTasteVM;

public class addTasteController {

    @FXML
    private CheckBox cb_swapPole;

    @FXML
    private CheckBox checkbox_playalert;

    @FXML
    private CheckBox checkbox_randomfood;

    @FXML
    private CheckBox checkbox_randomrate;

    @FXML
    private TextField txt_HighAnchorText;

    @FXML
    private TextField txt_buttonText;

    @FXML
    private TextField txt_comsumptionInst;

    @FXML
    private TextField txt_endInstruction;

    @FXML
    private TextArea txt_help;

    @FXML
    private TextField txt_highAnchorValue;

    @FXML
    private TextField txt_initialNotice;

    @FXML
    private TextField txt_lowAnchorText;

    @FXML
    private TextField txt_lowAnchorValue;

    @FXML
    private TextField txt_question;

    @FXML
    private TextField txt_timeWait;

    private AddTasteVM addVM;

    public void initializeBinding() {
        txt_initialNotice.textProperty().bindBidirectional(addVM.txt_initialNoticeProperty());
        txt_comsumptionInst.textProperty().bindBidirectional(addVM.txt_consumptionInstProperty());
        txt_question.textProperty().bindBidirectional(addVM.txt_questionProperty());

        txt_lowAnchorText.textProperty().bindBidirectional(addVM.txt_lowanchortextProperty());
        txt_HighAnchorText.textProperty().bindBidirectional(addVM.txt_highanchortextProperty());
        txt_lowAnchorValue.textProperty().bindBidirectional(addVM.txt_lowacnhorvalueProperty());
        txt_highAnchorValue.textProperty().bindBidirectional(addVM.txt_highacnhorvalueProperty());

        txt_buttonText.textProperty().bindBidirectional(addVM.txt_buttontextProperty());
        cb_swapPole.selectedProperty().bindBidirectional(addVM.checkbox_swappoleProperty());
        txt_help.textProperty().bindBidirectional(addVM.txt_helpProperty());
        txt_endInstruction.textProperty().bindBidirectional(addVM.txt_endInstructionProperty());
        txt_timeWait.textProperty().bindBidirectional(addVM.txt_timetowaitProperty());
        checkbox_randomfood.selectedProperty().bindBidirectional(addVM.checkbox_randomfoodProperty());
        checkbox_playalert.selectedProperty().bindBidirectional(addVM.checkbox_playalertProperty());
        checkbox_randomrate.selectedProperty().bindBidirectional(addVM.checkbox_randomrateProperty());
    }

    public void setViewModel(AddTasteVM view) {
        addVM = view;
        initializeBinding();
    }
}
