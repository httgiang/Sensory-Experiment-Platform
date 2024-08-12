package main.sensoryexperimentplatform.models;

import main.sensoryexperimentplatform.utilz.Observable;
import main.sensoryexperimentplatform.utilz.Observer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class listOfExperiment extends Observable {
    private static HashSet<String> experimentsName;
    private static ArrayList<Experiment> experiments;
    private List<Observer> observers = new ArrayList<Observer>();

    public static synchronized ArrayList<Experiment> getInstance() {
        if (experiments == null){
            experimentsName = new HashSet<>();
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }

    public static void addExperiment(Experiment experiment) throws Exception {
        for(Experiment e : getInstance()){
            System.out.println(e);
        }
        if(experimentsName.add(experiment.getExperimentName())){
            getInstance().add(experiment);
        }
        //DataAccess.saveNewExperiment(experiment);
        notifyAllObservers();
    }

    public static void deleteExperiment(Experiment experiment) throws Exception {
        if (getInstance().remove(experiment)) {
            DataAccess.updateFile();
            notifyAllObservers();
        }

    }



}
