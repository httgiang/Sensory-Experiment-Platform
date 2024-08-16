package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.conditionalStatementController;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.conditionalStatement;

import java.io.IOException;
import java.util.Stack;

public class ConditionalStatementVM implements Stages {
    private  conditionalStatement ConditionalStatement;

    private SimpleStringProperty value1Text;
    private SimpleStringProperty value2Text;
    private SimpleStringProperty variable2Choice;
    private SimpleStringProperty variable1Choice;
    private SimpleStringProperty compare;
    private Experiment experiment;
    public ConditionalStatementVM(conditionalStatement ConditionalStatement){
        this.ConditionalStatement = new conditionalStatement(true, false,true,false,null,null,"Something","Something else","Less Than");
        value1Text = new SimpleStringProperty(ConditionalStatement.getValue1Text());
        value2Text = new SimpleStringProperty(ConditionalStatement.getValue2Text());
        variable1Choice = new SimpleStringProperty(ConditionalStatement.getVariable1Choice());
        variable2Choice = new SimpleStringProperty(ConditionalStatement.getVariable2Choice());
        compare = new SimpleStringProperty(ConditionalStatement.getCompare());
        value1Text.addListener((observableValue, oldValue, newValue)->setValue1Choice(newValue));
        value2Text.addListener((observableValue, oldValue, newValue)->setValue2Choice(newValue));
        variable1Choice.addListener((observableValue, oldValue, newValue)->setVariable1Choice(newValue));
        variable2Choice.addListener((observableValue, oldValue, newValue)->setVariable2Choice(newValue));
        compare.addListener((observableValue, oldValue, newValue)->setCompare(newValue));
    }
    public ConditionalStatementVM(Experiment experiment){
        this.experiment = experiment;
        this.ConditionalStatement = new conditionalStatement(true, false,true,false,null,null,"Something","Something else","Less Than");
        experiment.addConditionalStatement(ConditionalStatement);
        value1Text = new SimpleStringProperty(ConditionalStatement.getValue1Text());
        value2Text = new SimpleStringProperty(ConditionalStatement.getValue2Text());
        variable1Choice = new SimpleStringProperty(ConditionalStatement.getVariable1Choice());
        variable2Choice = new SimpleStringProperty(ConditionalStatement.getVariable2Choice());
        compare = new SimpleStringProperty(ConditionalStatement.getCompare());
        value1Text.addListener((observableValue, oldValue, newValue)->setValue1Choice(newValue));
        value2Text.addListener((observableValue, oldValue, newValue)->setValue2Choice(newValue));
        variable1Choice.addListener((observableValue, oldValue, newValue)->setVariable1Choice(newValue));
        variable2Choice.addListener((observableValue, oldValue, newValue)->setVariable2Choice(newValue));
        compare.addListener((observableValue, oldValue, newValue)->setCompare(newValue));

    }

    public void setValue1Choice(String newValue){
     ConditionalStatement.setValue1Choice(newValue);
    }
    public void setValue2Choice(String newValue){
        ConditionalStatement.setValue2Choice(newValue);
    }
    public void setVariable2Choice(String newValue){
        ConditionalStatement.setVariable2Choice(newValue);
    }
    public void setVariable1Choice(String newValue){
        ConditionalStatement.setVariable1Choice(newValue);
    }
    public void setCompare(String newValue){
        ConditionalStatement.setCompare(newValue);
    }
    public SimpleStringProperty Value1TextProperty(){
        return value1Text;
    }
    public SimpleStringProperty Value2TextProperty(){
        return value2Text;
    }
    public SimpleStringProperty Variable1ChoiceProperty(){
        return variable1Choice;
    }
    public SimpleStringProperty Variable2ChoiceProperty(){
        return variable2Choice;
    }
    public SimpleStringProperty compareProperty(){
        return compare;
    }
    public String getValue1Text(){
        return value1Text.get();
    }
    public String getValue2Text(){
        return value2Text.get();
    }
    public String getVariable2Choice(){
        return variable2Choice.get();
    }
    public String getVariable1Choice(){
        return variable1Choice.get();
    }
    public String getCompare(){
        return compare.get();
    }


    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddConditionalStatement.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        conditionalStatementController controller  = fxmlLoader.getController();
        controller.setViewModel(this);

    }

    @Override
    public void handleMenuButtons(AnchorPane anchorPane, Stack<AddTasteVM> stack, Stack<AddCourseVM> addCourseVMS, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6, Button button7, Button button8, Button button9, Button button10, Button button11, Button button12, Stack<RatingContainer_VM> rating) throws IOException {
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

    public conditionalStatement getConditionalStatement() {
        return ConditionalStatement;
    }



    @Override
    public String toString() {
        return "If "+ ConditionalStatement.getVariable1Choice() + " " + ConditionalStatement.getCompare() + " Then " + ConditionalStatement.getVariable2Choice() ;
    }




    public String getTitle2(){
        return "Else";
    }

}

