package main.sensoryexperimentplatform.models;

public class IfConditionalStatement extends ConditionalStatement {
    public IfConditionalStatement(boolean value1, boolean value2, boolean variable1, boolean variable2, String value1Text, String value2Text, String variable1Choice, String variable2Choice, String compare) {
        super(value1, value2, variable1, variable2, value1Text, value2Text, variable1Choice, variable2Choice, compare);
    }

    public IfConditionalStatement(ConditionalStatement other) {
        super(other);
    }
//    public void addIf(Model m){
//       super.addIf(m);
//    }
}
