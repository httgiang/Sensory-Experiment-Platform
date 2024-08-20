package main.sensoryexperimentplatform.controllers;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.listOfExperiment;

public class PopUpConfirmController {

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_proceed;

    @FXML
    private Label deleteMsg;
    private Experiment experiment;

    public void setMessage(Experiment experiment, String msg){
        this.experiment = experiment;
        deleteMsg.setText(msg);
    }
    @FXML
    void cancel() {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void proceed(ActionEvent event) {
        try {
            listOfExperiment.deleteExperiment(experiment);;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cancel();
    }

}

