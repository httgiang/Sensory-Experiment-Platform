package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.sensoryexperimentplatform.viewmodel.RunNotice_VM;
import main.sensoryexperimentplatform.viewmodel.RunStartVM;
import main.sensoryexperimentplatform.viewmodel.StartVM;

public class RunStartController {
    @FXML
    private Label titleTxt, contentTxt;
    private StartVM viewModel;

    public void setViewModel(StartVM viewModel){
        this.viewModel = viewModel;
        bindViewModel();
    }
    private void bindViewModel(){
        titleTxt.textProperty().bind(viewModel.titleProperty());
        contentTxt.textProperty().bind(viewModel.contentProperty());
    }
}
