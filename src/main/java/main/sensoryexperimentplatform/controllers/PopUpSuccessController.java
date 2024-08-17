package main.sensoryexperimentplatform.controllers;
import javafx.fxml.*;
import javafx.scene.control.*;

public class PopUpSuccessController {

    @FXML
    private Label successMsg;
    public void setMessage(String message){
        successMsg.setText(message);
    }

}
