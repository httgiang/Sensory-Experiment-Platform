package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.sensoryexperimentplatform.controllers.SoundSingleton;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Sound;

public class AddSoundVM {
    private Sound sound;

    public AddSoundVM(){
        this.sound = SoundSingleton.getInstance();

    }
    public void loadSound(String name, String filePath) {
        sound.loadSound(name, filePath);
    }
    public Sound getSound(){
        return sound;
    }
    public void addListSoundshow(String name){

        sound.getSoundNameshow().add(name);
    }

}
