package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.Model;
import main.sensoryexperimentplatform.utilz.Observer;

import java.util.HashMap;

public class Experiment_VM  {
    HashMap<String, Model> map;
    private Experiment experiment;

    public Experiment_VM(Experiment experiment){
        this.experiment = experiment;
        map = new HashMap<>();

    }
    private void mapStageToIndex(){
        int index = 0;
        for(Model model : experiment.getStages()){
            map.put(index++ + " " + model.getClass().getSimpleName(), model);

        }

    }




}
