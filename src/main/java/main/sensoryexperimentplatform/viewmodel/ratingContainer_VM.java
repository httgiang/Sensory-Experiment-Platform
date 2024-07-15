package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.addRatingContainerController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.RatingContainer;
import main.sensoryexperimentplatform.models.Vas;
import main.sensoryexperimentplatform.models.gLMS;
import main.sensoryexperimentplatform.models.containerObject;

import java.io.IOException;
import java.util.Stack;

public class ratingContainer_VM implements choose{
    private RatingContainer ratingContainer;
    private IntegerProperty minTime;
    private BooleanProperty isRandomize;
    private Vas vas;
    private gLMS glms;
    private Experiment experiment;
    public ratingContainer_VM(Experiment experiment){
        this.experiment = experiment;
        this.ratingContainer = new RatingContainer(false,0);
        minTime = new SimpleIntegerProperty(ratingContainer.getMinTime());
        minTime.addListener((observableValue, oldValue, newValue) -> onMinTime(newValue));
        isRandomize = new SimpleBooleanProperty(ratingContainer.isRandomize());
        isRandomize.addListener((observableValue, oldValue, newValue) -> onRandom(newValue));
        experiment.addRatingContainerStage(ratingContainer);
    }

    private void onRandom(Boolean newValue) {
        ratingContainer.setRandomize(newValue);
    }

    private void onMinTime(Number newValue) {
        ratingContainer.setMinTime((Integer) newValue);
    }

    public IntegerProperty minTimeProperty(){
        return minTime;
    }
    public BooleanProperty randomProperty(){
        return isRandomize;
    }
    public int getMinTime(){
        return minTime.get();
    }
    public boolean getIsRandomize(){
        return isRandomize.get();

    }
    public void setMinTime(int newValue){
        ratingContainer.setMinTime(newValue);
    }
    public void setIsRandomize(boolean newValue){
        ratingContainer.setRandomize(newValue);
    }
    public void addContainerStage(containerObject object){
        ratingContainer.addStage(object);
    }
    public RatingContainer getRatingContainer(){
        return ratingContainer;
    }


    @Override
    public void modify(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS) throws IOException {

    }
    @Override
    public void modifyWithButton(AnchorPane propertiesPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button btn_AddPeriodicStage, Button btn_AddCourse, Button btn_assignSound,
                                 Button btn_addFoodAndTaste, Button btn_addAudibleInstruction
            , Button btn_addInput, Button btn_noticeStage,
                                 Button btn_addTimer, Button btn_AddQuestionStage,
                                 Button btn_addRatingContainer, Button btn_addTasteTest, Button btn_AddConditionalStatement, Stack<ratingContainer_VM> rating) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddRatingsContainer.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        propertiesPane.getChildren().setAll(newContent);
        btn_AddPeriodicStage.setDisable(true);
        btn_AddCourse.setDisable(true);
        btn_assignSound.setDisable(true);
        btn_addFoodAndTaste.setDisable(true);
        btn_addAudibleInstruction.setDisable(true);
        btn_addInput.setDisable(true);
        btn_addInput.setDisable(true);
        btn_noticeStage.setDisable(true);
        btn_addTimer.setDisable(true);
        btn_AddQuestionStage.setDisable(true);
        btn_addRatingContainer.setDisable(true);
        btn_addTasteTest.setDisable(true);
        btn_AddConditionalStatement.setDisable(true);
        rating.push(this);
        addRatingContainerController controller = fxmlLoader.getController();
        controller.setViewModel(this);

//               AddNoticeStage controller = fxmlLoader.getController();
    }

    @Override
    public String getTitle() {
        if (isRandomize.get()){
            return "Ratings container - Randomised";
        }
        return "Ratings container";
    }
}