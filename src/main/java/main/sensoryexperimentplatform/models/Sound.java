package main.sensoryexperimentplatform.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Sound {


    private String soundName;
    private String soundPath;

    private transient Map<String, Clip> soundMap = new HashMap<>();
    private ObservableList<String> soundNameshow = FXCollections.observableArrayList(); // for the appearance of sound
    private transient Map<String, String> soundPathMap = new HashMap<>(); // Map to store file paths


    public Sound(){
        initSound();
    }
    public ObservableList<String> getSoundNameshow() {
        if (soundNameshow == null) {
            soundNameshow = FXCollections.observableArrayList();
            initSound();
        }
        return soundNameshow;
    }

    public void addNewSound(String soundName) {
        // Ensure the list is initialized
        getSoundNameshow();
        // Add the new sound name to the list
        soundNameshow.add(soundName);
    }

    public void setSoundNameshow(ObservableList<String> soundNameshow) {
        this.soundNameshow = soundNameshow;
    }


    public void initSound(){
        soundNameshow.add("boop");
        soundNameshow.add("stomp");
        loadSound("boop","src/main/resources/sound/boop-741-mhz-39314.wav");
        loadSound("stomp","src/main/resources/sound/stompwav-14753.wav");
    }


    public Map<String, Clip> getSoundMap() {
        return soundMap;
    }
    public void loadSound(String name, String filePath) {
        try {
            InputStream fileStream = new FileInputStream(filePath);
            BufferedInputStream bufferedStream = new BufferedInputStream(fileStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            soundMap.put(name, clip);
            soundPathMap.put(name, filePath);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }



    public void playSound(String name) {
        Clip clip = soundMap.get(name);
        if (clip != null) {
            stopAllSounds();
            clip.start();
        }
    }

    public void stopSound(String name) {
        Clip clip = soundMap.get(name);
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    public void stopAllSounds() {
        for (Clip clip : soundMap.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }

    public void exportSoundToFolder( String filePath, String folderPath) {
        File sourceFile = new File(filePath);
        File targetFile = new File(folderPath + File.separator + sourceFile.getName());

        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String retriveSoundPath(String soundName) {
        return soundPathMap.get(soundName); // Retrieve the file path from the map
    }


    public String getSoundPath() {
        File sourceFile = new File(soundPath);
        File soundFile = new File("src/main/resources/sound/" + File.separator + sourceFile.getName());

        return soundFile.getAbsolutePath();

    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }










}