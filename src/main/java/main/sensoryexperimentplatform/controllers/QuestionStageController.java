package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.sensoryexperimentplatform.viewmodel.QuestionStage_VM;

public class QuestionStageController {
    private QuestionStage_VM viewModel;

    @FXML
    private CheckBox cbx_alert;



    @FXML
    private TextArea txt_HelpText;
    @FXML
    private TextField txt_leftText;

    @FXML
    private TextField txt_leftValue;

    @FXML
    private TextField txt_rightText;

    @FXML
    private TextField txt_storeVariable;


    @FXML
    private ComboBox<String> checkVariable;


    @FXML
    private RadioButton rtn_available;

    @FXML
    private RadioButton rtn_new;

    @FXML
    private ToggleGroup variableToggleGroup = new ToggleGroup();

    @FXML
    private TextField txt_rightValue;
    @FXML
    private TextField txt_question;

    public void setQuestionStage_vm( QuestionStage_VM viewModel){
        this.viewModel = viewModel;
        checkVariable.getItems().addAll(viewModel.getVariable());
        bindViewModel();
    }
    public void bindViewModel(){
        txt_HelpText.textProperty().bindBidirectional(viewModel.helpTextProperty());
        txt_leftText.textProperty().bindBidirectional(viewModel.leftTextProperty());
        txt_rightText.textProperty().bindBidirectional(viewModel.rightTextProperty());
        txt_question.textProperty().bindBidirectional(viewModel.questionProperty());
        txt_leftValue.textProperty().bindBidirectional(viewModel.leftValueProperty());
        txt_rightValue.textProperty().bindBidirectional(viewModel.rightValueProperty());
        cbx_alert.selectedProperty().bindBidirectional(viewModel.alertProperty());
        txt_storeVariable.textProperty().bindBidirectional(viewModel.choosenVariableProperty());


        txt_HelpText.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setHelpText(newValue);
//
//
        });
        cbx_alert.selectedProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setAlert(newValue);

        });
        txt_question.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setQuestion(newValue);

        });
        txt_leftValue.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setLeftValue(newValue);
        });
        txt_rightValue.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setRightValue(newValue);
        });
        txt_leftText.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setLeftText(newValue);
        });
        txt_rightText.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setRightText(newValue);
        });

        txt_storeVariable.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setChoosenVariable(newValue);
        });

        rtn_available.setToggleGroup(variableToggleGroup);
        rtn_new.setToggleGroup(variableToggleGroup);

        variableToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (rtn_new.isSelected()) {
                txt_storeVariable.setDisable(false);
                checkVariable.setDisable(true);
            } else if (rtn_available.isSelected()) {
                txt_storeVariable.setDisable(true);
                checkVariable.setDisable(false);
                txt_storeVariable.setText("LastQuestionStageResult");

            }
        });

//        if(viewModel.getVariableName() != null){
//            viewModel.addVariable(viewModel.getVariableName());
//        }
        if (viewModel.getChoosenVariable() != null) {
            checkVariable.setValue(viewModel.getChoosenVariable());
        }


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
    }

    }
