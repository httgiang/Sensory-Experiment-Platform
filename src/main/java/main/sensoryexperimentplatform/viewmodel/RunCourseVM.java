package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunCourseController;
import main.sensoryexperimentplatform.models.Course;

import java.io.IOException;

public class RunCourseVM implements RunStages{
    private Course course;
    StringProperty title, content, button, help, end;
    IntegerProperty refill, duration, quantity;
    public RunCourseVM(Course course) {
        this.course = course;
        System.out.println(course.toString());
        title = new SimpleStringProperty(course.getTitle());
        content = new SimpleStringProperty(course.getContent());
        button = new SimpleStringProperty(course.getButtonText());
        help = new SimpleStringProperty(course.getHelpText());
        refill = new SimpleIntegerProperty(course.getRefillWeight());
        duration = new SimpleIntegerProperty(course.getDuration());
        quantity = new SimpleIntegerProperty(course.getQuantity());
        end = new SimpleStringProperty(course.getEndStatement());
    }
    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunCourse.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);

        RunCourseController controller = loader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleRunButtons(Button btn_next, Button btn_back) {
        btn_next.textProperty().bind(this.buttonProperty());
    }

    public StringProperty titleProperty() {
        return title;
    }
    public StringProperty contentProperty() {
        return content;
    }
    public StringProperty buttonProperty() {
        return button;
    }
    public StringProperty helpProperty() {
        return help;
    }
    public IntegerProperty refillProperty() {
        return refill;
    }
    public IntegerProperty durationProperty() {
        return duration;
    }
    public IntegerProperty quantityProperty() {
        return quantity;
    }
    public StringProperty endProperty() {
        return end;
    }
    public Course getCourse() {
        return course;
    }


}
