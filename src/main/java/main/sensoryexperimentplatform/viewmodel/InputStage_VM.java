package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;

public class InputStage_VM implements ViewModel{
    private Input input;
    private Experiment experiment;
    //    private final ListProperty<Object> stages = new SimpleListProperty<>(FXCollections.observableArrayList());
    private StringProperty questionText, result, helpText, button;
    private BooleanProperty alert;

    public InputStage_VM(){
        this.input = new Input("User input", null,null, false);
        initListener();
       // experiment.addInput(input);

    }
    public InputStage_VM( Input input){
        this.input = input;
        initListener();
    }
    private void initListener(){
        questionText = new SimpleStringProperty(input.getQuestionText());
        helpText = new SimpleStringProperty(input.getHelpText());
        button = new SimpleStringProperty(input.getButtonText());
        alert = new SimpleBooleanProperty(input.isAlert());
        result = new SimpleStringProperty(input.getResult());
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
    public Model getModel() {
        return input;
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunInputStage.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);

        RunInputController controller = loader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("InputStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);

        InputStageController controller =fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleEditButtons(Button button1, Button button2, Button button3,
                                  Button button4, Button button5, Button button6,
                                  Button button7, Button button8, Button button9,
                                  Button button10, Button button11, Button button12)
            throws IOException {
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
    public void handleRunButtons(Button btn_next, Button btn_back) {
        btn_back.setDisable(false);
        btn_next.setDisable(false);
        btn_next.textProperty().bind(this.buttonTextProperty());
    }

    @Override
    public String toString(){
        return "[User Input] "+ questionText.get();
    }
    public BooleanProperty getPlayAlert() {
        return alert;
    }
    public void playAlert(){
        input.playSound();
    }
    public StringProperty getResult() {
        return result;
    }
    public void setResult(String result) {
        input.setResult(result);
    }


}