package main.sensoryexperimentplatform.viewmodel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.arduino.TestArduino;
import main.sensoryexperimentplatform.controllers.AddCourseController;
import main.sensoryexperimentplatform.models.*;

import javax.swing.text.View;
import java.io.IOException;


public class AddCourseVM implements ViewModel{
    private StringProperty txt_button;
    private StringProperty txt_content;
    private StringProperty  txt_help;
    private StringProperty txt_quantity;
    private StringProperty  txt_refill;
    private StringProperty txt_duration;
    private StringProperty txt_endStatement;
    private StringProperty txt_title;
    private BooleanProperty checkbox_alert;
    private Scale scale = Scale.getScaleInstance();
    private DoubleProperty consumedWeight;
    private Experiment experiment;
    ModelVMRegistry registry = ModelVMRegistry.getInstance();
    private Course course;
    public AddCourseVM(){
        this.course = new Course("Start eating phase","Please contact experimenter",
                "Meal finished","Stop eating stage"
                , 0,0,0,
                "Please remember while eating:\\" +
                "Do not leave your fork in the bowl at any time: if you want to put your fork down, please use the small plate provided. " +
                "\\Please also do not lean on the placemat.\\" +
                "Click on Meal Finished ONLY when you are sure you have had enough food.", false);
        initListener();

    }
    public AddCourseVM(Course course){
        this.course = course;
        initListener();

    }
    public void initListener(){
        txt_title = new SimpleStringProperty(course.getTitle());
        txt_content = new SimpleStringProperty(course.getContent());
        txt_button = new SimpleStringProperty(course.getButtonText());
        txt_refill = new SimpleStringProperty(String.valueOf(course.getRefillWeight()));
        txt_quantity = new SimpleStringProperty(String.valueOf(course.getQuantity()));
        txt_duration = new SimpleStringProperty(String.valueOf(course.getDuration()));
        txt_help = new SimpleStringProperty(course.getHelpText());
        txt_endStatement = new SimpleStringProperty(course.getEndStatement());
        checkbox_alert = new SimpleBooleanProperty(course.isAlert());


        txt_button.addListener((observableValue, oldValue, newValue) -> onButtonTextChange(newValue));
        txt_help.addListener((observableValue, oldValue, newValue) -> onHelpTextChange(newValue));
        txt_quantity.addListener((observableValue, oldValue, newValue) -> onQuantityChange(Integer.parseInt(newValue)));
        txt_refill.addListener((observableValue, oldValue, newValue) -> onRefillTextChange(Integer.parseInt(newValue)));
        txt_title.addListener((observableValue, oldValue, newValue) -> onTitleTextChange(newValue));
        txt_content.addListener((observableValue, oldValue, newValue) -> onContentTextChange(newValue));
        txt_endStatement.addListener(((observableValue, oldValue, newValue) -> onEndTextChange(newValue)));
        txt_duration.addListener(((observableValue, oldValue, newValue) -> onDurationChange(newValue)));
        checkbox_alert.addListener(((observableValue, oldValue, newValue) -> onAlertChange(newValue)));
    }


    public double getConsumedWeight() {
        return consumedWeight.get();
    }

    public DoubleProperty consumedWeightProperty() {
        return consumedWeight;
    }

    private void onAlertChange(Boolean newValue) {
        course.setAlert(newValue);
    }

    private void onDurationChange(String newValue) {
        course.setDuration(Integer.parseInt(newValue));
    }

    private void onEndTextChange(String newValue) {
        course.setEndStatement(newValue);
    }
    public BooleanProperty isAlertProperty() {
        return checkbox_alert;
    }

    private void onContentTextChange(String newValue) {
        course.setContent(newValue);
    }

    public String getTxt_button() {
        return txt_button.get();
    }

    public StringProperty txt_buttonProperty() {
        return txt_button;
    }

    public String getTxt_content() {
        return txt_content.get();
    }

    public StringProperty txt_contentProperty() {
        return txt_content;
    }

    public String getTxt_help() {
        return txt_help.get();
    }

    public StringProperty txt_helpProperty() {
        return txt_help;
    }

    public String getTxt_quantity() {
        return txt_quantity.get();
    }

    public StringProperty txt_quantityProperty() {
        return txt_quantity;
    }

    public String getTxt_refill() {
        return txt_refill.get();
    }

