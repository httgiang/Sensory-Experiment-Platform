package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.ConditionalStatement;

import main.sensoryexperimentplatform.models.Model;

public class ElseConditionalStatementVM extends ConditionalStatementVM {
    public ElseConditionalStatementVM(ConditionalStatement ConditionalStatement) {
        super(ConditionalStatement);
    }

    public String toString(){
        return "Else";
    }
}
