package main.sensoryexperimentplatform.controllers;

import main.sensoryexperimentplatform.viewmodel.Stages;

public class Wrapper {
    private String key;
    private Stages Stages;
    public Wrapper (String key, Stages Stages){
        this.key = key;
        this.Stages = Stages;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Stages getChoose() {
        return Stages;
    }

    public void setChoose(Stages Stages) {
        this.Stages = Stages;
    }
}
