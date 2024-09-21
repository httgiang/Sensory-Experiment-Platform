package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;

import main.sensoryexperimentplatform.models.*;

import java.io.IOException;

public class RatingContainer_VM implements ViewModel {
    private RatingContainer ratingContainer;
    private IntegerProperty minTime;
    private BooleanProperty isRandomize;

    public RatingContainer_VM(){
        this.ratingContainer = new RatingContainer(false,0);
        initListener();
      //  experiment.addRatingContainerStage(ratingContainer);
    }
    public RatingContainer_VM(RatingContainer rc){
        this.ratingContainer = rc;
        initListener();
    }
    private void initListener(){
        minTime = new SimpleIntegerProperty(ratingContainer.getMinTime());
        minTime.addListener((observableValue, oldValue, newValue) -> onMinTime(newValue));
        isRandomize = new SimpleBooleanProperty(ratingContainer.isRandomize());
        isRandomize.addListener((observableValue, oldValue, newValue) -> onRandom(newValue));
    }
    public RatingContainer_VM(IfConditionalStatementVM ifConditionalStatementVM){
        this.ratingContainer = new RatingContainer(false,0);
        minTime = new SimpleIntegerProperty(ratingContainer.getMinTime());
        minTime.addListener((observableValue, oldValue, newValue) -> onMinTime(newValue));
        isRandomize = new SimpleBooleanProperty(ratingContainer.isRandomize());
        isRandomize.addListener((observableValue, oldValue, newValue) -> onRandom(newValue));
        ifConditionalStatementVM.addIf(ratingContainer);
    }
    public RatingContainer_VM(ElseConditionalStatementVM elseConditionalStatementVM){
        this.ratingContainer = new RatingContainer(false,0);
        minTime = new SimpleIntegerProperty(ratingContainer.getMinTime());
        minTime.addListener((observableValue, oldValue, newValue) -> onMinTime(newValue));
        isRandomize = new SimpleBooleanProperty(ratingContainer.isRandomize());
        isRandomize.addListener((observableValue, oldValue, newValue) -> onRandom(newValue));
        elseConditionalStatementVM.addElse(ratingContainer);
    }
    @Override
    public Model getModel() {
        return ratingContainer;
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
        //KO CO, VI RATING KH LOAD LEN MAN HINH RUN RIENG
    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddRatingsContainer.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        addRatingContainerController controller = fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleEditButtons(Button btn_AddPeriodicStage, Button btn_AddCourse, Button btn_assignSound,
                                  Button btn_addFoodAndTaste, Button btn_addAudibleInstruction
            , Button btn_addInput, Button btn_noticeStage,
                                  Button btn_addTimer, Button btn_AddQuestionStage,
                                  Button btn_addRatingContainer, Button btn_addTasteTest, Button btn_AddConditionalStatement) throws IOException {;
        btn_AddPeriodicStage.setDisable(true);
        btn_AddCourse.setDisable(true);
        btn_assignSound.setDisable(true);
        btn_addFoodAndTaste.setDisable(true);
        btn_addAudibleInstruction.setDisable(true);
        btn_addInput.setDisable(true);
        btn_noticeStage.setDisable(true);
        btn_addTimer.setDisable(true);
        btn_AddQuestionStage.setDisable(true);
        btn_addRatingContainer.setDisable(true);
        btn_addTasteTest.setDisable(true);
        btn_AddConditionalStatement.setDisable(true);

    }

    @Override
    public void handleRunButtons(Button btn_next, Button btn_back, Tooltip tooltip, Tooltip nextButtonTooltip, ImageView help_image) {
        //KO CO
    }


    @Override
    public String toString() {
        if (isRandomize.get()) {
            return "Ratings container - Randomised";
        }
        return "Ratings container";
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
    public RatingContainer getRatingContainer(){
        return ratingContainer;
    }




}