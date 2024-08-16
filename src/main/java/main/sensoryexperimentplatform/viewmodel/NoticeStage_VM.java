package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.NoticeStageController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Notice;

import java.io.IOException;
import java.util.Stack;

public class NoticeStage_VM implements Stages {
    private StringProperty buttonText ;

    private StringProperty helpText ;
    private StringProperty titleText;
    private StringProperty contentText;

    private BooleanProperty alert;




    private Notice notice;
    private Experiment experiment;
    public NoticeStage_VM(Notice noticeStage) {
        this.notice = noticeStage;
        this.titleText = new SimpleStringProperty(noticeStage.getTitle());
        this.contentText = new SimpleStringProperty(noticeStage.getContent());
        this.buttonText = new SimpleStringProperty(noticeStage.getButtonText());
        this.helpText = new SimpleStringProperty(noticeStage.getHelpText());
        this.alert = new SimpleBooleanProperty(noticeStage.isAlert());
    }
    public NoticeStage_VM(Experiment experiment) {
        this.experiment = experiment;
        this.notice = new Notice("User input", null,null, null,false);
        this.buttonText = new SimpleStringProperty(notice.getButtonText());
        this.helpText = new SimpleStringProperty(notice.getHelpText());
        this.alert = new SimpleBooleanProperty(notice.isAlert());
        this.titleText = new SimpleStringProperty(notice.getTitle());
        this.contentText = new SimpleStringProperty(notice.getContent());
        experiment.addNoticeStage(notice);

    }




    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public String getButtonText() {
        return buttonText.get();
    }
    public String getHelpText() {
        return helpText.get();
    }
    public boolean getAlert(){
        return alert.get();
    }



    public StringProperty buttonTextProperty() {
        return buttonText;
    }

//
    public StringProperty helpTextProperty() {
        return helpText;
    }

    public StringProperty titleProperty() {
        return titleText;
    }

    public StringProperty contentProperty() {
        return contentText;
    }

    public BooleanProperty alertProperty() {
        return alert;
    }
    public void setHelpText(String newValue) {
        notice.setHelpText(newValue);
    }
    public void setTitle(String newValue) {
        notice.setTitle(newValue);
    }
    public void setContent(String newValue) {
        notice.setContent(newValue);
    }


    public void setButtonText(String newValue) {
        notice.setButtonText(newValue);
    }
    public void setAlert(Boolean newValue) {
        notice.setAlert(newValue);
    }
    public Notice getNotice(){
        return notice;
    }


    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddNoticeStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);


      //  System.out.println("Children in anchorPane after setting new content: " + anchorPane.getChildren().getFirst().getAccessibleRole());
        NoticeStageController controller = fxmlLoader.getController();
        controller.setNoticeStage_vm(this);


    }

    @Override
    public void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Stack<RatingContainer_VM> ratingContainerVm) throws IOException {
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
    public String toString() {
       return "[Instruction] "+ titleText.get();
    }
}
