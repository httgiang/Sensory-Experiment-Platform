package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.viewmodel.RunNotice_VM;
import main.sensoryexperimentplatform.viewmodel.RunStartVM;

public class RunStartController {
    @FXML
    private Label titleTxt, contentTxt;

    @FXML
    private AnchorPane anchorpane;
    private RunStartVM viewModel;

    public void setViewModel(RunStartVM viewModel){
        this.viewModel = viewModel;
        bindViewModel();
        scaleUILayout();
    }
    private void bindViewModel(){
        titleTxt.textProperty().bind(viewModel.getTitle());
        contentTxt.textProperty().bind(viewModel.getContent());


    }
    private void scaleUILayout(){
        if(contentTxt == null){
            titleTxt.setLayoutY(anchorpane.getHeight() / 2.0 - titleTxt.getLayoutBounds().getHeight() / 2.0);
        } else {
            titleTxt.setLayoutY(0.25 * anchorpane.getHeight());
        }
    }
}
