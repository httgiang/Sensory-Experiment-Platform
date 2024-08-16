package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.Experiment;

import static main.sensoryexperimentplatform.utilz.Constants.saveResultPath;

public class ShowResultVM {

    private String filePath;

    public ShowResultVM(Experiment experiment) {
        // Generate the file path based on the experiment
        this.filePath = generateFilePath(experiment);
    }

    private String generateFilePath(Experiment experiment) {
        // Logic to generate the file path based on the experiment
        return "results" + "/" + experiment.getExperimentName() + ".csv";
    }

    public String getFilePath() {
        return filePath;
    }
}
