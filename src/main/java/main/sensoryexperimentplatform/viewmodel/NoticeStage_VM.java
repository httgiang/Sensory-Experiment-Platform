package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;

public class NoticeStage_VM implements ViewModel{
    private StringProperty buttonText ;

    private StringProperty helpText ;
    private StringProperty titleText;
    private StringProperty contentText;

    private BooleanProperty alert;


    private Notice notice;
    private Experiment experiment;
    public NoticeStage_VM(Notice noticeStage) {
        this.notice = noticeStage;
        initListener();
    }
    public NoticeStage_VM() {
        this.notice = new Notice("User input", null,null, null,false);
        initListener();
     //   experiment.addNoticeStage(notice);

    }

    public void initListener(){
        this.titleText = new SimpleStringProperty(notice.getTitle());
        this.contentText = new SimpleStringProperty(notice.getContent());
        this.buttonText = new SimpleStringProperty(notice.getButtonText());
        this.helpText = new SimpleStringProperty(notice.getHelpText());
        this.alert = new SimpleBooleanProperty(notice.isAlert());
    }


    @Override
    public Model getModel() {
        return notice;
    }
    public NoticeStage_VM(IfConditionalStatementVM ifConditionalStatementVM) {
        this.notice = new Notice("User input", null,null, null,false);
        this.buttonText = new SimpleStringProperty(notice.getButtonText());
        this.helpText = new SimpleStringProperty(notice.getHelpText());
        this.alert = new SimpleBooleanProperty(notice.isAlert());
        this.titleText = new SimpleStringProperty(notice.getTitle());
        this.contentText = new SimpleStringProperty(notice.getContent());
        ifConditionalStatementVM.addIf(notice);
    }
    public NoticeStage_VM(ElseConditionalStatementVM elseConditionalStatementVM) {
        this.notice = new Notice("User input", null,null, null,false);
        this.buttonText = new SimpleStringProperty(notice.getButtonText());
        this.helpText = new SimpleStringProperty(notice.getHelpText());
        this.alert = new SimpleBooleanProperty(notice.isAlert());
        this.titleText = new SimpleStringProperty(notice.getTitle());
        this.contentText = new SimpleStringProperty(notice.getContent());
        elseConditionalStatementVM.addElse(notice);
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunNotice.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);

        RunNoticeController controller = fxmlLoader.getController();

        controller.setViewModel(this);
    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddNoticeStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        NoticeStageController controller = fxmlLoader.getController();
        controller.setNoticeStage_vm(this);
    }

    @Override
    public void handleEditButtons(Button button1, Button button2, Button button3,
                                  Button button4, Button button5, Button button6,
                                  Button button7, Button button8, Button button9,
                                  Button button10, Button button11, Button button12)
            throws IOException {
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
    public void handleRunButtons(Button btn_next, Button btn_back, Tooltip tooltip, ImageView help_image) {
        btn_back.setDisable(false);
        btn_next.setDisable(false);
        btn_next.textProperty().bind(this.buttonTextProperty());

        if (this.helpTextProperty().get() != null) {
            help_image.setVisible(true);
            tooltip.textProperty().bind(this.helpTextProperty());
        }
        if(this.helpTextProperty().get()  == null || this.helpTextProperty().get().isEmpty()) {
            help_image.setVisible(false);
            help_image.setManaged(false);
        }
    }

    @Override
    public String toString() {
        return "[Instruction] "+ titleText.get();
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
    public void playSound(){
        notice.playSound();
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


}
