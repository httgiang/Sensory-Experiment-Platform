package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.sensoryexperimentplatform.viewmodel.AddTasteVM;

public class AddTasteController {

    @FXML
    private CheckBox cbox;
    @FXML
    private CheckBox checkbox_playalert;

    @FXML
    private CheckBox checkbox_randomfood;

    @FXML
    private CheckBox checkbox_randomrate;

    @FXML
    private TextField txt_buttontext;

    @FXML
    private TextField txt_consumpins;

    @FXML
    private TextField txt_endInstruction;

    @FXML
    private TextArea txt_help;

    @FXML
    private TextField txt_highacnhorvalue;

    @FXML
    private TextField txt_highanchortext;

    @FXML
    private CheckBox checkbox_swappol;

    @FXML
    private TextField txt_howtaste;

    @FXML
    private TextField txt_inital;

    @FXML
    private TextField txt_lowacnhorvalue;

    @FXML
    private TextField txt_lowanchortext;

    @FXML
    private TextField txt_timetowait;
    private AddTasteVM addVM;

    public void init() {
        txt_inital.textProperty().bindBidirectional(addVM.txt_initalProperty());
        txt_buttontext.textProperty().bindBidirectional(addVM.txt_buttontextProperty());
        txt_consumpins.textProperty().bindBidirectional(addVM.txt_consumpinsProperty());
        txt_help.textProperty().bindBidirectional(addVM.txt_helpProperty());
        txt_endInstruction.textProperty().bindBidirectional(addVM.txt_endInstructionProperty());
        txt_howtaste.textProperty().bindBidirectional(addVM.txt_howtasteProperty());
        txt_timetowait.textProperty().bindBidirectional(addVM.txt_timetowaitProperty());
        checkbox_randomfood.selectedProperty().bindBidirectional(addVM.checkbox_randomfoodProperty());
        checkbox_playalert.selectedProperty().bindBidirectional(addVM.checkbox_playalertProperty());
        checkbox_swappol.selectedProperty().bindBidirectional(addVM.checkbox_swappoleProperty());
        txt_highacnhorvalue.textProperty().bindBidirectional(addVM.txt_highacnhorvalueProperty());
        txt_lowacnhorvalue.textProperty().bindBidirectional(addVM.txt_lowacnhorvalueProperty());
        checkbox_randomrate.selectedProperty().bindBidirectional(addVM.checkbox_randomrateProperty());
        txt_lowanchortext.textProperty().bindBidirectional(addVM.txt_lowanchortextProperty());
        txt_highanchortext.textProperty().bindBidirectional(addVM.txt_highanchortextProperty());
    }

    public void setViewModel(AddTasteVM view) {
        addVM = view;
        init();
    }
}
