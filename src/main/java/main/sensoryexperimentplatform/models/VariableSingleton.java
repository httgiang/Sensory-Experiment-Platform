package main.sensoryexperimentplatform.models;

public class VariableSingleton {
    private static Variable instance;

    private VariableSingleton() {
    }
    public static Variable getInstance() {
        if (instance == null) {
            instance = new Variable();
        }
        return instance;
    }
}
