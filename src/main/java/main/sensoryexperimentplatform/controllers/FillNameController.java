package main.sensoryexperimentplatform.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.viewmodel.*;
import main.sensoryexperimentplatform.models.Experiment;

import java.io.IOException;

public class FillNameController {
    private FillName_VM viewModel;

    @FXML
    private TextField uid;
    public void setViewModel(FillName_VM viewModel){
        this.viewModel = viewModel;
        Bindings.bindBidirectional(uid.textProperty(), viewModel.uid());
    }

    @FXML
    void handleApproveBtn(MouseEvent event) throws IOException, CloneNotSupportedException {
        Experiment experiment = new Experiment(viewModel.getExperiment());
        runExperiment(experiment, viewModel.getUid());
        close();
    }

    @FXML
    void handleCancelBtn(MouseEvent event) {
        close();
    }
    void close(){
        Stage stage = (Stage) uid.getScene().getWindow();
        stage.close();
    }

    private void runExperiment(Experiment experiment, String uid) throws IOException, CloneNotSupportedException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunExperiment.fxml"));
        Parent root = loader.load();

        RunController controller = loader.getController(); // Get the controller from the loader
        RunExperiment_VM viewModel = new RunExperiment_VM(experiment, uid);
        controller.setViewModel(viewModel);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setOnCloseRequest((WindowEvent event) -> {
            // Perform any necessary cleanup here
            System.out.println("Experiment is closing");
            stage.close();
            controller.stopTimer();
//            scene.getWindow().hide();
        });
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("Press any keys to exit full screen");
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                stage.setFullScreen(!stage.isFullScreen()); // Toggle full-screen mode
            }
        });
        stage.show();


    }


}
