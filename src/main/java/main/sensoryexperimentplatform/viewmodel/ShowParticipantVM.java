package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.Experiment;

public class ShowParticipantVM {
    private String folderPath;
    Experiment experiment;

    public ShowParticipantVM(Experiment experiment) {
        this.experiment = experiment;
        // Generate the file path based on the experiment
        this.folderPath = generateFolderPath(experiment);
    }

    private String generateFolderPath(Experiment experiment) {
        // Logic to generate the file path based on the experiment
        return "results" + "/" + experiment.getExperimentName();
    }

    public String getFolderPath() {
        return folderPath;
    }
    public Experiment getExperiment() {
        return experiment;
    }
}
