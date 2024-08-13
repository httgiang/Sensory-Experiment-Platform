package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import main.sensoryexperimentplatform.viewmodel.RunInputVM;

public class RunInputController {

    @FXML
    private ImageView help_image;

    @FXML
    private TextField txt_input;

    @FXML
    private Label txt_question;

    private RunInputVM viewModel;

    public void setViewModel(RunInputVM viewModel){
        this.viewModel = viewModel;
        txt_input.setAlignment(Pos.CENTER);
        bindViewModel();
        if(viewModel.getPlayAlert().get()){
            viewModel.playAlert();
        }
    }

    private void bindViewModel(){
        txt_question.textProperty().bindBidirectional(viewModel.getQuestionText());
    }

}
