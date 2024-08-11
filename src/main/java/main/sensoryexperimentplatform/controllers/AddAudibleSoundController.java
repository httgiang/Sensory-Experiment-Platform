package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.sensoryexperimentplatform.viewmodel.AudibleSound_VM;

public class AddAudibleSoundController {
    private AudibleSound_VM viewModel;


    @FXML
    private TextField txt_buttonText;

    @FXML
    private TextArea txt_content;

    @FXML
    private TextArea txt_helpText;

    @FXML
    private TextField txt_title;

//    public void intialize(){
//        viewModel = new audibleSound_VM();
//        bindViewModel();
//
//    }
    public void bindViewModel(){
        txt_buttonText.textProperty().bindBidirectional(viewModel.buttonTextProperty());
        txt_helpText.textProperty().bindBidirectional(viewModel.helpTextProperty());
        txt_content.textProperty().bindBidirectional(viewModel.contentProperty());
        txt_title.textProperty().bindBidirectional(viewModel.titleProperty());

        txt_title.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setTitle(newValue);
        });

        txt_helpText.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setHelpText(newValue);
        });

        txt_content.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setContent(newValue);
        });

        txt_buttonText.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setButtonText(newValue);
        });


    }
    public void setViewModel(AudibleSound_VM viewModel){
        this.viewModel = viewModel;
        bindViewModel();
    }



}
