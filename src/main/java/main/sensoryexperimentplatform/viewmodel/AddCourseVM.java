package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.AddCourseController;
import main.sensoryexperimentplatform.models.Course;
import main.sensoryexperimentplatform.models.Experiment;

import java.io.IOException;
import java.util.Stack;

public class AddCourseVM implements Stages {
    private StringProperty txt_button;
    private StringProperty txt_content;
    private StringProperty  txt_help;
    private StringProperty txt_quantity;
    private StringProperty  txt_refill;

    private StringProperty txt_title;
    private Experiment experiment;
    private Course course;

    public AddCourseVM( Experiment experiment){
        this.experiment = experiment;
        this.course = new Course("Start Eating Stage","User Input","User Input",0,0,0, "User Input", "User Input" );
        txt_button = new SimpleStringProperty(course.getButtonText());
        txt_help = new SimpleStringProperty(course.getHelpText());
        txt_quantity = new SimpleStringProperty(String.valueOf(course.getQuantity()));
        txt_refill = new SimpleStringProperty(String.valueOf(course.getRefillWeight()));
        txt_title = new SimpleStringProperty(course.getTitle());
        txt_content = new SimpleStringProperty(course.getContent());
        txt_button.addListener((observableValue, oldValue, newValue) -> onButtonTextChange(newValue));
        txt_help.addListener((observableValue, oldValue, newValue) -> onHelpTextChange(newValue));
        txt_quantity.addListener((observableValue, oldValue, newValue) -> onQuantityChange(newValue));
        txt_refill.addListener((observableValue, oldValue, newValue) -> onRefillTextChange(newValue));
        txt_title.addListener((observableValue, oldValue, newValue) -> onTitleTextChange(newValue));
        txt_content.addListener((observableValue, oldValue, newValue) -> onContentTextChange(newValue));
        //txt_help = new SimpleStringProperty(model.getHelp)
        experiment.addCourse(course);

    }
    public AddCourseVM(Course course){
        this.course = course;
        txt_button = new SimpleStringProperty(course.getButtonText());
        txt_help = new SimpleStringProperty(course.getHelpText());
        txt_quantity = new SimpleStringProperty(String.valueOf(course.getQuantity()));
        txt_refill = new SimpleStringProperty(String.valueOf(course.getRefillWeight()));
        txt_title = new SimpleStringProperty(course.getTitle());
        txt_content = new SimpleStringProperty(course.getContent());
        txt_button.addListener((observableValue, oldValue, newValue) -> onButtonTextChange(newValue));
        txt_help.addListener((observableValue, oldValue, newValue) -> onHelpTextChange(newValue));
        txt_quantity.addListener((observableValue, oldValue, newValue) -> onQuantityChange(newValue));
        txt_refill.addListener((observableValue, oldValue, newValue) -> onRefillTextChange(newValue));
        txt_title.addListener((observableValue, oldValue, newValue) -> onTitleTextChange(newValue));
        txt_content.addListener((observableValue, oldValue, newValue) -> onContentTextChange(newValue));
        //txt_help = new SimpleStringProperty(model.getHelp)
        if(experiment != null){
            experiment.addCourse(course);
        }


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

    public Experiment getExperiment() {
        return experiment;
    }

    public Course getCourse() {
        return course;
    }

    private void onTitleTextChange(String newValue) {
        course.setTitle(newValue);
    }

    private void onRefillTextChange(String newValue) {
        course.setRefillWeight(Integer.parseInt(newValue));
    }

    private void onQuantityChange(String newValue) {
        course.setQuantity(Integer.parseInt(newValue));
    }

    private void onHelpTextChange(String newValue) {
        course.setHelpText(newValue);
    }

    private void onButtonTextChange(String newValue) {
        course.setButtonText(newValue);
    }

    @Override
    public void loadInterface(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS) throws IOException {

    }

    @Override
    public void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button btn_AddPeriodicStage, Button btn_AddCourse, Button btn_assignSound,
                                  Button btn_addFoodAndTaste, Button btn_addAudibleInstruction
            , Button btn_addInput, Button btn_noticeStage,
                                  Button  btn_addTimer, Button btn_AddQuestionStage,
                                  Button btn_addRatingContainer, Button btn_addTasteTest, Button btn_AddConditionalStatement, Stack<RatingContainer_VM> ratingContainerVm) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddCourse.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        AddCourseController controller = fxmlLoader.getController();
        btn_AddPeriodicStage.setDisable(false);
        btn_AddCourse.setDisable(true);
        btn_noticeStage.setDisable(true);
        btn_addAudibleInstruction.setDisable(true);
        controller.setViewModel(this);
        addCourseVMS.push(this);
    }

    @Override
    public String toString() {
        return txt_title.get();
    }

}
