package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.AddPeriodic;
import main.sensoryexperimentplatform.models.Course;
import main.sensoryexperimentplatform.models.Periodic;

import java.io.IOException;
import java.util.Stack;

public class PeriodicVM implements Stages {
    private Course course;
    private SimpleStringProperty amoutEveryAfter;
    private SimpleStringProperty daiLuongAmount;
    private SimpleStringProperty daiLuongEveryAfter;
    private SimpleStringProperty everyFor;

    private SimpleStringProperty forAmount;
    public PeriodicVM (Course course){
        this.course = course;
        Periodic periodic = new Periodic(-1,"grams", "Every", -1, "grams");
        course.setPeriodic(periodic);
        amoutEveryAfter = new SimpleStringProperty(String.valueOf(periodic.getAmount()));
        daiLuongAmount = new SimpleStringProperty(periodic.getForDaiLuong());
        daiLuongEveryAfter = new SimpleStringProperty(periodic.getDaiLuong());
        forAmount = new SimpleStringProperty(String.valueOf(periodic.getForAmount()));
        everyFor = new SimpleStringProperty(periodic.getTime());
        forAmount.addListener((observableValue, oldValue, newValue) -> onForAmount(newValue));
        everyFor.addListener((observableValue, oldValue, newValue) -> onTime(newValue));
        amoutEveryAfter.addListener((observableValue, oldValue, newValue) -> onAmountEveryAfter(newValue));
        daiLuongAmount.addListener((observableValue, oldValue, newValue) -> onDaiLuongAmount(newValue));
        daiLuongEveryAfter.addListener((observableValue, oldValue, newValue) -> onForEvery(newValue));
    }

    private void onForAmount(String newValue) {
        course.getPeriodic().setForAmount(Long.parseLong(newValue));
    }

    private void onTime(String newValue) {
        course.getPeriodic().setTime(newValue);
    }

    private void onForEvery(String newValue) {
        course.getPeriodic().setForDaiLuong(newValue);
    }

    private void onDaiLuongAmount(String newValue) {

        course.getPeriodic().setForDaiLuong(newValue);
    }

    private void onAmountEveryAfter(String newValue) {
        course.getPeriodic().setAmount(Long.parseLong(newValue));
    }

    public String getAmoutEveryAfter() {
        return amoutEveryAfter.get();
    }

    public SimpleStringProperty amoutEveryAfterProperty() {
        return amoutEveryAfter;
    }

    public String getDaiLuongAmount() {
        return daiLuongAmount.get();
    }

    public SimpleStringProperty daiLuongAmountProperty() {
        return daiLuongAmount;
    }

    public String getDaiLuongEveryAfter() {
        return daiLuongEveryAfter.get();
    }

    public SimpleStringProperty daiLuongEveryAfterProperty() {
        return daiLuongEveryAfter;
    }

    public String getEveryFor() {
        return everyFor.get();
    }

    public SimpleStringProperty everyForProperty() {
        return everyFor;
    }

    public String getForAmount() {
        return forAmount.get();
    }

    public SimpleStringProperty forAmountProperty() {
        return forAmount;
    }

    @Override
    public void loadInterface(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddPeriodicStage.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        AddPeriodic controller = fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Stack<RatingContainer_VM> ratingContainerVm) throws IOException {

    }

    @Override
    public String toString() {
        return course.getPeriodic().getTime() +" "+ course.getPeriodic().getAmount() +" "+ course.getPeriodic().getDaiLuong();
    }

}
