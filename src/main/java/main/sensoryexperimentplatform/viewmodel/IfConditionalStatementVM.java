package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.ConditionalStatement;
import main.sensoryexperimentplatform.models.Model;


public class IfConditionalStatementVM extends ConditionalStatementVM implements ViewModel {
    ConditionalStatementVM conditionalStatement;



    public IfConditionalStatementVM(ConditionalStatement ConditionalStatement) {
        super(ConditionalStatement);
    }

    public void addIf(Model object){
       getConditionalStatement().getIfConditional().add(object);
    }



    public String toString(){
        return "If "+  (getVariable1Choice() != null && !getVariable1Choice().isEmpty() ? getVariable1Choice() : getValue1Text()) + " " + getCompare() + " Then " + (getVariable2Choice() != null && !getVariable2Choice().isEmpty() ? getVariable2Choice() : getValue2Text()) ;
    }
}
