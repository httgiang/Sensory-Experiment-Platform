package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.sensoryexperimentplatform.viewmodel.InputStage_VM;

public class RunInputController {



    @FXML
    private TextField txt_input;

    @FXML
    private Label txt_question;

    private InputStage_VM viewModel;

    public void setViewModel(InputStage_VM viewModel){
        this.viewModel = viewModel;
        txt_input.setAlignment(Pos.CENTER);
        bindViewModel();
        if(viewModel.getPlayAlert().get()){
            viewModel.playAlert();
        }




    }
    public void setResult(String input){
        viewModel.setResult(input);
    }


    private void bindViewModel() {
        txt_input.textProperty().bindBidirectional(viewModel.getResult());

        txt_input.textProperty().addListener((observableValue, oldValue, newValue) -> {
            viewModel.setResult(newValue);
        });
        txt_question.textProperty().bindBidirectional(viewModel.questionProperty());

    }
}
