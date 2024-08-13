package main.sensoryexperimentplatform.models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    private static final long serialVersionUID = 1L; // for serialization

    private Map<String, String> soundFilePathMap; // To store file paths for serialization
    private transient Map<String, Clip> soundMap; // transient to avoid serialization
    private ArrayList<String> soundNameshow; // for the appearance of sound


    public Sound(){
        soundMap = new HashMap<>();
        soundNameshow = new ArrayList<>();
        soundNameshow.add("boop");
        loadSound("boop","src/main/java/main/sensoryexperimentplatform/sound/boop-741-mhz-39314.wav");
        loadSound("stomp","src/main/java/main/sensoryexperimentplatform/sound/stompwav-14753.wav");
        soundNameshow.add("stomp");

    }
    public ArrayList<String> getSoundNameshow() {
        return soundNameshow;
    }
    public void addSoundShow(String name){
        soundNameshow.add(name);
    }



    public Map<String, Clip> getSoundMap() {
        return soundMap;
    }

    public void loadSound(String name, String filePath) {
        try {
            File soundFile = new File(filePath);

            if (!soundFile.exists()) {
                throw new IllegalArgumentException("Sound file not found: " + filePath);
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            soundMap.put(name, clip);
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
    public void exportSoundToFolder(String name, String filePath, String folderPath) {
        File sourceFile = new File(filePath);
        File targetFile = new File(folderPath + File.separator + sourceFile.getName());

        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadSoundsFromFolder(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder path: " + folderPath);
        }


        File[] files = folder.listFiles();


        if (files == null) {
            throw new IllegalStateException("Unable to list files in the folder: " + folderPath);
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".wav")) {
                String fileName = file.getName();
                String soundName = fileName.substring(0, fileName.lastIndexOf('.'));
                loadSound(soundName, file.getAbsolutePath());
            }
        }
    }







}
