package main.sensoryexperimentplatform.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import main.sensoryexperimentplatform.viewmodel.NoticeStage_VM;



public class RunNoticeController {
    @FXML
    private Label titleTxt, contentTxt;
    private NoticeStage_VM viewModel;
    @FXML
    private ImageView help_image;


    public void setViewModel(NoticeStage_VM viewModel){
        this.viewModel = viewModel;

        bindViewModel();
        if (viewModel.alertProperty().get()) {
           viewModel.playSound();
        }

    }




    private void bindViewModel(){
        titleTxt.textProperty().bindBidirectional(viewModel.titleProperty());
        contentTxt.textProperty().bindBidirectional(viewModel.contentProperty());

    }
}
