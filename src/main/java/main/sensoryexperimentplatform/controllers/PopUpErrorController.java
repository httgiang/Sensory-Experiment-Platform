package main.sensoryexperimentplatform.controllers;

import javafx.fxml.*;
import javafx.scene.control.*;


public class PopUpErrorController {

    @FXML
    private Label errorMsg;
    public void setMessage(String message){
        errorMsg.setText(message);
    }


}
