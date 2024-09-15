package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.viewmodel.GLMSStage_VM;

public class GLMSController {

    private GLMSStage_VM glmsStageVm;
    @FXML
    private CheckBox checkB_sound;

    @FXML
    private CheckBox checkB_swap;



    @FXML
    private ChoiceBox<String> choiceB_avail;

    @FXML
    private Label lbl_buttonText;

    @FXML
    private Label lbl_help;

    @FXML
    private Label lbl_playSound;

    @FXML
    private Label lbl_storeResponse;

    @FXML
    private Label lbl_swapPole;

    @FXML
    private RadioButton radioBtn_Yes;

    @FXML
    private RadioButton radioBtn_available;

    @FXML
    private TextField txt_LowAncTxt;

    @FXML
    private TextArea txt_help;

    @FXML
    private TextField txt_question;

    @FXML
    private TextField txt_yes;

    @FXML
    private AnchorPane innerPane;
    @FXML
    private ScrollPane scrollPane;

    private void bind() {
        txt_LowAncTxt.textProperty().bindBidirectional(glmsStageVm.txt_LowAncTxtProperty());
        txt_help.textProperty().bindBidirectional(glmsStageVm.txt_helpProperty());
        txt_question.textProperty().bindBidirectional(glmsStageVm.txt_questionProperty());
        checkB_sound.selectedProperty().bindBidirectional(glmsStageVm.checkB_soundProperty());
        checkB_swap.selectedProperty().bindBidirectional(glmsStageVm.checkB_swapProperty());

        txt_yes.textProperty().bindBidirectional(glmsStageVm.choosenVariableProperty());

        choiceB_avail.getItems().addAll(glmsStageVm.getVariable());

        if (glmsStageVm.getChoosenVariable() != null) {
            choiceB_avail.setValue(glmsStageVm.getChoosenVariable());
        }


        choiceB_avail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            glmsStageVm.setChoosenVariable(newValue);
            if (newValue != null) {
                glmsStageVm.addVariable(newValue);
            }
        });


        txt_yes.setOnAction(event -> {
            String newValue = txt_yes.getText();
            if (newValue != null && !newValue.isEmpty()) {
                glmsStageVm.setChoosenVariable(newValue);
                glmsStageVm.addVariable(newValue);
            }
        });

        txt_yes.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String newText = txt_yes.getText();
                if (newText != null && !newText.isEmpty()) {
                    glmsStageVm.setChoosenVariable(newText);
                    glmsStageVm.addVariable(newText);
                }
            }
        });

        // If variable name is set (from a previous state), ensure it is added
        if (glmsStageVm.getVariableName() != null) {
            glmsStageVm.setChoosenVariable(glmsStageVm.getVariableName());
            glmsStageVm.addVariable(glmsStageVm.getVariableName());
        }
    }


    public void setViewModel(GLMSStage_VM glms) {
        this.glmsStageVm = glms;
        bind();


    }
}