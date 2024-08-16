package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.*;
import main.sensoryexperimentplatform.models.Course;

public class RunCourseVM {
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
