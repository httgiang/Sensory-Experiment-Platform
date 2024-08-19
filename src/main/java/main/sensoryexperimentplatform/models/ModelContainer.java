package main.sensoryexperimentplatform.models;

import org.controlsfx.control.PropertySheet;

import java.util.*;
//TasteTest, Conditional Statement, Course, Rating Container
public abstract class ModelContainer implements Model{
    protected ArrayList<Model> children;

    public void addChildren(Model model) {
        children.add(model);
    }
    public List<Model> getChildren() {
        return children;
    }
}
