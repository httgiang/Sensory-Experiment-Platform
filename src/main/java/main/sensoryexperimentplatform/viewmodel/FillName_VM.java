package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.sensoryexperimentplatform.models.Experiment;

public class FillName_VM {
    private StringProperty uid;
    private Experiment experiment;
    public FillName_VM (Experiment experiment){
        this.experiment = experiment;
        uid = new SimpleStringProperty("");

    }

    public StringProperty uid(){
        return uid;
    }

    public String getUid() {
        return uid.get();
    }

    public Experiment getExperiment() {
        return experiment;
    }
}
