package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import main.sensoryexperimentplatform.controllers.SoundSingleton;
import main.sensoryexperimentplatform.models.AudibleInstruction;
import main.sensoryexperimentplatform.models.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;

public class AssignSoundVM {
    private Sound sound;
    private String selectedSoundName;
    private ListProperty<String> listSoundName;
    private AudibleInstruction audibleInstruction;




    public AssignSoundVM(AudibleInstruction audibleInstruction) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        this.audibleInstruction = audibleInstruction;
        this.sound = SoundSingleton.getInstance();
        listSoundName = new SimpleListProperty<>(sound.getSoundNameshow());
        bind();

    }
    public String getSoundName(){
        return audibleInstruction.getSoundName();
    }

    public void bind(){
        listSoundName.addListener((observableValue, oldValue, newValue) -> changeSelectedSound(newValue));
    }


    private void changeSelectedSound(ObservableList<String> newValue){
        sound.setSoundNameshow(newValue);

    }
    //get list sound name to show
    public ListProperty<String> listSoundNameProperty() {
        return listSoundName;
    }

    //set sound name for audible stage
    public void setAudibleSoundName(String soundName){
        audibleInstruction.setSoundName(soundName);
    }
    //get Audible sound name
    public String getAudibleSoundName(){
        return audibleInstruction.getSoundName();
    }
    // set audible file path
    public void setAudibleFilePath(String filePath) {
        audibleInstruction.setFilePath(filePath);
    }
    // method to play sound
    public void playSound(String name) {
        sound.playSound(name);
    }
    // method to stop sound
    public void stopSound(String name) {
        sound.stopSound(name);
    }
    // method to remove sound
    public void removeSound(String name) {
        sound.getSoundNameshow().remove(name);
    }
    // method to set sound path
    public void setSoundPath(String path) {
        sound.setSoundPath(path);
    }
    // method to get sound path
    public String getSoundPath() {
        return sound.getSoundPath();
    }
    // method to load sound
    public void loadSound(String name, String filePath) {
        sound.loadSound(name, filePath);
    }

    // method to addListSoundShow
    public void addListSoundshow(String name){

        sound.getSoundNameshow().add(name);
    }
    //method to export sound
    public void exportSound( String filePath){
        sound.exportSoundToFolder(filePath,"src/main/resources/sound/");
    }
    public String retriveSoundPath(String soundName){
        return sound. retriveSoundPath(soundName);
    }


    public Sound getSound(){
        return sound;
    }


}