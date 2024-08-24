package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.ConditionalStatement;
import main.sensoryexperimentplatform.models.Model;


public class IfConditionalStatementVM extends ConditionalStatementVM implements ViewModel {
    ConditionalStatementVM conditionalStatement;



    public IfConditionalStatementVM(ConditionalStatement ConditionalStatement) {
        super(ConditionalStatement);
    }


    public String toString(){
        return "If "+ getVariable1Choice() + " " + getCompare() + " Then " + getVariable2Choice() ;
    }
}
