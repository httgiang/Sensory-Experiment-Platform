package main.sensoryexperimentplatform.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.sensoryexperimentplatform.viewmodel.AudibleSound_VM;



public class RunAudibleController {


    @FXML
    private Label contentTxt;
;

    @FXML
    private Label titleTxt;
    private AudibleSound_VM viewModel;

    public void setViewModel(AudibleSound_VM viewModel) {
        this.viewModel = viewModel;
        bindViewModel();
    }
    private void bindViewModel(){
        titleTxt.textProperty().bind(viewModel.titleProperty());
        contentTxt.textProperty().bind(viewModel.contentProperty());
        viewModel.playSound();

    }

}
