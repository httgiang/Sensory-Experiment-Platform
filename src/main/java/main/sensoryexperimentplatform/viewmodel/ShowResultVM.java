package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.Experiment;

public class ShowResultVM {
    private Experiment experiment;
    public ShowResultVM (Experiment experiment) {
        this.experiment = experiment;
    }
    public Experiment getExperiment () {
        return experiment;
    }
}
