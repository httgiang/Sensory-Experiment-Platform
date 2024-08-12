package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.sensoryexperimentplatform.viewmodel.RunQuestion_VM;

public class RunQuestionController {

    @FXML
    private ImageView help_image;

    @FXML
    private Label txt_question;

    private RunQuestion_VM viewModel;

    public void setViewModel(RunQuestion_VM viewModel){
        this.viewModel = viewModel;
        bindViewModel();
        if(viewModel.playAlertProperty().get()){
            viewModel.playAlert();
        }

    }
    private void bindViewModel(){
        txt_question.textProperty().bindBidirectional(viewModel.questionTextProperty());
    }

}
