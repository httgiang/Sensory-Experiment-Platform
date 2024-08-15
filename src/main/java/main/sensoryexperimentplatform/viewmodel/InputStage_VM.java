package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.InputStageController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Input;

import java.io.IOException;
import java.util.Stack;

public class InputStage_VM implements Stages {
    private Input input;
    private Experiment experiment;
    //    private final ListProperty<Object> stages = new SimpleListProperty<>(FXCollections.observableArrayList());
    private StringProperty questionText;
    private StringProperty helpText;
    private StringProperty button;
    private BooleanProperty alert;

    public InputStage_VM(Experiment experiment){
        this.experiment = experiment;
        this.input = new Input("User input", null,null, false);
        questionText = new SimpleStringProperty(input.getQuestionText());
        helpText = new SimpleStringProperty(input.getHelpText());
        button = new SimpleStringProperty(input.getButtonText());
        alert = new SimpleBooleanProperty(input.isAlert());
        experiment.addInput(input);

    }
    public InputStage_VM(Experiment experiment, Input input){
        this.input = input;
        this.experiment = experiment;
        questionText = new SimpleStringProperty(input.getQuestionText());
        helpText = new SimpleStringProperty(input.getHelpText());
        button = new SimpleStringProperty(input.getButtonText());
        alert = new SimpleBooleanProperty(input.isAlert());


    }
    //questionText
    public StringProperty questionProperty() {
        return questionText;
    }
    //helpText
    public StringProperty helpTextProperty() {
        return helpText;
    }
    //buttonText
    public StringProperty buttonTextProperty() {
        return button;
    }
    //alert
    public BooleanProperty alertProperty() {
        return alert;
    }

    //getQuestion
    public String getQuestionText(){
        return questionText.get();
    }
    //get button
    public String getButton(){
        return button.get();
    }
    //getHelpText
    public String getHelpText(){
        return helpText.get();
    }
    //getAlert
    public boolean getAlert(){
        return alert.get();
    }
    //setHelpText
    public void setHelpText(String newValue) {
        input.setHelpText(newValue);
    }
    //setQuestionText
    public void setQuestion(String newValue) {
        input.setQuestionText(newValue);
    }
    //setButtonText
    public void setButtonText(String newValue) {
        input.setButtonText(newValue);
    }
    //setAlert
    public void setAlert(Boolean newValue) {
        input.setAlert(newValue);
    }

    public Input getInput(){
        return input;
    }

    @Override
    public void loadInterface(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("InputStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);

        InputStageController controller =fxmlLoader.getController();
        controller.setViewModel(this);

    }

    @Override
    public void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Stack<RatingContainer_VM> ratingContainerVm) throws IOException {
        button1.setDisable(true);
        button3.setDisable(true);
        button4.setDisable(true);
        button7.setDisable(false);
        button5.setDisable(false);
        button2.setDisable(false);
        button11.setDisable(false);
        button6.setDisable(false);
        button8.setDisable(false);
        button12.setDisable(false);
        button10.setDisable(false);
        button9.setDisable(false);


    }
    @Override
    public String toString(){
        return "[User Input] "+ questionText.get();
    }

}