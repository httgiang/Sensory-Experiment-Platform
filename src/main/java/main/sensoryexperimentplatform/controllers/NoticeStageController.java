package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import main.sensoryexperimentplatform.viewmodel.NoticeStage_VM;

public class NoticeStageController {
    private NoticeStage_VM noticeStage_vm;
    private NoticeStage_VM viewModel;

    @FXML
    private TextField txt_buttonText;

    @FXML
    private TextArea txt_content;

    @FXML
    private TextArea txt_helptext;
    @FXML
    private TextField txt_title;
    @FXML
    private CheckBox cbx_sound;




    @FXML
    void txt_buttonText(MouseEvent event) {

    }

    @FXML
    void txt_content(MouseEvent event) {

    }

    @FXML
    void txt_helptext(MouseEvent event) {

    }



    public void setNoticeStage_vm( NoticeStage_VM viewModel){
        this.viewModel = viewModel;
        bindViewModel();
    }


    public void bindViewModel(){
        txt_helptext.textProperty().bindBidirectional(viewModel.helpTextProperty());
        txt_buttonText.textProperty().bindBidirectional(viewModel.buttonTextProperty());
        txt_title.textProperty().bindBidirectional(viewModel.titleProperty());
        txt_content.textProperty().bindBidirectional(viewModel.contentProperty());
        cbx_sound.selectedProperty().bindBidirectional(viewModel.alertProperty());
        txt_buttonText.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setButtonText(newValue);
        });

        txt_helptext.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setHelpText(newValue);
        });

        txt_content.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setContent(newValue);
        });
        txt_title.textProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setTitle(newValue);
        });

        cbx_sound.selectedProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setAlert(newValue);
        });




    }
    @FXML
    void txt_title(MouseEvent event) {

    }

}
