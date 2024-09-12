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
    private ComboBox<String> checkVariable;

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

    }

}
