package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.ConditionalStatement;
import main.sensoryexperimentplatform.models.Experiment;


public class IfConditionalStatementVM extends ConditionalStatementVM{
    ConditionalStatementVM conditionalStatement;


    public IfConditionalStatementVM(ConditionalStatement ConditionalStatement) {
        super(ConditionalStatement);
    }

    public IfConditionalStatementVM(Experiment experiment) {
        super(experiment);
    }
    public String toString(){
        return "If "+ getVariable1Choice() + " " + getCompare() + " Then " + getVariable2Choice() ;
    }
}
