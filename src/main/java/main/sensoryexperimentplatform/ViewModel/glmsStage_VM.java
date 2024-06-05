package main.sensoryexperimentplatform.ViewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.GLMSController;
import main.sensoryexperimentplatform.models.gLMS;

import java.io.IOException;

public class glmsStage_VM implements choose {
    private StringProperty txt_question;
    private StringProperty txt_LowAncTxt;
    private StringProperty txt_yes;
    private BooleanProperty checkB_swap;
    private StringProperty txt_help;
    private BooleanProperty checkB_sound;
    private gLMS glms;
    public glmsStage_VM(){
        this.glms = new gLMS("User Input",null,null,null, false);;
        txt_help = new SimpleStringProperty(glms.getHelpText());
        txt_LowAncTxt = new SimpleStringProperty(glms.getButtonText());
        txt_question = new SimpleStringProperty(glms.getTitle());
        checkB_sound = new SimpleBooleanProperty(glms.getAlert());
        checkB_swap  = new SimpleBooleanProperty(glms.isResponse());
<<<<<<< HEAD
        txt_yes = new SimpleStringProperty();
=======
        txt_yes= new SimpleStringProperty();
        txt_help.addListener((observableValue, oldValue, newValue) -> onHelp(newValue));
        txt_LowAncTxt.addListener((observableValue, oldValue, newValue) -> onLowAnc(newValue));
        checkB_swap.addListener((observableValue, oldValue, newValue) -> onSwapChange(newValue));
>>>>>>> 7a2212825dec188091988102bf0da9285f466266
        txt_question.addListener((observableValue, oldValue, newValue) -> onQuestionTextChange(newValue));
        checkB_sound.addListener((observableValue, oldValue, newValue) -> onSoundChange(newValue));
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

    public boolean isCheckB_sound() {
        return checkB_sound.get();
    }

    public BooleanProperty checkB_soundProperty() {
        return checkB_sound;
    }

    public gLMS getGLMS() {
        return glms;
    }

    @Override
    public void modify(AnchorPane anchorpane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("GLMS.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorpane.getChildren().setAll(newContent);
        GLMSController controller = fxmlLoader.getController();
        glmsStage_VM view = new glmsStage_VM();
        controller.setViewModel(view);
    }

    @Override
    public void modifyWithButton(AnchorPane anchorPane, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Button button13) throws IOException {

    }
}
