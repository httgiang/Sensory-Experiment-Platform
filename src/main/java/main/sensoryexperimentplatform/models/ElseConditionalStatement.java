package main.sensoryexperimentplatform.models;

public class ElseConditionalStatement extends ConditionalStatement{
    public ElseConditionalStatement(boolean value1, boolean value2, boolean variable1, boolean variable2, String value1Text, String value2Text, String variable1Choice, String variable2Choice, String compare) {
        super(value1, value2, variable1, variable2, value1Text, value2Text, variable1Choice, variable2Choice, compare);
    }

    public ElseConditionalStatement(ConditionalStatement other) {
        super(other);
    }

    @Override
    public void addElse(Model m) {
        super.addElse(m);
    }
}
