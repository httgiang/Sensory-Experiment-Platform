package main.sensoryexperimentplatform.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.utilz.PopUpType;
import main.sensoryexperimentplatform.viewmodel.AssignSoundVM;
import main.sensoryexperimentplatform.viewmodel.PopUpVM;

import java.io.File;
import java.io.IOException;

import static main.sensoryexperimentplatform.utilz.PopUpType.ERROR;
import static main.sensoryexperimentplatform.utilz.PopUpType.SUCCESS;

public class AddSoundController {


    private NotiAddSound NotiAddSound;
    private AssignSoundVM assignSoundVM;



    @FXML
    private Button btn_browse;
    @FXML
    private Button btn_cancel;

    @FXML
    private TextField txt_file;

    private Stage ownerStage;


    @FXML
    private TextField txt_name;

    public void setOwnerStage(Stage ownerStage){
        this.ownerStage = ownerStage;
    }



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
    void btn_save(ActionEvent event) throws IOException {
        File file = new File(txt_file.getText());


        if (!txt_file.getText().isEmpty() && !txt_name.getText().isEmpty()) {
             if (assignSoundVM.listSoundNameProperty().contains(txt_name.getText())) {
                PopUpVM popUpVM = new PopUpVM(ERROR,"The sound name already exits in the system",ownerStage);

            }
           else  if (!file.exists()) {
                PopUpVM popUpVM = new PopUpVM(ERROR,"The experiment file cannot be found",ownerStage);
                return;
            }
             else{
            assignSoundVM.addListSoundshow(txt_name.getText());
            assignSoundVM.exportSound(txt_file.getText());
            assignSoundVM.setSoundPath(txt_file.getText());
            assignSoundVM.loadSound(txt_name.getText(), txt_file.getText());
            NotiAddSound.notifyObject();
            Stage currentStage = (Stage) btn_cancel.getScene().getWindow();
            currentStage.close();
            PopUpVM popUpVM = new PopUpVM(SUCCESS,"The sound was Successfully import",ownerStage);
        }
        }

        else if(txt_file.getText().isEmpty() && !txt_name.getText().isEmpty()){
            PopUpVM popUpVM = new PopUpVM(ERROR,"A Sound to import must be selected",ownerStage);

        }
        else if(!txt_file.getText().isEmpty() && txt_name.getText().isEmpty()){
            PopUpVM popUpVM = new PopUpVM(ERROR,"The sound must called something",ownerStage);

        }
         else if(!txt_file.getText().isEmpty() && !txt_name.getText().isEmpty()){
            PopUpVM popUpVM = new PopUpVM(ERROR,"The sound must called something",ownerStage);

        }

    }

    @FXML
    void txt_file(ActionEvent event) {
    }

    @FXML
    void txt_name(ActionEvent event) {
    }
}
