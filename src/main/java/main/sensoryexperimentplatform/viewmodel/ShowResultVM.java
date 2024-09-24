package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Participant;

import static main.sensoryexperimentplatform.utilz.Constants.saveResultPath;

public class ShowResultVM {

    private String filePath;

    public ShowResultVM(Experiment experiment) {
        // Generate the file path based on the experiment
        this.filePath = generateFilePath(experiment);
    }
    public ShowResultVM(Experiment experiment, Participant participant) {
        this.filePath = generateFilePathParticipant(experiment, participant);
    }

    private String generateFilePathParticipant(Experiment experiment, Participant participant) {
        return "results" + "/" + experiment.getExperimentName() +"/"+ participant.getUid() + ".csv";
    }
    private String generateFilePath(Experiment experiment) {
        // Logic to generate the file path based on the experiment
        return "results" + "/" + experiment.getExperimentName() + ".csv";
    }

    public String getFilePath() {
        return filePath;
    }
}
