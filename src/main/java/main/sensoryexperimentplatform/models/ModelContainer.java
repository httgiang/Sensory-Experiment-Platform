package main.sensoryexperimentplatform.models;

import org.controlsfx.control.PropertySheet;

import java.util.*;

public abstract class ModelContainer implements Model{ //TasteTest, Conditional Statement, Course, Rating Container
    protected ArrayList<Model> children;

    public void addChildren(Model model) {
        children.add(model);
    }
    public List<Model> getChildren() {
        return children;
    }


}
