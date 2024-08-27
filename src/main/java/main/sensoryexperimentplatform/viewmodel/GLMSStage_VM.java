package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;

public class GLMSStage_VM implements ViewModel{
    private StringProperty txt_question, txt_LowAncTxt, txt_yes, txt_help;
    private StringProperty buttonText;
    private BooleanProperty checkB_swap;
    private BooleanProperty checkB_sound;
    private DoubleProperty sliderValue;
    private StringProperty conducted;
    private Experiment experiment;
    private gLMS glms;
    public GLMSStage_VM(){
        this.glms = new gLMS("User Input",null,null,null, false);;
        initListener();
       // experiment.addGlmsStage(glms);
    }
    public GLMSStage_VM(gLMS glms){
        this.glms = glms;
        initListener();
    }


    public GLMSStage_VM (IfConditionalStatementVM ifConditionalStatementVM){
        this.glms = new gLMS("User Input",null,null,null, false);
        initListener();
       ifConditionalStatementVM.addIf(glms);
    }

    public GLMSStage_VM (ElseConditionalStatementVM elseConditionalStatementVM){
        this.glms = new gLMS("User Input",null,null,null, false);
        initListener();
        elseConditionalStatementVM.addElse(glms);
    }

    private void initListener(){
        txt_help = new SimpleStringProperty(glms.getHelpText());
        txt_LowAncTxt = new SimpleStringProperty(glms.getButtonText());
        txt_question = new SimpleStringProperty(glms.getTitle());
        checkB_sound = new SimpleBooleanProperty(glms.getAlert());
        checkB_swap  = new SimpleBooleanProperty(glms.isResponse());
        buttonText = new SimpleStringProperty(glms.getButtonText());

        txt_help.addListener((observableValue, oldValue, newValue) -> onHelp(newValue));
        txt_LowAncTxt.addListener((observableValue, oldValue, newValue) -> onLowAnc(newValue));
        checkB_swap.addListener((observableValue, oldValue, newValue) -> onSwapChange(newValue));
        txt_question.addListener((observableValue, oldValue, newValue) -> onQuestionTextChange(newValue));
        checkB_sound.addListener((observableValue, oldValue, newValue) -> onSoundChange(newValue));
        buttonText.addListener((observableValue, oldValue, newValue) -> onButtonChange(newValue));

        sliderValue = new SimpleDoubleProperty(glms.getResult());
        conducted = new SimpleStringProperty(glms.getConducted());
        sliderValue.addListener(((observableValue, oldValue, newValue) ->{
            setResult(newValue.intValue());
            conducted.set(DataAccess.getCurrentFormattedTime());
            setDate();
        } ));
    }

    @Override
    public Model getModel() {
        return glms;
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunGLMS.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().addAll(newContent);
        RunGLMSController controller = loader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("GLMS.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        GLMSController controller = fxmlLoader.getController();
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
        btn_next.textProperty().bind(this.buttonTextProperty());

        if (this.conductedTextProperty().get() == null){
            btn_next.setDisable(true);
        }else btn_next.setDisable(false);

        this.conductedTextProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btn_next.setDisable(false);
            }
        });
    }

    @Override
    public String toString(){
        return "[GLMS] "+ txt_question.get();
    }

    public void onButtonChange(String newValue){
        glms.setButtonText(newValue);
    }

    public void setDate(){
        glms.setConducted(DataAccess.getCurrentFormattedTime());
    }

    public void setResult(int result){
        glms.setResult(result);
    }
    private void onHelp(String newValue) {
        glms.setHelpText(newValue);
    }

    private void onLowAnc(String newValue) {
        glms.setButtonText(newValue);
    }


    private void onSwapChange(Boolean newValue) {
        glms.setResponse(newValue);
    }

    private void onSoundChange(Boolean newValue) {
        glms.setAlert(newValue);
    }

    private void onQuestionTextChange(String s) {
        glms.setTitle(s);
    }

    public String getTxt_question() {
        return txt_question.get();
    }

    public StringProperty txt_questionProperty() {
        return txt_question;
    }

    public String getTxt_LowAncTxt() {
        return txt_LowAncTxt.get();
    }

    public StringProperty txt_LowAncTxtProperty() {
        return txt_LowAncTxt;
    }

    public String getTxt_yes() {
        return txt_yes.get();
    }

    public StringProperty txt_yesProperty() {
        return txt_yes;
    }

    public boolean isCheckB_swap() {
        return checkB_swap.get();
    }

    public BooleanProperty checkB_swapProperty() {
        return checkB_swap;
    }

    public String getTxt_help() {
        return txt_help.get();
    }

    public StringProperty txt_helpProperty() {
        return txt_help;
    }
    public BooleanProperty alertProperty() {
        return checkB_sound;
    }
    public boolean isCheckB_sound() {
        return checkB_sound.get();
    }

    public BooleanProperty checkB_soundProperty() {
        return checkB_sound;
    }

    public gLMS getGLMS() {
        return glms;
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

    public void playAlertSound(){
        glms.playAlertSound();
    }
    public DoubleProperty sliderValueProperty() {
        return sliderValue;
    }
    public StringProperty conductedTextProperty() {
        return conducted;
    }


}