package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.addTasteController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.TasteTest;

import java.io.IOException;
import java.util.Stack;

public class AddTasteVM implements Stages {
    private StringProperty txt_inital;
    private StringProperty txt_help;
    private StringProperty txt_endInstruction;
    private StringProperty txt_timetowait;
    private  BooleanProperty checkbox_randomfood;
    private  BooleanProperty checkbox_randomrate;
    private  BooleanProperty checkbox_playalert;
    private StringProperty txt_howtaste;
    private StringProperty txt_consumpins;
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
        this.model= model;
        txt_buttontext = new SimpleStringProperty(model.returnsampleVas().getButtonText());
        txt_consumpins = new SimpleStringProperty(model.getConsumptionInstruction());
        txt_inital = new SimpleStringProperty(model.getConsumptionInstruction());
        txt_highacnhorvalue = new SimpleStringProperty(String.valueOf(model.returnsampleVas().getHighAnchorValue()));
        txt_highanchortext = new SimpleStringProperty(String.valueOf(model.returnsampleVas().getHighAnchorText()));
        txt_endInstruction = new SimpleStringProperty();
        txt_lowacnhorvalue = new SimpleStringProperty(String.valueOf(model.returnsampleVas().getLowAnchorValue()));
        txt_howtaste = new SimpleStringProperty(model.returnsampleVas().getTitle());
        txt_timetowait = new SimpleStringProperty(String.valueOf(model.getTimeWait()));
        txt_help = new SimpleStringProperty(model.getSampleGLMS().getHelpText());
        txt_lowanchortext = new SimpleStringProperty(model.returnsampleVas().getLowAnchorText());
        checkbox_playalert = new SimpleBooleanProperty(model.getSampleGLMS().getAlert());
        checkbox_randomfood = new SimpleBooleanProperty(model.isRandomizeFood());
        checkbox_swappole = new SimpleBooleanProperty(model.returnsampleVas().getIsSwap());
        checkbox_randomrate = new SimpleBooleanProperty(model.isRandomizeRatingGLMS());
        txt_consumpins.addListener((observableValue, oldValue, newValue) -> onCon(newValue));
        txt_buttontext.addListener((observableValue, oldValue, newValue) -> onButton(newValue));
        txt_howtaste.addListener((observableValue, oldValue, newValue) -> onHowTaste(newValue));
        txt_highacnhorvalue.addListener((observableValue, oldValue, newValue) -> onHighVal(newValue));
        txt_highanchortext.addListener((observableValue, oldValue, newValue) -> onHighText(newValue));
        txt_help.addListener((observableValue, oldValue, newValue) -> onHelp(newValue));
        txt_lowacnhorvalue.addListener((observableValue, oldValue, newValue) -> onLowVal(newValue));
        txt_lowanchortext.addListener((observableValue, oldValue, newValue) -> onLowText(newValue));
        checkbox_playalert.addListener((observableValue, oldValue, newValue) -> onPlayAlert(newValue));
        checkbox_randomrate.addListener((observableValue, oldValue, newValue) -> onRandomrate(newValue));
        checkbox_randomfood.addListener((observableValue, oldValue, newValue) -> onRandomFood(newValue));
        txt_timetowait.addListener((observableValue, oldValue, newValue) -> onTime(newValue));
        txt_inital.addListener((observableValue, oldValue, newValue) -> onInitial(newValue));
    }
    public AddTasteVM(Experiment experiment){
        this.experiment = experiment;
        model = new TasteTest(null,null,null,null,null,0,100,null, false, null,null, 1,false, false, false);
        txt_buttontext = new SimpleStringProperty(model.returnsampleVas().getButtonText());
        txt_consumpins = new SimpleStringProperty(model.getConsumptionInstruction());
        txt_inital = new SimpleStringProperty(model.getConsumptionInstruction());
        txt_highacnhorvalue = new SimpleStringProperty(String.valueOf(model.returnsampleVas().getHighAnchorValue()));
        txt_highanchortext = new SimpleStringProperty(String.valueOf(model.returnsampleVas().getHighAnchorText()));
        txt_endInstruction = new SimpleStringProperty();
        txt_lowacnhorvalue = new SimpleStringProperty(String.valueOf(model.returnsampleVas().getLowAnchorValue()));
        txt_howtaste = new SimpleStringProperty(model.returnsampleVas().getTitle());
        txt_timetowait = new SimpleStringProperty(String.valueOf(model.getTimeWait()));
        txt_help = new SimpleStringProperty(model.getSampleGLMS().getHelpText());
        txt_lowanchortext = new SimpleStringProperty(model.returnsampleVas().getLowAnchorText());
        checkbox_playalert = new SimpleBooleanProperty(model.getSampleGLMS().getAlert());
        checkbox_randomfood = new SimpleBooleanProperty(model.isRandomizeFood());
        checkbox_swappole = new SimpleBooleanProperty(model.returnsampleVas().getIsSwap());
        checkbox_randomrate = new SimpleBooleanProperty(model.isRandomizeRatingGLMS());
        txt_consumpins.addListener((observableValue, oldValue, newValue) -> onCon(newValue));
        txt_buttontext.addListener((observableValue, oldValue, newValue) -> onButton(newValue));
        txt_howtaste.addListener((observableValue, oldValue, newValue) -> onHowTaste(newValue));
        txt_highacnhorvalue.addListener((observableValue, oldValue, newValue) -> onHighVal(newValue));
        txt_highanchortext.addListener((observableValue, oldValue, newValue) -> onHighText(newValue));
        txt_help.addListener((observableValue, oldValue, newValue) -> onHelp(newValue));
        txt_lowacnhorvalue.addListener((observableValue, oldValue, newValue) -> onLowVal(newValue));
        txt_lowanchortext.addListener((observableValue, oldValue, newValue) -> onLowText(newValue));
        checkbox_playalert.addListener((observableValue, oldValue, newValue) -> onPlayAlert(newValue));
        checkbox_randomrate.addListener((observableValue, oldValue, newValue) -> onRandomrate(newValue));
        checkbox_randomfood.addListener((observableValue, oldValue, newValue) -> onRandomFood(newValue));
        txt_timetowait.addListener((observableValue, oldValue, newValue) -> onTime(newValue));
        txt_inital.addListener((observableValue, oldValue, newValue) -> onInitial(newValue));
    }


    private void onInitial(String newValue) {
        model.setEndInStruction(newValue);
    }

    private void onCon(String newValue) {
        model.setConsumptionInstruction(newValue);
    }

    private void onButton(String newValue) {
        model.returnsampleVas().setButtonText(newValue);
    }

    private void onTime(String newValue) {
        model.setTime(Integer.parseInt(newValue));
    }

    private void onRandomFood(Boolean newValue) {
        model.setRandomizeFood(newValue);
    }

    private void onRandomrate(Boolean newValue) {
        model.setRandomizeRatingGLMS(newValue);
        model.setRandomizeRatingGLMS(newValue);
    }

    private void onPlayAlert(Boolean newValue) {
        model.getSampleGLMS().setAlert(newValue);
        model.returnsampleVas().setAlert(newValue);
    }

    private void onLowText(String newValue) {
        model.returnsampleVas().setLowAnchorText(newValue);
    }

    private void onLowVal(String newValue) {
        model.returnsampleVas().setHighAnchorValue(Integer.parseInt(newValue));
    }

    private void onHelp(String newValue) {
        model.getSampleGLMS().setHelpText(newValue);
        model.returnsampleVas().setHelpText(newValue);
    }


    private void onHighText(String newValue) {
        model.returnsampleVas().setHighAnchorText(newValue);
    }

    private void onHighVal(String newValue) {
        model.returnsampleVas().setHighAnchorValue(Integer.parseInt(newValue));
    }

    private void onHowTaste(String newValue) {
        model.getSampleGLMS().setTitle(newValue);
        model.returnsampleVas().setTitle(newValue);
    }

    public String getTxt_inital() {
        return txt_inital.get();
    }

    public StringProperty txt_initalProperty() {
        return txt_inital;
    }

    public String getTxt_help() {
        return txt_help.get();
    }

    public StringProperty txt_helpProperty() {
        return txt_help;
    }

    public boolean isCheckbox_randomrate() {
        return checkbox_randomrate.get();
    }

    public BooleanProperty checkbox_randomrateProperty() {
        return checkbox_randomrate;
    }

    public String getTxt_howtaste() {
        return txt_howtaste.get();
    }

    public StringProperty txt_howtasteProperty() {
        return txt_howtaste;
    }

    public String getTxt_lowacnhorvalue() {
        return txt_lowacnhorvalue.get();
    }

    public StringProperty txt_lowacnhorvalueProperty() {
        return txt_lowacnhorvalue;
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

    public boolean isCheckbox_playalert() {
        return checkbox_playalert.get();
    }

    public BooleanProperty checkbox_playalertProperty() {
        return checkbox_playalert;
    }

    public String getTxt_consumpins() {
        return txt_consumpins.get();
    }

    public StringProperty txt_consumpinsProperty() {
        return txt_consumpins;
    }

    public boolean isCheckbox_swappole() {
        return checkbox_swappole.get();
    }

    public BooleanProperty checkbox_swappoleProperty() {
        return checkbox_swappole;
    }

    public boolean isCheckbox_randomfood() {
        return checkbox_randomfood.get();
    }

    public BooleanProperty checkbox_randomfoodProperty() {
        return checkbox_randomfood;
    }

    public String getTxt_highacnhorvalue() {
        return txt_highacnhorvalue.get();
    }

    public StringProperty txt_highacnhorvalueProperty() {
        return txt_highacnhorvalue;
    }

    public String getTxt_timetowait() {
        return txt_timetowait.get();
    }

    public StringProperty txt_timetowaitProperty() {
        return txt_timetowait;
    }

    public String getTxt_buttontext() {
        return txt_buttontext.get();
    }

    public StringProperty txt_buttontextProperty() {
        return txt_buttontext;
    }

    public String getTxt_endInstruction() {
        return txt_endInstruction.get();
    }

    public StringProperty txt_endInstructionProperty() {
        return txt_endInstruction;
    }

    @Override
    public void loadInterface(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS) throws IOException {
    }

    @Override
    public void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button button1, Button button2, Button button3, Button btn_addFoodAndTaste, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Stack<ratingContainer_VM> ratingContainerVm) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddTasteTest.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        addTasteController controller = fxmlLoader.getController();
        btn_addFoodAndTaste.setDisable(false);
        controller.setViewModel(this);
        stack.push(this);

    }

    @Override
    public String toString() {
        return "[Taste Test]";
    }

}
