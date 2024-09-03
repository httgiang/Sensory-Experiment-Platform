package main.sensoryexperimentplatform.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import main.sensoryexperimentplatform.viewmodel.QuestionStage_VM;



public class RunQuestionController {


    @FXML
    private Button btn_left;

    @FXML
    private Button btn_right;

    @FXML
    private Label txt_question;

    private QuestionStage_VM viewModel;
    @FXML
    private AnchorPane anchorPane;

    @FXML



    public void setViewModel(QuestionStage_VM viewModel){
        initialize();
        this.viewModel = viewModel;
        bindViewModel();
        if(viewModel.alertProperty().get()){
            viewModel.playAlert();
        }

        btn_left.setOnMouseClicked(event -> handleButtonClick(viewModel.getLeftValue()));
        btn_right.setOnMouseClicked(event -> handleButtonClick(viewModel.getLeftValue()));

    }

    private void bindViewModel(){
        txt_question.textProperty().bindBidirectional(viewModel.questionProperty());
        btn_left.textProperty().bindBidirectional(viewModel.leftTextProperty());
        btn_right.textProperty().bindBidirectional(viewModel.rightTextProperty());
    }

    private void handleButtonClick(String buttonIdentifier) {
        viewModel.setResult(buttonIdentifier);
    }

    @FXML
    private void initialize() {
        anchorPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            double paneWidth = (Double) newVal;
            double buttonWidth = btn_right.getWidth();
            double centerX = (paneWidth - buttonWidth) / 2;
            btn_right.setLayoutX(centerX + buttonWidth);
            btn_left.setLayoutX(centerX);
        });
    }

}
