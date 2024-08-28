package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.AddAudibleSoundController;
import main.sensoryexperimentplatform.controllers.RunAudibleController;
import main.sensoryexperimentplatform.controllers.SoundSingleton;
import main.sensoryexperimentplatform.models.AudibleInstruction;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Model;
import main.sensoryexperimentplatform.models.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public class AudibleSound_VM implements ViewModel{
    private StringProperty title;
    private StringProperty content;
    private StringProperty helpText;
    private StringProperty buttonText;
    private StringProperty soundName;
    private StringProperty filePath;
    private AudibleInstruction audibleInstruction;
    private Sound sound;
    private AssignSoundVM assignSoundVM;
    private Experiment experiment;

    public AudibleSound_VM() throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        this.experiment = experiment;
        this.audibleInstruction = new AudibleInstruction("Default Notice Stage", null, null,null,null, null);
        // if there exits any bug this maybe a problem
        initListener();

    }
    public AudibleSound_VM(AudibleInstruction audibleInstruction) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        this.audibleInstruction = audibleInstruction;
        initListener();

    }

    public AudibleSound_VM(IfConditionalStatementVM ifConditionalStatementVM) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        this.audibleInstruction = new AudibleInstruction("Default Notice Stage", null, null,null,null, null);
        initListener();
        ifConditionalStatementVM.addIf(audibleInstruction);
    }
    public AudibleSound_VM(ElseConditionalStatementVM elseConditionalStatementVM) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        this.audibleInstruction = new AudibleInstruction("Default Notice Stage", null, null,null,null, null);
        initListener();
        elseConditionalStatementVM.addElse(audibleInstruction);
    }



    private void initListener(){
        this.title = new SimpleStringProperty(audibleInstruction.getTitle());
        this.content = new SimpleStringProperty(audibleInstruction.getContent());
        this.buttonText = new SimpleStringProperty(audibleInstruction.getButtonText());
        this.helpText = new SimpleStringProperty(audibleInstruction.getHelpText());
        this.soundName = new SimpleStringProperty(audibleInstruction.getSoundName());
        this.filePath = new SimpleStringProperty(audibleInstruction.getFilePath());
        this.sound = SoundSingleton.getInstance();

    }


    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty contentProperty() {
        return content;
    }

    public StringProperty buttonTextProperty() {
        return buttonText;
    }
    public StringProperty helpTextProperty() {
        return helpText;
    }
    public StringProperty filePathProperty() {
        return filePath;
    }

    public String getContent(){
        return content.get();
    }
    public String getButton(){
        return buttonText.get();
    }
    public String getHelpText(){
        return helpText.get();
    }
    public String getFilePath(){
        return filePath.get();
    }
    public void setHelpText(String newValue) {audibleInstruction.setHelpText(newValue);

    }



    public void setButtonText(String newValue) {;
        audibleInstruction.setButtonText(newValue);
    }
    public void setTitle(String newValue) {
        audibleInstruction.setTitle(newValue);
    }
    public void setContent(String newValue) {
        audibleInstruction.setContent(newValue);
    }
    public void setSoundName(String name) {
        audibleInstruction.setSoundName(name);
    }
    public String getSoundName(){
        return audibleInstruction.getSoundName();
    }
    public StringProperty soundNameProperty() {
        return soundName;
    }

    public AudibleInstruction getAudibleIntruction(){
        return audibleInstruction;
    }


    @Override
    public Model getModel() {
        return audibleInstruction;
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunAudible.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);

        RunAudibleController controller = loader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddAudibleSound.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        AddAudibleSoundController controller = fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleEditButtons(Button btn_addPeriodicStage, Button btn_addCourse, Button btn_assignSound,
                                  Button btn_addFoodAndTaste, Button btn_addAudibleInstruction
            , Button btn_addInput, Button btn_noticeStage,
                                  Button  btn_addTimer, Button btn_addQuestionStage,
                                  Button btn_addRatingContainer, Button btn_addTasteTest, Button btn_addConditionalStatement) throws IOException {
        btn_addFoodAndTaste.setDisable(true);
        btn_addPeriodicStage.setDisable(true);
        btn_addAudibleInstruction.setDisable(false);
        btn_addInput.setDisable(false);
        btn_noticeStage.setDisable(false);
        btn_addTimer.setDisable(false);
        btn_addQuestionStage.setDisable(false);
        btn_addRatingContainer.setDisable(false);
        btn_addTasteTest.setDisable(false);
        btn_addConditionalStatement.setDisable(false);
        btn_addCourse.setDisable(false);
        btn_assignSound.setDisable(false);
    }

    @Override
    public void handleRunButtons(Button btn_next, Button btn_back) {
        btn_back.setDisable(false);
        btn_next.setDisable(false);
        btn_next.textProperty().bind(this.buttonTextProperty());
    }


    @Override
    public String toString() {
        return "[Audio] " + audibleInstruction.getTitle();
    }

    public  void playSound(){
        audibleInstruction.playSound(audibleInstruction.getSoundName());
    }
}
