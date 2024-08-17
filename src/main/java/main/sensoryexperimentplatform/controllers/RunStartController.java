package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.sensoryexperimentplatform.viewmodel.RunNotice_VM;
import main.sensoryexperimentplatform.viewmodel.RunStartVM;

public class RunStartController {
    @FXML
    private Label titleTxt, contentTxt;
    private RunStartVM viewModel;

    public void setViewModel(RunStartVM viewModel){
        this.viewModel = viewModel;
        bindViewModel();
    }
    private void bindViewModel(){
        titleTxt.textProperty().bind(viewModel.getTitle());
        contentTxt.textProperty().bind(viewModel.getContent());


    }
}
