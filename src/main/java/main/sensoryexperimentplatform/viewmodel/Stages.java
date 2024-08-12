package main.sensoryexperimentplatform.viewmodel;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Stack;

public interface Stages {
    void loadInterface(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS) throws IOException;
    void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS,
                           Button button1, Button button2, Button button3, Button button4, Button button5,
                           Button button6, Button button7, Button button8, Button button9, Button button10,
                           Button button11, Button button12, Stack<RatingContainer_VM> rating) throws IOException;


}
