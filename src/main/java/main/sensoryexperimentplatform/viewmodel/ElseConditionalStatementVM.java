package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.ConditionalStatement;

import main.sensoryexperimentplatform.models.Model;

public class ElseConditionalStatementVM extends ConditionalStatementVM implements ViewModel {
    public ElseConditionalStatementVM(ConditionalStatement ConditionalStatement) {
        super(ConditionalStatement);
    }

    public void addElse(Model object){
        getConditionalStatement().getElseConditional().add(object);
        System.out.println( getConditionalStatement().getElseConditional().add(object));
    }

    public String toString(){
        return "Else";
    }
}