    public StringProperty txt_refillProperty() {
        return txt_refill;
    }

    public String getTxt_title() {
        return txt_title.get();
    }

    public StringProperty txt_titleProperty() {
        return txt_title;
    }
    public StringProperty txt_endStatementProperty(){
        return txt_endStatement;
    }
    public StringProperty txt_durationProperty() {
        return txt_duration;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public Course getCourse() {
        return course;
    }

    private void onTitleTextChange(String newValue) {
        course.setTitle(newValue);
    }

    private void onRefillTextChange(int newValue) {
        course.setRefillWeight(newValue);
    }

    private void onQuantityChange(int newValue) {
        course.setQuantity(newValue);
    }

    private void onHelpTextChange(String newValue) {
        course.setHelpText(newValue);
    }

    private void onButtonTextChange(String newValue) {
        course.setButtonText(newValue);
    }

    public void setConsumedWeight(double consumedWeight) {
        this.consumedWeight.set(consumedWeight);
    }

    @Override
    public Model getModel() {
        return course;
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) {

    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddCourse.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        AddCourseController controller = fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleRunButtons(Button btn_next, Button btn_back) {
        btn_back.setDisable(false);
        btn_next.setDisable(false);
        btn_next.textProperty().bind(this.txt_buttonProperty());
    }

    @Override
    public void handleEditButtons(Button btn_addPeriodicStage, Button btn_addCourse, Button btn_assignSound,
                                  Button btn_addFoodAndTaste, Button btn_addAudibleInstruction
            , Button btn_addInput, Button btn_noticeStage,
                                  Button  btn_addTimer, Button btn_addQuestionStage,
                                  Button btn_addRatingContainer, Button btn_addTasteTest, Button btn_addConditionalStatement) throws IOException {

        btn_addPeriodicStage.setDisable(false);
        btn_assignSound.setDisable(true);
        btn_addFoodAndTaste.setDisable(true);
        btn_addAudibleInstruction.setDisable(false);
        btn_addInput.setDisable(false);
        btn_noticeStage.setDisable(false);
        btn_addTimer.setDisable(false);
        btn_addQuestionStage.setDisable(false);
        btn_addRatingContainer.setDisable(false);
        btn_addTasteTest.setDisable(false);
        btn_addConditionalStatement.setDisable(false);
        btn_addCourse.setDisable(true);
    }

    public void initRunSetup(ListView<ViewModel> listView){
        //Add vao notice stage de nguoi dung thi nghiem bat dau nhan do an
        showNoticeStage("Start", listView);

        // Bat dau can
        TestArduino.startRecording();


        // Bind bien consumed weight voi cai can. Consumed = weight ban dau - weight hien tai.
        // Bay gio cai nay dang sai, se sua lai sau khi cai can nhan dung gia tri
        this.consumedWeight = new SimpleDoubleProperty(0.0);

        this.consumedWeight.bindBidirectional(Scale.getScaleInstance().weightProperty());

        // Cai dat listener cho cai can
        this.consumedWeight.addListener((observable, oldValue, newValue) -> {

            if (newValue.doubleValue() >= 0.1) {
                // Neu nhu nguoi tham gia an dc hon 400g -> nhac nho Refill
                showNoticeStage("Refill", listView);
                this.setConsumedWeight(0.0);
            }
        });

        showChildrenPane(listView);

    }
    public void showChildrenPane(ListView<ViewModel> listView){
        for(Model children : course.getChildren()){
            listView.getItems().add(registry.getViewModel(children));
        }
        showNoticeStage("End", listView);
        TestArduino.stopRecording();
    }

    private void showNoticeStage(String type, ListView<ViewModel> listView){
        Notice notice = null;
        if(type.equals("Start")){
            notice = new Notice(getTxt_title(), getTxt_content(), "Food added", null, false);
        } else if(type.equals("Refill")){
            notice = new Notice("Refill required", "Please call the experimenter", "Food added", null, false);
            System.out.println("REFILL");
        } else if(type.equals("End")){
            notice = new Notice("Stop eating", txt_endStatement.get(), getTxt_button(), null, false);
        }
        NoticeStage_VM noticeStageVm = new NoticeStage_VM(notice);

        listView.getItems().add(noticeStageVm);

    }


    @Override
    public String toString() {
        return txt_title.get();
    }



}
