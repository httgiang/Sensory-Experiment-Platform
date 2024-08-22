package main.sensoryexperimentplatform.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.AddCourseController;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class Course extends ModelContainer implements Model{
    private boolean alert;
    private String buttonText, helpText, title, content;
    private String endStatement;
    private int refillWeight;
    private int duration;
    private int quantity;
    private Periodic periodic;
    public Course(String title, String content, String buttonText, String endStatement,
                  int refillWeight, int duration, int quantity, String helpText, boolean alert){
        this.title = title;
        this.content = content;
        this.buttonText = buttonText;
        this.refillWeight = refillWeight;
        this.duration = duration;
        this.quantity = quantity;
        this.helpText = helpText;
        this.endStatement = endStatement;
        this.alert = alert;
        children = new ArrayList<>();
    }
    public Course(Course course){
        this.title = course.getTitle();
        this.content = course.getContent();
        this.buttonText = course.getButtonText();
        this.refillWeight = course.getRefillWeight();
        this.duration = course.getDuration();
        this.quantity = course.getQuantity();
        this.helpText = course.getHelpText();
        this.endStatement = course.getEndStatement();
        this.alert = course.isAlert();;

        this.children = course.getChildren();
    }
    //Default
    public Course(String title, String content){
        refillWeight = -1;
        duration = -1;
        quantity = -1; //grams
        helpText = "Please remember while eating:\n" +
                "Do not leave your fork in the bowl at any time: if you want to put your fork down, please use the small plate provided. " +
                "\nPlease also do not lean on the placemat.\n" +
                "Click on Meal Finished ONLY when you are sure you have had enough food.";
        buttonText = "Finished";
        endStatement = "End session";

    }
    public void setPeriodic(Periodic periodic){
        this.periodic = periodic;
    }
    public void show(){
        System.out.println("Title: " + title);
        System.out.println("content: " + content);
        System.out.println("buttonText: " + buttonText);
        System.out.println("duration: " + duration +" seconds");
        System.out.println("Quantity need to be consumed: "+ quantity +" grams");
        System.out.println("helpText: " + helpText);
        System.out.println("endStatement: " + endStatement);

    }

    public boolean isAlert() {
        return alert;
    }
    
    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getRefillWeight() {
        return refillWeight;
    }
    public void setRefillWeight(int refillWeight) {
        this.refillWeight = refillWeight;
    }

    public int getDuration() {
        return duration;
    }


    public String getContent() {
        return content;
    }


    public String getTitle() {
        return title;
    }

    public String getEndStatement() {
        return endStatement;
    }

    public Periodic getPeriodic() {
        return periodic;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return "startEating(\"" + getTitle() + "\",\"" + getContent() + "\",\""
                + getButtonText() + "\",\"" + getEndStatement() + "\",\""
                + getRefillWeight() + "\",\"" + getQuantity() +"\",\""
                + getDuration() + "\",\"" + getHelpText() + "\",\"" + isAlert() + "\")\n"
                + componentToString() + "endEating()";
    }
}
