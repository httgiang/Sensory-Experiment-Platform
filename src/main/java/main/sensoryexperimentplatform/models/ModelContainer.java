package main.sensoryexperimentplatform.models;

import org.controlsfx.control.PropertySheet;

import java.util.*;
//TasteTest, Conditional Statement, Course, Rating Container
public abstract class ModelContainer implements Model{
    protected ArrayList<Model> children;

    public void addChildren(Model model) {
        children.add(model);
    }
    public ArrayList<Model> getChildren() {
        return children;
    }
    public String componentToString(){
        if (children == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if(!children.isEmpty()){
            for(Model stage : children){
                sb.append(stage.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public void setChildren(ArrayList<Model> children) {
        this.children = children;
    }
}
