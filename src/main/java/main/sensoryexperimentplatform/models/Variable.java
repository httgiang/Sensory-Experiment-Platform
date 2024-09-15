package main.sensoryexperimentplatform.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Variable implements Model {
    private ObservableList<String> variable;

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public void setVariable(ObservableList<String> variable) {
        this.variable = variable;
    }

    private String variableName;

    public Variable() {
        this.variable = FXCollections.observableArrayList();
        variable.add("hello");
        variable.add("world");
    }

    public void addVariable(String variableName) {
        if (!variable.contains(variableName)) {
            variable.add(variableName);
        }
        
    }
    public ObservableList<String> getVariable() {
        return variable;
    }


}
