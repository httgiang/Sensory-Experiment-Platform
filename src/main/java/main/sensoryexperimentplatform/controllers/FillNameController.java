package main.sensoryexperimentplatform.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
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
        close();

        Experiment experiment = new Experiment(viewModel.getExperiment());
        runExperiment(experiment, viewModel.getUid());

    }

    @FXML
    void handleCancelBtn(MouseEvent event) {
        close();
    }

    void close() {
        // Close the current stage (window)
        Stage stage = (Stage) uid.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }

    private void runExperiment(Experiment experiment, String uid) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunExperiment.fxml"));
        Parent root = loader.load();

        RunController controller = loader.getController(); // Get the controller from the loader
        Experiment s = new Experiment(experiment);
        controller.initRunExperiment(s, uid);

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setOnCloseRequest((WindowEvent event) -> {
            stage.close();
            controller.stopTimer();
        });

//        // Disable ESC key from exiting full-screen mode
//        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() == KeyCode.ESCAPE) {
//                event.consume(); // Consume the ESC key press
//            }
//        });
//
//        // Disable any key combination from exiting full-screen mode
//        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint(""); // Optionally remove the exit hint message

        stage.showAndWait();
    }
}
