package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.sensoryexperimentplatform.viewmodel.RunAudible_VM;
import main.sensoryexperimentplatform.viewmodel.RunNotice_VM;

public class RunAudibleController {


    @FXML
    private Label contentTxt;

    @FXML
    private Label titleTxt;
    private RunAudible_VM viewModel;

    public void setViewModel(RunAudible_VM viewModel){
        this.viewModel = viewModel;
        bindViewModel();
    }
    private void bindViewModel(){
        titleTxt.textProperty().bind(viewModel.getTitle());
        contentTxt.textProperty().bind(viewModel.getContent());
        viewModel.playSound();

    }

}
