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
    private StringProperty title;
    private StringProperty content;
    private StringProperty button;
    private BooleanProperty alert;

    public InputStage_VM(Experiment experiment){
        this.experiment = experiment;
        this.input = new Input("User input", null,null, false);
        title = new SimpleStringProperty(input.getTitle());
        content = new SimpleStringProperty(input.getContent());
        button = new SimpleStringProperty(input.getButtonText());
        alert = new SimpleBooleanProperty(input.isAlert());
        experiment.addInput(input);

    }
    public InputStage_VM(Experiment experiment, Input input){
        this.input = input;
        this.experiment = experiment;
        title = new SimpleStringProperty(input.getTitle());
        content = new SimpleStringProperty(input.getContent());
        button = new SimpleStringProperty(input.getButtonText());
        alert = new SimpleBooleanProperty(input.isAlert());


    }



//    public void addInputStage() {
//
//        String title = this.title.get();
//        String content = this.content.get();
//        String buttonText = this.button.get();
//        boolean alert = this.alert.get();
//        Input stage = new Input(title, content, buttonText, alert);
//        experiment.addInputStage(title, content, buttonText, alert);
////        stages.add(stage);
//
//    }

    //    public ObservableList<Object> getStages() {
//        return stages.get();
//    }
//
//    public ListProperty<Object> stagesProperty() {
//        return stages;
//    }
    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty contentProperty() {
        return content;
    }

    public StringProperty buttonTextProperty() {
        return button;
    }

    public BooleanProperty alertProperty() {
        return alert;
    }
    public String getContent(){
        return content.get();
    }
    public String getButton(){
        return button.get();
    }

    public boolean getAlert(){
        return alert.get();
    }

    public void setHelpText(String newValue) {
        input.setContent(newValue);
    }
    public void setQuestion(String newValue) {
        input.setTitle(newValue);
    }
    public void setButtonText(String newValue) {
        input.setButtonText(newValue);
    }
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
    public void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Stack<ratingContainer_VM> ratingContainerVm) throws IOException {

    }
    @Override
    public String toString(){
        return "[User Input] "+ title.get();
    }

}