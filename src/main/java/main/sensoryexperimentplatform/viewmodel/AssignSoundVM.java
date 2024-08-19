package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.controllers.SoundSingleton;
import main.sensoryexperimentplatform.models.AudibleInstruction;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Sound;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class AssignSoundVM {
    private Sound sound;
    private String selectedSoundName;
    private AudibleSound_VM audibleSound_vm;

    private AudibleInstruction audibleInstruction;



    public AssignSoundVM() throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        this.sound = SoundSingleton.getInstance();


    }
    public String getSoundName(){
        return audibleInstruction.getSoundName();
    }



    public ArrayList<String> getListNameshow() {
        return new ArrayList<>(sound.getSoundNameshow());
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





    public String getSelectedSoundName() {
        return selectedSoundName;
    }

    public void setSelectedSoundName(String selectedSoundName) {
        this.selectedSoundName = selectedSoundName;
    }
    //for testing

    public Sound getSound(){
        return sound;
    }


}