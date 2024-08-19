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

    public void bind(){
        listSoundName.addListener((observableValue, oldValue, newValue) -> changeSelectedSound(newValue));
    }


    private void changeSelectedSound(ObservableList<String> newValue){
        sound.setSoundNameshow(newValue);

    }
    public ListProperty<String> listSoundNameProperty() {
        return listSoundName;
    }
    public ObservableList<String> getSoundsName(){
        return listSoundName.get();
    }

    public void setAudibleSoundName(String soundName){
      audibleInstruction.setSoundName(soundName);
    }

    public String getAudibleSoundName(){
        return audibleInstruction.getSoundName();
    }
    public void setAudibleFilePath(String filePath) {
        audibleInstruction.setFilePath(filePath);
    }

    public void playSound(String name) {
       sound.playSound(name);
    }

    public void stopSound(String name) {
       sound.stopSound(name);
    }

    public void removeSound(String name) {
       sound.getSoundNameshow().remove(name);
    }
    public void setSoundPath(String path) {
        sound.setSoundPath(path);
    }
    public String getSoundPath() {
        return sound.getSoundPath();
    }

    public void loadSound(String name, String filePath) {
        sound.loadSound(name, filePath);
    }


    public void addListSoundshow(String name){

        sound.getSoundNameshow().add(name);
    }
    public void exportSound( String filePath){
        sound.exportSoundToFolder(filePath,"src/main/java/main/sensoryexperimentplatform/sound");
    }


    public Sound getSound(){
        return sound;
    }


}