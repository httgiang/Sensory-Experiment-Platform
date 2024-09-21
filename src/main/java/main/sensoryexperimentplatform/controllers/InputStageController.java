package main.sensoryexperimentplatform.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import main.sensoryexperimentplatform.viewmodel.InputStage_VM;

public class InputStageController {
    private InputStage_VM viewModel;

    @FXML
    private CheckBox cbx_playsound;

    @FXML
    private TextField txt_buttonText;

    @FXML
    private TextArea txt_helptext;

    @FXML
    private TextField txt_question;

    @FXML
    private TextField txt_storeVariable;

    @FXML
    private ComboBox<String> checkVariable;
    @FXML
    private ToggleGroup variableToggleGroup = new ToggleGroup();

    @FXML
    private RadioButton rtn_available;

    @FXML
    private RadioButton rtn_new;


    public void setViewModel(InputStage_VM viewModel){
        this.viewModel = viewModel;
        checkVariable.getItems().addAll(viewModel.getVariable());
        bindViewModel();
    }

    public void bindViewModel(){
        txt_buttonText.textProperty().bindBidirectional(viewModel.buttonTextProperty());
        txt_helptext.textProperty().bindBidirectional(viewModel.helpTextProperty());
        txt_question.textProperty().bindBidirectional(viewModel.questionProperty());
        cbx_playsound.selectedProperty().bindBidirectional(viewModel.alertProperty());
        txt_storeVariable.textProperty().bindBidirectional(viewModel.choosenVariableProperty());


        // Add listeners for immediate update
        txt_buttonText.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setButtonText(newValue);
        });

        txt_helptext.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setHelpText(newValue);
        });

        txt_question.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setQuestion(newValue);
        });

        cbx_playsound.selectedProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setAlert(newValue);
        });
        txt_storeVariable.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setVariableName(newValue);
        });
        variableToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (rtn_new.isSelected()) {
                txt_storeVariable.setDisable(false);
                checkVariable.setDisable(true);
            } else if (rtn_available.isSelected()) {
                txt_storeVariable.setDisable(true);
                checkVariable.setDisable(false);
                txt_storeVariable.setText("LastInputStageResult");

            }
        });
        rtn_available.setToggleGroup(variableToggleGroup);
        rtn_new.setToggleGroup(variableToggleGroup);
        rtn_new.setSelected(true);





        if (viewModel.getChoosenVariable() != null) {
            checkVariable.setValue(viewModel.getChoosenVariable());
            rtn_available.setSelected(true);        }


        checkVariable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setChoosenVariable(newValue);
            if (newValue != null) {
                viewModel.addVariable(newValue);
            }
        });


        txt_storeVariable.setOnAction(event -> {
            String newValue = txt_storeVariable.getText();
            if (newValue != null && !newValue.isEmpty()) {
                viewModel.setChoosenVariable(newValue);
                viewModel.addVariable(newValue);
            }
        });

        txt_storeVariable.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                String newText = txt_storeVariable.getText();
                if (newText != null && !newText.isEmpty()) {
                    viewModel.setChoosenVariable(newText);
                    viewModel.addVariable(newText);
                }
            }
        });

        // If variable name is set (from a previous state), ensure it is added
        if (viewModel.getVariableName() != null) {
            viewModel.setChoosenVariable(viewModel.getVariableName());
            viewModel.addVariable(viewModel.getVariableName());
        }
//        

    }

    /*public void initialize() {
        txt_buttonText.textProperty().bindBidirectional(inputStage_vm.buttonTextProperty());
        txt_helptext.textProperty().bindBidirectional(inputStage_vm.contentProperty());
        txt_question.textProperty().bindBidirectional(inputStage_vm.titleProperty());
        cbx_alert.selectedProperty().bindBidirectional(inputStage_vm.alertProperty());

    }*/


    @FXML
    void cbx_alert(ActionEvent event) {

    }

    @FXML
    void txt_buttonText(ActionEvent event) {



    }

    @FXML
    void txt_helptext(MouseEvent event) {


    }

    @FXML
    void txt_question(MouseEvent event) {


    }


    public void cbx_playsound(ActionEvent event) {
    }
}