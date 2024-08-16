package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.VasController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Vas;

import java.io.IOException;
import java.util.Stack;

public class VasStage_VM implements Stages {
    private Experiment experiment;
    private StringProperty questionText;
    private StringProperty lowAnchorText;
    private StringProperty highAnchorText;
    private StringProperty helpText;
    private StringProperty buttonText;
    private StringProperty lowAnchorValue;
    private StringProperty highAnchorValue;
    private StringProperty txt_BtnTxt;
    private StringProperty txt_yes;
    private BooleanProperty checkB_sound;
    private BooleanProperty checkB_swap;
    private final BooleanProperty alert = new SimpleBooleanProperty(true);
    private Vas vas;
    public VasStage_VM(Vas vas){
        this.vas= vas;
        lowAnchorText = new SimpleStringProperty(vas.getLowAnchorText());
        highAnchorText = new SimpleStringProperty(vas.getHighAnchorText());
        buttonText = new SimpleStringProperty(vas.getButtonText());
        helpText = new SimpleStringProperty(vas.getHelpText());
        lowAnchorValue = new SimpleStringProperty(String.valueOf(vas.getLowAnchorValue()));
        highAnchorValue = new SimpleStringProperty(String.valueOf(vas.getHighAnchorValue()));
        questionText = new SimpleStringProperty(vas.getTitle());
        checkB_sound = new SimpleBooleanProperty(vas.getAlert());
        checkB_swap = new SimpleBooleanProperty(vas.getIsSwap());
        checkB_swap.addListener((observableValue, oldValue, newValue) -> onCheckSwap(newValue));
        checkB_sound.addListener((observableValue, oldValue, newValue) -> onCheckSound(newValue));
        highAnchorValue.addListener((observableValue, oldValue, newValue) -> onhighAnchorValue(newValue));
        lowAnchorValue.addListener((observableValue, oldValue, newValue) -> onlowAnchorValue(newValue));
        helpText.addListener((observableValue, oldValue, newValue) -> onHelpText(newValue));
        buttonText.addListener((observableValue, oldValue, newValue) -> {
            onButtonText(newValue);
//            System.out.println(vas);
        });

        highAnchorText.addListener((observableValue, oldValue, newValue) -> onhighAnchorText(newValue));
        lowAnchorText.addListener((observableValue, oldValue, newValue) -> onlowAnchorText(newValue));
        questionText.addListener((observableValue, oldValue, newValue) -> onQuestionTextChange(newValue));
    }

    public VasStage_VM(Experiment experiment) {
        this.experiment = experiment;
        this.vas = new Vas("User input", null, null,
                0, 100, null, null, null, false, false);
        lowAnchorText = new SimpleStringProperty(vas.getLowAnchorText());
        highAnchorText = new SimpleStringProperty(vas.getHighAnchorText());
        buttonText = new SimpleStringProperty(vas.getButtonText());
        helpText = new SimpleStringProperty(vas.getHelpText());
        lowAnchorValue = new SimpleStringProperty(String.valueOf(vas.getLowAnchorValue()));
        highAnchorValue = new SimpleStringProperty(String.valueOf(vas.getHighAnchorValue()));
        questionText = new SimpleStringProperty(vas.getTitle());
        checkB_sound = new SimpleBooleanProperty(vas.getAlert());
        checkB_swap = new SimpleBooleanProperty(vas.getIsSwap());
        checkB_swap.addListener((observableValue, oldValue, newValue) -> onCheckSwap(newValue));
        checkB_sound.addListener((observableValue, oldValue, newValue) -> onCheckSound(newValue));
        highAnchorValue.addListener((observableValue, oldValue, newValue) -> onhighAnchorValue(newValue));
        lowAnchorValue.addListener((observableValue, oldValue, newValue) -> onlowAnchorValue(newValue));
        helpText.addListener((observableValue, oldValue, newValue) -> onHelpText(newValue));
        buttonText.addListener((observableValue, oldValue, newValue) -> {
            onButtonText(newValue);
        });

        highAnchorText.addListener((observableValue, oldValue, newValue) -> onhighAnchorText(newValue));
        lowAnchorText.addListener((observableValue, oldValue, newValue) -> onlowAnchorText(newValue));
        questionText.addListener((observableValue, oldValue, newValue) -> onQuestionTextChange(newValue));
        experiment.addVasStage(vas);
    }

