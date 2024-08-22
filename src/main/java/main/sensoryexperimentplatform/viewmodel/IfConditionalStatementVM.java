package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.ConditionalStatement;
import main.sensoryexperimentplatform.models.Model;


public class IfConditionalStatementVM extends ConditionalStatementVM{
    ConditionalStatementVM conditionalStatement;



    public IfConditionalStatementVM(ConditionalStatement ConditionalStatement) {
        super(ConditionalStatement);
    }
    @Override
    public void addIf(Model object){
        super.addIf(object);
    }

    public String toString(){
        return "If "+ getVariable1Choice() + " " + getCompare() + " Then " + getVariable2Choice() ;
    }
}
