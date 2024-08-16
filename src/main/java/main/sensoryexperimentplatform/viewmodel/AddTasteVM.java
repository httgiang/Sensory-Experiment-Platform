package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;
import java.util.Stack;

public class AddTasteVM implements Stages {
    private StringProperty txt_initialNotice;
    private StringProperty txt_consumptionInst;
    private StringProperty txt_question;
    private StringProperty txt_help;
    private StringProperty txt_endInstruction;
    private StringProperty txt_timetowait;
    private  BooleanProperty checkbox_randomfood;
    private  BooleanProperty checkbox_randomrate;
    private  BooleanProperty checkbox_playalert;

    private StringProperty txt_lowanchortext;
    private StringProperty txt_highanchortext;
    private StringProperty txt_lowacnhorvalue;
    private StringProperty txt_highacnhorvalue;
    private StringProperty txt_buttontext;
    private  BooleanProperty checkbox_swappole;
    private TasteTest model;
    private Experiment experiment;
    public TasteTest getTastetest(){
        return model;
    }
    public AddTasteVM(TasteTest model){
        this.model = model;
        initBinding(model);
        initListener();
//        for(Object o : model.getStages()){
//            System.out.println("taste test con: " + o);
//        }
    }
    public AddTasteVM(Experiment experiment){
        this.experiment = experiment;
        model = new TasteTest("Please call the experimenter for the samples","How <taste> is <food>?", "Consumption instruction",
                "End instruction", "Low Anchor Text","High Anchor Text", "Continue", "",
                0,100, false, false, false, 0, false);
        initBinding(model);
        initListener();
        experiment.addNewTasteTest(model);
    }


    private void initBinding(TasteTest model){
        txt_initialNotice = new SimpleStringProperty(model.getNoticeStageContent());
        txt_consumptionInst = new SimpleStringProperty(model.getConsumptionInstruction());
        txt_question = new SimpleStringProperty(model.getQuestion());

        txt_lowanchortext = new SimpleStringProperty(model.getSampleVas().getLowAnchorText());
        txt_highanchortext = new SimpleStringProperty(String.valueOf(model.getSampleVas().getHighAnchorText()));
        txt_lowacnhorvalue = new SimpleStringProperty(String.valueOf(model.getSampleVas().getLowAnchorValue()));
        txt_highacnhorvalue = new SimpleStringProperty(String.valueOf(model.getSampleVas().getHighAnchorValue()));

        txt_buttontext = new SimpleStringProperty(model.getSampleVas().getButtonText());
        checkbox_swappole = new SimpleBooleanProperty(model.getSampleVas().getIsSwap());
        txt_help = new SimpleStringProperty(model.getSampleGLMS().getHelpText());
        txt_endInstruction = new SimpleStringProperty(model.getEndInstruction());
        txt_timetowait = new SimpleStringProperty(String.valueOf(model.getTimeWait()));

        checkbox_playalert = new SimpleBooleanProperty(model.getSampleGLMS().getAlert());
        checkbox_randomfood = new SimpleBooleanProperty(model.isRandomizedFood());
        checkbox_randomrate = new SimpleBooleanProperty(model.isRandomizeRatings());
    }
    private void initListener(){
        txt_initialNotice.addListener((observableValue, oldValue, newValue) -> onInitialNotice(newValue));
        txt_consumptionInst.addListener((observableValue, oldValue, newValue) -> onConsumptionInst(newValue));
        txt_question.addListener((observableValue, oldValue, newValue) -> onQuestion(newValue));

        txt_lowanchortext.addListener((observableValue, oldValue, newValue) -> onLowText(newValue));
        txt_highanchortext.addListener((observableValue, oldValue, newValue) -> onHighText(newValue));
        txt_highacnhorvalue.addListener((observableValue, oldValue, newValue) -> onHighVal(newValue));
        txt_lowacnhorvalue.addListener((observableValue, oldValue, newValue) -> onLowVal(newValue));

        txt_buttontext.addListener((observableValue, oldValue, newValue) -> onButtonText(newValue));
        checkbox_swappole.addListener((observableValue, oldValue, newValue) -> onSwap(newValue));
        txt_help.addListener((observableValue, oldValue, newValue) -> onHelp(newValue));
        txt_endInstruction.addListener((observableValue, oldValue, newValue) -> onEndInst(newValue));
        txt_timetowait.addListener((observableValue, oldValue, newValue) -> onTime(newValue));


        checkbox_playalert.addListener((observableValue, oldValue, newValue) -> onPlayAlert(newValue));
        checkbox_randomrate.addListener((observableValue, oldValue, newValue) -> onRandomRating(newValue));
        checkbox_randomfood.addListener((observableValue, oldValue, newValue) -> onRandomFood(newValue));

    }
    private void onInitialNotice(String newValue) {
        model.setNoticeStageContent(newValue);
    }

