package main.sensoryexperimentplatform.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import main.sensoryexperimentplatform.arduino.TestArduino;
import main.sensoryexperimentplatform.utilz.Observer;

public class Scale {
    private static Scale instance;
    private DoubleProperty weight;
    private Scale(){
        //handle connect logic
        weight = new SimpleDoubleProperty(0.0);
        TestArduino.addWeightListener(this);
    }


    public static Scale getScaleInstance(){
        if(instance == null){
            instance = new Scale();
        }
        return instance;
    }

    public double getWeight() {
        return weight.get();
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }


}
