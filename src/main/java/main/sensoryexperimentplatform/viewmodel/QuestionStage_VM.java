package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;

public class QuestionStage_VM implements ViewModel {
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


    public QuestionStage_VM(){
        this.questionStage = new Question("Question ",null,null,null,null,null,false);
        initListener();
      //  experiment.addQuestion(questionStage);
    }
    public  QuestionStage_VM(Question questionStage){
        this.questionStage = questionStage;
        initListener();
    }

    public  QuestionStage_VM(IfConditionalStatementVM ifConditionalStatementVM){
        this.questionStage = new Question("Question ",null,null,null,null,null,false);
        initListener();
        ifConditionalStatementVM.addIf(questionStage);

    }
    public  QuestionStage_VM(ElseConditionalStatementVM elseConditionalStatementVM){
        this.questionStage = new Question("Question ",null,null,null,null,null,false);
        initListener();
       elseConditionalStatementVM.addElse(questionStage);

    }

    private void initListener(){
        this.question = new SimpleStringProperty(questionStage.getQuestion());
        this.leftText = new SimpleStringProperty(questionStage.getLeftButtonText());
        this.rightText = new SimpleStringProperty(questionStage.getRightButtonText());
        this.leftValue = new SimpleStringProperty(questionStage.getLeftButtonValue());
        this.rightValue = new SimpleStringProperty(questionStage.getRightButtonValue());
        this.helpText = new SimpleStringProperty(questionStage.getHelpText());
        this.alert= new SimpleBooleanProperty(questionStage.isAlert());
    }

    public void addVariable(String variableName){
        questionStage.addVariable(variableName);
    }
    public ObservableList<String> getVariable(){
        return questionStage.getVariable();
    }

    @Override
    public Model getModel() {
        return questionStage;
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunQuestionStage.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);
        RunQuestionController controller = loader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("QuestionStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        QuestionStageController controller = fxmlLoader.getController();
        controller.setQuestionStage_vm(this);
    }

    @Override
    public void handleEditButtons(Button btn_AddPeriodicStage, Button btn_AddCourse, Button btn_assignSound,
                                  Button btn_addFoodAndTaste, Button btn_addAudibleInstruction
            , Button btn_addInput, Button btn_noticeStage,
                                  Button btn_addTimer, Button btn_AddQuestionStage,
                                  Button btn_addRatingContainer, Button btn_addTasteTest, Button btn_AddConditionalStatement) throws IOException {

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
    public void handleRunButtons(Button btn_next, Button btn_back, Tooltip tooltip, Tooltip nextButtonTooltip, ImageView help_image) {
        btn_back.setDisable(false);
        btn_next.setDisable(false);

        if (this.helpTextProperty().get() != null) {
            help_image.setVisible(true);
            tooltip.textProperty().bind(this.helpTextProperty());
        }
        if(this.helpTextProperty().get()  == null || this.helpTextProperty().get().equals("null")) {
            tooltip.setOpacity(0.0);
            help_image.setVisible(false);
            help_image.setManaged(false);
        }
    }

    @Override
    public String toString() {
        return "[Question] "+ question.get();
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

    public void playAlert(){
        questionStage.playSound();
    }
    public void setResult(String result) {
        questionStage.setResult(result);
    }
}












