package main.sensoryexperimentplatform.models;

import java.util.ArrayList;
import java.util.List;

public class ConditionalStatement extends ModelContainer implements Model{
    private boolean value1;
    private boolean value2;
    private boolean variable1;
    private boolean variable2;
    private String value1Text;
    private String value2Text;
    private String variable1Choice;
    private String variable2Choice;
    private String compare;
    private ArrayList<Model> IfConditional;
    private ArrayList<Model> ElseConditional;


    public ConditionalStatement(boolean value1, boolean value2, boolean variable1, boolean variable2, String value1Text,
                                String value2Text, String variable1Choice, String variable2Choice, String compare){
        this.value1 = value1;
        this.value2 = value2;
        this.variable1 = variable1;
        this.variable2 = variable2;
        this.value2Text = value2Text;
        this.value1Text= value1Text;
        this.variable1Choice = variable1Choice;
        this.variable2Choice = variable2Choice;
        this.compare = compare;
        children = new ArrayList<>();
        IfConditional = new ArrayList<>();
        ElseConditional = new ArrayList<>();

    }

    public void addChildren(Model s){
        children.add(s);
    }
    public void addIf(Model m){
        IfConditional.add(m);
    }
    public void addElse(Model m){
        ElseConditional.add(m);
    }

    public ArrayList<Model> getElseIfConditional() {
        return ElseConditional;
    }
    public ArrayList<Model> getIfConditional() {
        return IfConditional;
    }

    public List<Model> getChildren(){
        return children;
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
        children = new ArrayList<>();
//        for(Model m : other.children){
//            if(m instanceof ConditionalStatement){
//                Vas temp = new Vas((Vas) m);
//                addChildren(temp);
//            }
//            if (m instanceof gLMS) {
//                gLMS temp = new gLMS((gLMS) m);
//                addChildren(temp);
//            }
//            if(m instanceof Notice){
//                Notice temp = new Notice((Notice) m);
//                addChildren(temp);
//            }
//            if(m instanceof Input){
//                Input temp = new Input((Input) m);
//                addChildren(temp);
//            }
//            if(m instanceof Question){
//                Question temp = new Question((Question) m);
//                addChildren(temp);
//            }
//            if(m instanceof AudibleInstruction){
//                AudibleInstruction temp = new AudibleInstruction((AudibleInstruction) m);
//                addChildren(temp);
//            }
//            if(m instanceof Timer){
//                Timer temp = new Timer((Timer) m);
//                addChildren(temp);
//            }
//
//        }

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

    public String toString() {
        return "if" + getIfConditional() + "else" + getElseIfConditional();
    }





}
