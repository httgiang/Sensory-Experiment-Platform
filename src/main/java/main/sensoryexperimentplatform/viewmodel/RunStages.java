package main.sensoryexperimentplatform.viewmodel;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public interface RunStages {
     void loadInterface(AnchorPane anchorPane) throws IOException;

     void handleRunButtons(Button btn_next, Button btn_back);

}
