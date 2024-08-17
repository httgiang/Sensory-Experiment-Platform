package main.sensoryexperimentplatform.viewmodel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.utilz.*;

import java.io.IOException;

import static main.sensoryexperimentplatform.utilz.PopUpType.*;

public class PopUpVM {


    public PopUpVM(PopUpType type, String msg, Experiment experiment) throws IOException {
        if(type == ERROR){
            popUpError(msg);
        } else if(type == SUCCESS){
            popUpSuccess(msg);
        } else if(type == CONFIRM){
            popUPConfirm(experiment, msg);
        }
    }

    private void popUpError(String msg) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("PopUpError.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        PopUpErrorController controller = fxmlLoader.getController();
        controller.setMessage(msg);
        stage.show();
    }

    private void popUpSuccess(String msg) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("PopUpSuccess.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        PopUpSuccessController controller = fxmlLoader.getController();
        controller.setMessage(msg);
        stage.show();
    }

    private void popUPConfirm(Experiment experiment, String msg) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("PopUpConfirm.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        PopUpConfirmController controller = fxmlLoader.getController();
        controller.setMessage(experiment, msg);
        stage.show();
    }
}
