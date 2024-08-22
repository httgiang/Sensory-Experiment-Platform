package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.ConditionalStatement;
import main.sensoryexperimentplatform.models.Experiment;

public class ElseConditionalStatementVM extends ConditionalStatementVM {
    public ElseConditionalStatementVM(ConditionalStatement ConditionalStatement) {
        super(ConditionalStatement);
    }

    public ElseConditionalStatementVM(Experiment experiment) {
        super(experiment);
    }
    public String toString(){
        return "Else";
    }
}
