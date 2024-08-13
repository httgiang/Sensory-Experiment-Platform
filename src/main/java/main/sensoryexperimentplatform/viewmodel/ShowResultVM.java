package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.Experiment;

public class ShowResultVM {

    private String filePath;

    public ShowResultVM(Experiment experiment) {
        // Generate the file path based on the experiment
        this.filePath = generateFilePath(experiment);
    }

    private String generateFilePath(Experiment experiment) {
        // Logic to generate the file path based on the experiment
        return "src/results/" + experiment.getExperimentName() + "_" + experiment.getVersion() + ".csv";
    }

    public String getFilePath() {
        return filePath;
    }
}
