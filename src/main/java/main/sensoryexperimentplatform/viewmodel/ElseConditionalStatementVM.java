package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.ConditionalStatement;

import main.sensoryexperimentplatform.models.Model;

public class ElseConditionalStatementVM extends ConditionalStatementVM {
    public ElseConditionalStatementVM(ConditionalStatement ConditionalStatement) {
        super(ConditionalStatement);
    }
    @Override
    public void addElse(Model object){
       super.addElse(object);
    }
    public String toString(){
        return "Else";
    }
}
