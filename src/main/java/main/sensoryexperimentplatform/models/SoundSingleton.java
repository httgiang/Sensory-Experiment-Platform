package main.sensoryexperimentplatform.models;


public class SoundSingleton {
    private static Sound instance;

    private SoundSingleton() {}

    public static Sound getInstance() {
        if (instance == null) {
            instance = new Sound();
        }
        return instance;
    }
}
