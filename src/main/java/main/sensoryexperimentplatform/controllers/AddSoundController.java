package main.sensoryexperimentplatform.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.viewmodel.AddSoundVM;
import main.sensoryexperimentplatform.viewmodel.AssignSoundVM;
import java.io.File;

public class AddSoundController {


    private NotiAddSound NotiAddSound;
    private AssignSoundController AssignSoundController;
    private AddSoundVM viewModel;



    @FXML
    private Button btn_browse;
    @FXML
    private Button btn_cancel;

    @FXML
    private TextField txt_file;

    @FXML
    private TextField txt_name;



    public void setViewModel(AddSoundVM viewModel, AssignSoundController assignSoundController) {
       this.viewModel = viewModel;
       this.AssignSoundController = assignSoundController;
       NotiAddSound = new NotiAddSound(assignSoundController);

    }

//    private void bindViewModel() {
//        txt_file.textProperty().bindBidirectional(viewModel.fileProperty());
//        txt_name.textProperty().bindBidirectional(viewModel.nameProperty());
//
//
//    }

    @FXML
    void btn_browse(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("WAV Files","*.wav")
        );
        File selectedFile = fileChooser.showOpenDialog(btn_browse.getScene().getWindow());
        if (selectedFile != null) {

            txt_file.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void btn_cancel(ActionEvent event) {
        Stage currentStage = (Stage) btn_cancel.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void btn_save(ActionEvent event) {
        if(!txt_file.getText().isEmpty() || !txt_name.getText().isEmpty()) {
        viewModel.addListSoundshow(txt_name.getText());
        viewModel.loadSound(txt_name.getText(), txt_file.getText());
        NotiAddSound.notifyObject();
        viewModel.exportSound(txt_name.getText(), txt_file.getText());
        Stage currentStage = (Stage) btn_cancel.getScene().getWindow();
        currentStage.close();}
    }

    @FXML
    void txt_file(ActionEvent event) {
    }

    @FXML
    void txt_name(ActionEvent event) {
    }
}
