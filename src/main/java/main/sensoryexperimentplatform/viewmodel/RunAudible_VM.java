package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunAudibleController;
import main.sensoryexperimentplatform.models.AudibleInstruction;
import main.sensoryexperimentplatform.models.Notice;
import main.sensoryexperimentplatform.models.Sound;

import java.io.IOException;

public class RunAudible_VM implements RunStages{
    private AudibleInstruction audibleInstruction;
    private StringProperty title, content, button,helpText;
    private StringProperty soundName;



    public RunAudible_VM(AudibleInstruction audibleInstruction){
        this.audibleInstruction = audibleInstruction;
        button = new SimpleStringProperty(audibleInstruction.getButtonText());
        title = new SimpleStringProperty(audibleInstruction.getTitle());
        content = new SimpleStringProperty(audibleInstruction.getContent());
        soundName = new SimpleStringProperty(audibleInstruction.getSoundName());

        helpText = new SimpleStringProperty(audibleInstruction.getHelpText());

    }

    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunAudible.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);

        RunAudibleController controller = loader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleRunButtons(Button btn_next, Button btn_back) {
        btn_next.setDisable(false);
        btn_next.textProperty().bind(this.buttonProperty());

    }
    public StringProperty getTitle(){
        return title;
    }
    public StringProperty getContent(){
        return content;
    }
    public StringProperty getHelpText(){
        return helpText;
    }
    public StringProperty getSoundName(){
        return soundName;
    }
    public  void playSound(){
        audibleInstruction.playSound(audibleInstruction.getSoundName());
    }

    public StringProperty buttonProperty() {
        return button;
    }



}
