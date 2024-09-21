package main.sensoryexperimentplatform.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ConditionalStatement implements Model{
    private boolean value1;
    private boolean value2;
    private boolean variable1;
    private boolean variable2;
    private String value1Text;
    private String value2Text;
    private String variable1Choice;
    private String variable2Choice;
    private String compare;
    private ObservableList<Model> ifConditional ;
    private ObservableList<Model> elseConditional;
    private int BoutNumber;


    public ConditionalStatement(boolean value1, boolean value2, boolean variable1, boolean variable2, String value1Text,
                                String value2Text, String variable1Choice, String variable2Choice, String compare){

        this.value1 = value1;
        this.value2 = value2;
        this.variable1 = variable1;
        this.variable2 = variable2;
        this.value2Text = value2Text;
        this.value1Text = value1Text;
        this.variable1Choice = variable1Choice;
        this.variable2Choice = variable2Choice;
        this.compare = compare;
        this.BoutNumber = 9600;
        this.ifConditional = FXCollections.observableArrayList();
        this.elseConditional = FXCollections.observableArrayList();

    }


    public ConditionalStatement(ConditionalStatement other){
        this.value1 = other.value1;
        this.value2 = other.value2;
        this.variable1 = other.variable1;
        this.variable2 = other.variable2;
        this.value2Text = other.value2Text;
        this.value1Text = other.value1Text;
        this.variable1Choice = other.variable1Choice;
        this.variable2Choice = other.variable2Choice;
        this.compare = other.compare;
        this.BoutNumber = 9600;
        this.ifConditional = FXCollections.observableArrayList(other.ifConditional);
        this.elseConditional = FXCollections.observableArrayList(other.elseConditional);

    }
    // Add vas for rating container


    public boolean isValue1() {
        return value1;
    }

    public void setValue1(boolean value1) {
        this.value1 = value1;
    }

    public boolean isValue2() {
        return value2;
    }

    public void setValue2(boolean value2) {
        this.value2 = value2;
    }

    public boolean isVariable1() {
        return variable1;
    }

    public void setVariable1(boolean variable1) {
        this.variable1 = variable1;
    }

    public boolean isVariable2() {
        return variable2;
    }

    public void setVariable2(boolean variable2) {
        this.variable2 = variable2;
    }

    public String getValue1Text() {
        return value1Text;
    }

    public void setValue1Choice(String value1Text) {
        this.value1Text = value1Text;
    }

    public String getValue2Text() {
        return value2Text;
    }

    public void setValue2Choice(String value2Text) {
        this.value2Text = value2Text;
    }

    public String getVariable1Choice() {
        return variable1Choice;
    }

    public void setVariable1Choice(String variable1Choice) {
        this.variable1Choice = variable1Choice;
    }

    public String getVariable2Choice() {
        return variable2Choice;
    }

    public void setVariable2Choice(String variable2Choice) {
        this.variable2Choice = variable2Choice;
    }

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
    }

    public void addIf(Model m){
        ifConditional.add(m);
        System.out.println(ifConditional + "2");
    }
    public void addElse(Model m){
        elseConditional.add(m);
        System.out.println(elseConditional + "1000");
    }

    public ObservableList<Model> getIfConditional() {
        return ifConditional;
    }

    public ObservableList<Model> getElseConditional() {
        return elseConditional;
    }

    private int getVariableBasedOnChoice(String choice) {
        switch (choice) {
            case "Bout Number":
                return 9600;

            default:
                return 0;
        }
    }

    public boolean evaluateCondition() {
        int leftSideValue = variable1 ? getVariableBasedOnChoice(variable1Choice) : Integer.parseInt(value1Text);
        int rightSideValue = variable2 ? getVariableBasedOnChoice(variable2Choice) : Integer.parseInt(value2Text);

        switch (compare) {
            case "less Than (<)":
                return leftSideValue < rightSideValue;
            case "greater than (>)":
                return leftSideValue > rightSideValue;
            case "equals (=)":
                return leftSideValue == rightSideValue;
            case "not equal to (!=)":
                return leftSideValue != rightSideValue;
            case "less than or equals to(=>)":
                return leftSideValue <= rightSideValue;
            case "greater than or equals to(>=)":
                return leftSideValue >= rightSideValue;
            default:
                return false;
        }
    }




    @Override
    public String toString() {
        StringBuilder ifBuilder = new StringBuilder();
        for (Model model : ifConditional) {
            System.out.println(getIfConditional() );
            ifBuilder.append(model.toString()).append("\n");
        }

        StringBuilder elseBuilder = new StringBuilder();
        for (Model model : elseConditional) {
            elseBuilder.append(model.toString()).append("\n");
        }

        if (!ifBuilder.isEmpty()) ifBuilder.setLength(ifBuilder.length() - 1);
        if (!elseBuilder.isEmpty()) elseBuilder.setLength(elseBuilder.length() - 1);

        return "conditionalStatement(\"" + value1 + "\",\"" + value2 + "\",\"" + variable1 + "\",\"" + variable2 +
                "\",\"" + value1Text + "\",\"" + value2Text + "\",\"" + variable1Choice + "\",\"" +
                variable2Choice + "\",\"" + compare + "\")" + "\n" +
                String.format("If() \n%s \nEndIf() \nElse() \n%s \nEndElse()", ifBuilder, elseBuilder) + "\nEndConditionalStatement()";
    }




}
