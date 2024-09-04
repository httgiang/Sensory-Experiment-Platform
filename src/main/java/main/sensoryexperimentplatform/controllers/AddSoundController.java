package main.sensoryexperimentplatform.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.viewmodel.AssignSoundVM;
import java.io.File;

public class AddSoundController {


    private NotiAddSound NotiAddSound;
    private AssignSoundVM assignSoundVM;



    @FXML
    private Button btn_browse;
    @FXML
    private Button btn_cancel;

    @FXML
    private TextField txt_file;

    @FXML
    private TextField txt_name;



    public void setViewModel(AssignSoundController assignSoundController,AssignSoundVM assignSoundVM) {
       this.assignSoundVM = assignSoundVM;
       NotiAddSound = new NotiAddSound(assignSoundController);

    }


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
        if (!txt_file.getText().isEmpty() && !txt_name.getText().isEmpty()) {
            // Add the sound name to the list for display purposes
            assignSoundVM.addListSoundshow(txt_name.getText());

            // Export the sound to the desired folder first
            assignSoundVM.exportSound(txt_file.getText());


            // Set the sound path after export
            assignSoundVM.setSoundPath(txt_file.getText());

            // Load the sound using the assigned sound path
            assignSoundVM.loadSound(txt_name.getText(), txt_file.getText());

            // Notify the system that a sound has been added
            NotiAddSound.notifyObject();

            // Close the current stage
            Stage currentStage = (Stage) btn_cancel.getScene().getWindow();
            currentStage.close();
        }

    }

    @FXML
    void txt_file(ActionEvent event) {
    }

    @FXML
    void txt_name(ActionEvent event) {
    }
}
