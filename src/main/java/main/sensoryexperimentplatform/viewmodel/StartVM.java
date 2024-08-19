package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;

public class StartVM implements ViewModel{
    private Experiment experiment;
    private Start start;
    private SimpleStringProperty title;
    private SimpleStringProperty content;
    private ObjectProperty<Color> colorBackGround;
    private ObjectProperty<Color> colorDisable;
    private ObjectProperty<Color> TextColor;
    private SimpleStringProperty StartOfStageDelay;
    private SimpleStringProperty EndOfStageDelay;
    private SimpleBooleanProperty balance;
    private SimpleStringProperty buttonText;
    public StartVM(Experiment experiment){
        this.experiment = experiment;
        this.start = new Start("Start Experiment","This is the first stage of the experiment" , null, false, null, null, 0,100, null);
        title = new SimpleStringProperty(start.getTitle());
        buttonText =  new SimpleStringProperty(start.getButtonText());
        balance = new SimpleBooleanProperty(start.getRequireBalance());
        content = new SimpleStringProperty(start.getContent());
        colorBackGround = new SimpleObjectProperty<>(start.getBackGroundColor());
        colorDisable = new SimpleObjectProperty<>(start.getDisableButtonColor());
        TextColor= new SimpleObjectProperty<>(start.getTextColor());
        StartOfStageDelay = new SimpleStringProperty(String.valueOf(start.getStartOfStageDelay()));
        EndOfStageDelay = new SimpleStringProperty(String.valueOf(start.getEndOfStageDelay()));


        experiment.addStart(start);

        buttonText.addListener((observableValue, oldValue, newValue) -> onButtonChange(newValue));
        balance.addListener((observableValue, oldValue, newValue) -> onBalanceChange(newValue));
        title.addListener((observableValue, oldValue, newValue) -> onTitleChange(newValue));
        content.addListener((observableValue, oldValue, newValue) -> onContentChange(newValue));
        colorBackGround.addListener((observableValue, oldValue, newValue) -> onColorBackground(newValue));
        TextColor.addListener((observableValue, oldValue, newValue) -> onColorText(newValue));
        StartOfStageDelay.addListener((observableValue, oldValue, newValue) -> onStart(newValue));
        EndOfStageDelay.addListener((observableValue, oldValue, newValue) -> onEnd(newValue));
        colorDisable.addListener((observableValue, oldValue, newValue) -> onColorDisable(newValue));
    }


    public StartVM(Start start){
        this.start = start;
        title = new SimpleStringProperty(start.getTitle());
        buttonText =  new SimpleStringProperty(start.getButtonText());
        balance = new SimpleBooleanProperty(start.getRequireBalance());
        content = new SimpleStringProperty(start.getContent());
        colorBackGround = new SimpleObjectProperty<>(start.getBackGroundColor());
        colorDisable = new SimpleObjectProperty<>(start.getDisableButtonColor());
        TextColor= new SimpleObjectProperty<>(start.getTextColor());
        StartOfStageDelay = new SimpleStringProperty(String.valueOf(start.getStartOfStageDelay()));
        EndOfStageDelay = new SimpleStringProperty(String.valueOf(start.getEndOfStageDelay()));
        buttonText.addListener((observableValue, oldValue, newValue) -> onButtonChange(newValue));
        balance.addListener((observableValue, oldValue, newValue) -> onBalanceChange(newValue));
        title.addListener((observableValue, oldValue, newValue) -> onTitleChange(newValue));
        content.addListener((observableValue, oldValue, newValue) -> onContentChange(newValue));
        colorBackGround.addListener((observableValue, oldValue, newValue) -> onColorBackground(newValue));
        TextColor.addListener((observableValue, oldValue, newValue) -> onColorText(newValue));
        StartOfStageDelay.addListener((observableValue, oldValue, newValue) -> onStart(newValue));
        EndOfStageDelay.addListener((observableValue, oldValue, newValue) -> onEnd(newValue));
        colorDisable.addListener((observableValue, oldValue, newValue) -> onColorDisable(newValue));
    }

    private void onButtonChange(String newValue) {
        start.setButtonText(newValue);
    }

    private void onBalanceChange(Boolean newValue) {
        start.setRequireBalance(newValue);
    }

    private void onColorDisable(Color newValue) {
        start.setDisableButtonColor(newValue);
    }

    private void onEnd(String newValue) {
        start.setEndOfStageDelay(Long.parseLong(newValue));
    }

    private void onStart(String newValue) {
        start.setStartOfStageDelay(Long.parseLong(newValue));
    }

    private void onColorText(Color newValue) {
        start.setTextColor(newValue);
    }

    private void onColorBackground(Color newValue) {
        start.setBackGroundColor( newValue);
    }

    private void onContentChange(String newValue) {
        start.setContent(newValue);
    }

    private void onTitleChange(String newValue) {
        start.setTitle(newValue);
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public String getContent() {
        return content.get();
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public Color getColorBackGround() {
        return colorBackGround.get();
    }

    public ObjectProperty<Color> colorBackGroundProperty() {
        return colorBackGround;
    }

    public Color getColorDisable() {
        return colorDisable.get();
    }

    public ObjectProperty<Color> colorDisableProperty() {
        return colorDisable;
    }

    public String getButtonText() {
        return buttonText.get();
    }

    public SimpleStringProperty buttonTextProperty() {
        return buttonText;
    }

    public Color getTextColor() {
        return TextColor.get();
    }

    public ObjectProperty<Color> textColorProperty() {
        return TextColor;
    }

    public String getStartOfStageDelay() {
        return StartOfStageDelay.get();
    }

    public SimpleStringProperty startOfStageDelayProperty() {
        return StartOfStageDelay;
    }

    public String getEndOfStageDelay() {
        return EndOfStageDelay.get();
    }

    public SimpleStringProperty endOfStageDelayProperty() {
        return EndOfStageDelay;
    }

    public boolean isBalance() {
        return balance.get();
    }

    public SimpleBooleanProperty balanceProperty() {
        return balance;
    }


    @Override
    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunStart.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);
        RunStartController controller = loader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("StartStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        StartStageController controller = fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleEditButtons(Button button1, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12) throws IOException {
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
        btn_next.textProperty().bind(this.buttonTextProperty());
        btn_back.setDisable(true);
    }

    @Override
    public String toString(){
        if (start == null){
            return "Start experiment";
        }
        else {
            return start.getTitle();
        }
    }

}