    public VasStage_VM(RatingContainer_VM rating) {
        this.vas = new Vas("User input", null, null,
                0, 100, null, null, null, false, false);
        rating.addContainerStage(vas);
        lowAnchorText = new SimpleStringProperty(vas.getLowAnchorText());
        highAnchorText = new SimpleStringProperty(vas.getHighAnchorText());
        buttonText = new SimpleStringProperty(vas.getButtonText());
        helpText = new SimpleStringProperty(vas.getHelpText());
        lowAnchorValue = new SimpleStringProperty(String.valueOf(vas.getLowAnchorValue()));
        highAnchorValue = new SimpleStringProperty(String.valueOf(vas.getHighAnchorValue()));
        questionText = new SimpleStringProperty(vas.getTitle());
        checkB_sound = new SimpleBooleanProperty(vas.getAlert());
        checkB_swap = new SimpleBooleanProperty(vas.getIsSwap());
        checkB_swap.addListener((observableValue, oldValue, newValue) -> onCheckSwap(newValue));
        checkB_sound.addListener((observableValue, oldValue, newValue) -> onCheckSound(newValue));
        highAnchorValue.addListener((observableValue, oldValue, newValue) -> onhighAnchorValue(newValue));
        lowAnchorValue.addListener((observableValue, oldValue, newValue) -> onlowAnchorValue(newValue));
        helpText.addListener((observableValue, oldValue, newValue) -> onHelpText(newValue));
        buttonText.addListener((observableValue, oldValue, newValue) -> {
            onButtonText(newValue);
        });

        highAnchorText.addListener((observableValue, oldValue, newValue) -> onhighAnchorText(newValue));
        lowAnchorText.addListener((observableValue, oldValue, newValue) -> onlowAnchorText(newValue));
        questionText.addListener((observableValue, oldValue, newValue) -> onQuestionTextChange(newValue));
    }

    private void onCheckSwap(Boolean newValue) {
        vas.setSwap(newValue);
    }

    private void onCheckSound(Boolean newValue) {
        vas.setAlert(newValue);
    }

    private void onhighAnchorValue(String newValue) {
        vas.setHighAnchorValue(Integer.parseInt(newValue));
    }

    private void onlowAnchorValue(String newValue) {
        vas.setLowAnchorValue(Integer.parseInt(newValue));
    }

    private void onHelpText(String newValue) {
        vas.setHelpText(newValue);
    }

    private void onButtonText(String newValue) {
        vas.setButtonText(newValue);
    }

    private void onhighAnchorText(String newValue) {
        vas.setHighAnchorText(newValue);
    }

    private void onlowAnchorText(String newValue) {
        vas.setLowAnchorText(newValue);
    }

    public boolean isCheckB_swap() {
        return checkB_swap.get();
    }

    public BooleanProperty checkB_swapProperty() {
        return checkB_swap;
    }

    public boolean isCheckB_sound() {
        return checkB_sound.get();
    }

    public BooleanProperty checkB_soundProperty() {
        return checkB_sound;
    }

    public String getHighAnchorValue() {
        return highAnchorValue.get();
    }

    public StringProperty highAnchorValueProperty() {
        return highAnchorValue;
    }

    public String getLowAnchorValue() {
        return lowAnchorValue.get();
    }

    public StringProperty lowAnchorValueProperty() {
        return lowAnchorValue;
    }

    public String getTxt_yes() {
        return txt_yes.get();
    }

    public StringProperty txt_yesProperty() {
        return txt_yes;
    }

    public String getTxt_BtnTxt() {
        return txt_BtnTxt.get();
    }

    public StringProperty txt_BtnTxtProperty() {
        return txt_BtnTxt;
    }

    private void onQuestionTextChange(String s) {
        vas.setTitle(s);
    }

    public String getQuestionText() {
        return questionText.get();
    }

    public StringProperty questionTextProperty() {
        return questionText;

    }

    public void setQuestionText(String questionText) {
        this.questionText.set(questionText);
    }

    public String getLowAnchorText() {
        return lowAnchorText.get();
    }

    public StringProperty lowAnchorTextProperty() {
        return lowAnchorText;
    }

    public void setLowAnchorText(String lowAnchorText) {
        this.lowAnchorText.set(lowAnchorText);
    }

    public String getHighAnchorText() {
        return highAnchorText.get();
    }

    public StringProperty highAnchorTextProperty() {
        return highAnchorText;
    }

    public void setHighAnchorText(String highAnchorText) {
        this.highAnchorText.set(highAnchorText);
    }

    public String getHelpText() {
        return helpText.get();
    }

    public StringProperty helpTextProperty() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText.set(helpText);
    }

    public String getButtonText() {
        return buttonText.get();
    }

    public StringProperty buttonTextProperty() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText.set(buttonText);
    }


    public boolean isAlert() {
        return alert.get();
    }

    public BooleanProperty alertProperty() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert.set(alert);
    }

    public Vas getVas() {
        return vas;
    }

    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("VasStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        VasController controller = fxmlLoader.getController();
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
        return "[VAS] " + getQuestionText();
    }
}