    private void onConsumptionInst(String newValue) {
        model.setConsumptionInstruction(newValue);
    }
    private void onQuestion(String newValue){
        model.setQuestion(newValue);
    }

    private void onButtonText(String newValue) {
        model.getSampleVas().setButtonText(newValue);
    }
    private void onSwap(Boolean newValue){
        model.setSwap(newValue);
    }

    private void onEndInst(String newValue){
        model.setEndInstruction(newValue);

    }
    private void onTime(String newValue) {
        model.setTime(Integer.parseInt(newValue));
    }

    private void onRandomFood(Boolean newValue) {
        model.setRandomizedFood(newValue);
    }

    private void onRandomRating(Boolean newValue) {
        model.setRandomizeRatings(newValue);
    }

    private void onPlayAlert(Boolean newValue) {
        model.setAlert(newValue);
    }

    private void onLowText(String newValue) {
        model.getSampleVas().setLowAnchorText(newValue);
    }

    private void onLowVal(String newValue) {
        model.getSampleVas().setHighAnchorValue(Integer.parseInt(newValue));
    }

    private void onHelp(String newValue) {
        model.getSampleGLMS().setHelpText(newValue);
        model.getSampleVas().setHelpText(newValue);
    }


    private void onHighText(String newValue) {
        model.getSampleVas().setHighAnchorText(newValue);
    }

    private void onHighVal(String newValue) {
        model.getSampleVas().setHighAnchorValue(Integer.parseInt(newValue));
    }

    public String getTxt_initialNotice() {
        return txt_initialNotice.get();
    }

    public StringProperty txt_initialNoticeProperty() {
        return txt_initialNotice;
    }

    public String getTxt_consumptionInst() {
        return txt_consumptionInst.get();
    }

    public StringProperty txt_consumptionInstProperty() {
        return txt_consumptionInst;
    }

    public String getTxt_question() {
        return txt_question.get();
    }

    public StringProperty txt_questionProperty() {
        return txt_question;
    }



    public String getTxt_help() {
        return txt_help.get();
    }

    public StringProperty txt_helpProperty() {
        return txt_help;
    }

    public String getTxt_endInstruction() {
        return txt_endInstruction.get();
    }

    public StringProperty txt_endInstructionProperty() {
        return txt_endInstruction;
    }

    public String getTxt_timetowait() {
        return txt_timetowait.get();
    }

    public StringProperty txt_timetowaitProperty() {
        return txt_timetowait;
    }

    public boolean isCheckbox_randomfood() {
        return checkbox_randomfood.get();
    }

    public BooleanProperty checkbox_randomfoodProperty() {
        return checkbox_randomfood;
    }

    public boolean isCheckbox_randomrate() {
        return checkbox_randomrate.get();
    }

    public BooleanProperty checkbox_randomrateProperty() {
        return checkbox_randomrate;
    }

    public boolean isCheckbox_playalert() {
        return checkbox_playalert.get();
    }

    public BooleanProperty checkbox_playalertProperty() {
        return checkbox_playalert;
    }



    public String getTxt_lowanchortext() {
        return txt_lowanchortext.get();
    }

    public StringProperty txt_lowanchortextProperty() {
        return txt_lowanchortext;
    }

    public String getTxt_highanchortext() {
        return txt_highanchortext.get();
    }

    public StringProperty txt_highanchortextProperty() {
        return txt_highanchortext;
    }

    public String getTxt_lowacnhorvalue() {
        return txt_lowacnhorvalue.get();
    }

    public StringProperty txt_lowacnhorvalueProperty() {
        return txt_lowacnhorvalue;
    }

    public String getTxt_highacnhorvalue() {
        return txt_highacnhorvalue.get();
    }

    public StringProperty txt_highacnhorvalueProperty() {
        return txt_highacnhorvalue;
    }

    public String getTxt_buttontext() {
        return txt_buttontext.get();
    }

    public StringProperty txt_buttontextProperty() {
        return txt_buttontext;
    }

    public boolean isCheckbox_swappole() {
        return checkbox_swappole.get();
    }

    public BooleanProperty checkbox_swappoleProperty() {
        return checkbox_swappole;
    }

    public TasteTest getModel() {
        return model;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddTasteTest.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        AddTasteController controller = fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleMenuButtons(Button button1, Button button2,
                                  Button btn_assignSound,
                                  Button btn_addFoodAndTaste,
                                  Button button5, Button button6,
                                  Button button7, Button button8,
                                  Button button9, Button button10,
                                  Button button11, Button button12)
            throws IOException {
        btn_addFoodAndTaste.setDisable(false);
        btn_assignSound.setDisable(true);
    }

    @Override
    public String toString() {
        return "Taste test";
    }

}
