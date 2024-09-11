package main.sensoryexperimentplatform.viewmodel;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.models.Model;

import java.io.IOException;

public interface ViewModel {

    Model getModel();
    void loadRunInterface(AnchorPane anchorPane) throws IOException;
    void loadEditInterface(AnchorPane anchorPane) throws IOException;
    void handleEditButtons(Button button1, Button button2, Button button3, Button button4, Button button5,
                           Button button6, Button button7, Button button8, Button button9, Button button10,
                           Button button11, Button button12) throws IOException;

    void handleRunButtons(Button btn_next, Button btn_back, Tooltip tooltip, Tooltip nextButtonTooltip,ImageView help_image);
}
