package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;



public class ConditionalStatementVM implements ViewModel {
    private ConditionalStatement conditionalStatement;

    private SimpleStringProperty value1Text;
    private SimpleStringProperty value2Text;
    private SimpleStringProperty variable2Choice;
    private SimpleStringProperty variable1Choice;
    private SimpleStringProperty compare;
    ModelVMRegistry registry = ModelVMRegistry.getInstance();

    public ConditionalStatementVM(ConditionalStatement conditionalStatement){
        this.conditionalStatement = new ConditionalStatement(true, false,true,false,null,null,"Something","Something else","Less Than");
        value1Text = new SimpleStringProperty(conditionalStatement.getValue1Text());
        value2Text = new SimpleStringProperty(conditionalStatement.getValue2Text());
        variable1Choice = new SimpleStringProperty(conditionalStatement.getVariable1Choice());
        variable2Choice = new SimpleStringProperty(conditionalStatement.getVariable2Choice());
        compare = new SimpleStringProperty(conditionalStatement.getCompare());
        value1Text.addListener((observableValue, oldValue, newValue)->setValue1Choice(newValue));
        value2Text.addListener((observableValue, oldValue, newValue)->setValue2Choice(newValue));
        variable1Choice.addListener((observableValue, oldValue, newValue)->setVariable1Choice(newValue));
        variable2Choice.addListener((observableValue, oldValue, newValue)->setVariable2Choice(newValue));
        compare.addListener((observableValue, oldValue, newValue)->setCompare(newValue));
    }
    public ConditionalStatementVM(){
        this.conditionalStatement = new ConditionalStatement(true, false,true,false,null,null,"Something","Something else","Less Than");

        value1Text = new SimpleStringProperty(conditionalStatement.getValue1Text());
        value2Text = new SimpleStringProperty(conditionalStatement.getValue2Text());
        variable1Choice = new SimpleStringProperty(conditionalStatement.getVariable1Choice());
        variable2Choice = new SimpleStringProperty(conditionalStatement.getVariable2Choice());
        compare = new SimpleStringProperty(conditionalStatement.getCompare());
        value1Text.addListener((observableValue, oldValue, newValue)->setValue1Choice(newValue));
        value2Text.addListener((observableValue, oldValue, newValue)->setValue2Choice(newValue));
        variable1Choice.addListener((observableValue, oldValue, newValue)->setVariable1Choice(newValue));
        variable2Choice.addListener((observableValue, oldValue, newValue)->setVariable2Choice(newValue));
        compare.addListener((observableValue, oldValue, newValue)->setCompare(newValue));

    }

    public void setValue1Choice(String newValue){
     conditionalStatement.setValue1Choice(newValue);
    }
    public void setValue2Choice(String newValue){
        conditionalStatement.setValue2Choice(newValue);
    }
    public void setVariable2Choice(String newValue){
        conditionalStatement.setVariable2Choice(newValue);
    }
    public void setVariable1Choice(String newValue){
        conditionalStatement.setVariable1Choice(newValue);
    }
    public void setCompare(String newValue){
        conditionalStatement.setCompare(newValue);
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

    public void initRunSetup(ListView<ViewModel> listView){
        showChildrenPane(listView);
    }
    public void showChildrenPane(ListView<ViewModel> listView){
        if(conditionalStatement.evaluateCondition()){
            for(Model ifConditional: conditionalStatement.getIfConditional()){
                listView.getItems().add(registry.getViewModel(ifConditional));
            }
        }
        else{
            for(Model elseConditional: conditionalStatement.getElseConditional()){
                listView.getItems().add(registry.getViewModel(elseConditional));
            }
        }

    }


    @Override
    public Model getModel() {
        return conditionalStatement;
    }

    @Override
    public void loadRunInterface(AnchorPane anchorPane) {

    }

    @Override
    public void loadEditInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddConditionalStatement.fxml"));
        AnchorPane newContent = fxmlLoader.load();
        anchorPane.getChildren().setAll(newContent);
        conditionalStatementController controller  = fxmlLoader.getController();
        controller.setViewModel(this);
    }

    @Override
    public void handleEditButtons(Button button1, Button button2,
                                  Button button3, Button button4, Button button5,
                                  Button button6, Button button7, Button button8,
                                  Button button9, Button button10, Button button11, Button button12)
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
    public void handleRunButtons(Button btn_next, Button btn_back, Tooltip tooltip, Tooltip nextButtonTooltip, ImageView help_image) {
        btn_back.setDisable(false);
        btn_next.setDisable(false);
        btn_next.setWrapText(true);

      //  btn_next.textProperty().bind(this.button);
    }

    public ConditionalStatement getConditionalStatement() {
        return conditionalStatement;
    }



    @Override
    public String toString() {
        return "If "+ conditionalStatement.getVariable1Choice() + " " + conditionalStatement.getCompare() + " Then " + conditionalStatement.getVariable2Choice() ;
    }


    public void addIf(Model object){
        conditionalStatement.addIf(object);
    }
    public void addElse(Model object){
        conditionalStatement.addElse(object);
    }


    public String getTitle2(){
        return "Else";
    }

}

