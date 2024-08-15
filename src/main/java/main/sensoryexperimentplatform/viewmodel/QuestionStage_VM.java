package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.QuestionStageController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Question;

import java.io.IOException;
import java.util.Stack;

public class QuestionStage_VM implements Stages {
    //Notice can chinh lai
    private StringProperty question ;
    private StringProperty leftText ;
    private StringProperty rightText ;
    private StringProperty leftValue;
    private StringProperty rightValue;
    private StringProperty helpText;
    private BooleanProperty alert;
    private Question questionStage;
    private Experiment experiment;


    public QuestionStage_VM(Experiment experiment){
        this.experiment = experiment;
        this.questionStage = new Question("Question ",null,null,null,null,null,false);
        this.question = new SimpleStringProperty(questionStage.getQuestion());
        this.leftText = new SimpleStringProperty(questionStage.getLeftButtonText());
        this.rightText = new SimpleStringProperty(questionStage.getRightButtonText());
        this.leftValue = new SimpleStringProperty(questionStage.getLeftButtonValue());
        this.rightValue = new SimpleStringProperty(questionStage.getRightButtonValue());
        this.helpText = new SimpleStringProperty(questionStage.getHelpText());
        this.alert= new SimpleBooleanProperty(questionStage.isAlert());
        experiment.addQuestion(questionStage);
    }
    public  QuestionStage_VM(Question questionStage){
        this.questionStage = questionStage;
        this.question = new SimpleStringProperty(questionStage.getQuestion());
        this.leftText = new SimpleStringProperty(questionStage.getLeftButtonText());
        this.rightText = new SimpleStringProperty(questionStage.getRightButtonText());
        this.leftValue = new SimpleStringProperty(questionStage.getLeftButtonValue());
        this.rightValue = new SimpleStringProperty(questionStage.getRightButtonValue());
        this.helpText = new SimpleStringProperty(questionStage.getHelpText());
        this.alert= new SimpleBooleanProperty(questionStage.isAlert());

    }


    public String getLeftText(){
        return leftText.get();

    }
    public String getRightText(){
        return rightText.get();

    }
    public boolean getAlert(){
        return alert.get();

    }
    public String getRightValue(){
        return rightValue.get();

    }
    public String getLeftValue(){
        return leftValue.get();

    }
    public String getHelpText(){
        return helpText.get();

    }
    public StringProperty helpTextProperty() {
        return helpText;
    }
    public StringProperty leftTextProperty() {
        return leftText;
    }
    public StringProperty rightTextProperty() {
        return rightText;
    }
    public StringProperty leftValueProperty() {
        return leftValue;
    }
    public StringProperty rightValueProperty() {
        return rightValue;
    }
    public StringProperty questionProperty() {
        return question;
    }
    public BooleanProperty alertProperty() {
        return alert;
    }
    public void setQuestion(String newValue){
        questionStage.setQuestion(newValue);
    }
    public void setAlert(Boolean newValue) {
        questionStage.setAlert(newValue);
    }
    public void setHelpText(String newValue) {
        questionStage.setContent(newValue);
    }
    public void setLeftText(String newValue) {questionStage.setLeftButtonText(newValue);
    }
    public void setRightText(String newValue) {questionStage.setRightButtonText(newValue);
    }
    public void setLeftValue(String newValue) {questionStage.setLeftButtonValue(newValue);
    }
    public void setRightValue(String newValue) {questionStage.setRightButtonValue(newValue);
    }

    public Question getQuestionStage(){
        return questionStage;
    }

    @Override
    public void loadInterface(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("QuestionStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
//               Question question = new Question("NULL","NULL","NULL",false);
        QuestionStageController controller = fxmlLoader.getController();
        controller.setQuestionStage_vm(this);
    }

    @Override
    public void handleMenuButtons(AnchorPane propertiesPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button btn_AddPeriodicStage, Button btn_AddCourse, Button btn_assignSound,
                                  Button btn_addFoodAndTaste, Button btn_addAudibleInstruction
            , Button btn_addInput, Button btn_noticeStage,
                                  Button btn_addTimer, Button btn_AddQuestionStage,
                                  Button btn_addRatingContainer, Button btn_addTasteTest, Button btn_AddConditionalStatement, Stack<RatingContainer_VM> ratingContainerVm) throws IOException {

        btn_AddPeriodicStage.setDisable(true);
        btn_AddCourse.setDisable(false);
        btn_assignSound.setDisable(true);
        btn_addFoodAndTaste.setDisable(true);
        btn_addAudibleInstruction.setDisable(false);
        btn_addInput.setDisable(false);
        btn_noticeStage.setDisable(false);
        btn_addTimer.setDisable(false);
        btn_AddQuestionStage.setDisable(false);
        btn_addRatingContainer.setDisable(false);
        btn_addTasteTest.setDisable(false);
        btn_AddConditionalStatement.setDisable(false);
    }

    @Override
    public String toString() {
        return "[Question] "+ question.get();
    }

}












