package main.sensoryexperimentplatform.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

    public void setViewModel(FillName_VM viewModel) {
        this.viewModel = viewModel;
        Bindings.bindBidirectional(uid.textProperty(), viewModel.uid());

        // Set key event listener for Enter and Esc keys
        uid.setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                handleApproveBtn(null);  // Simulate approve button press
            } catch (IOException | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            handleCancelBtn(null);  // Simulate cancel button press
        }
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

    void close() {
        Stage stage = (Stage) uid.getScene().getWindow();
        stage.close();
    }

    private void runExperiment(Experiment experiment, String uid) throws IOException, CloneNotSupportedException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunExperiment.fxml"));
        Parent root = loader.load();

        RunController controller = loader.getController(); // Get the controller from the loader
        controller.initRunExperiment(experiment, uid);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setOnCloseRequest((WindowEvent event) -> {
            stage.close();
            controller.stopTimer();
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
