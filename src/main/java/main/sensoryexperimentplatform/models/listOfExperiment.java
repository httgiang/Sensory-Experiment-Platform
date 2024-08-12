package main.sensoryexperimentplatform.models;

import main.sensoryexperimentplatform.utilz.Observable;
import main.sensoryexperimentplatform.utilz.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class listOfExperiment extends Observable {
    private static ArrayList<Experiment> experiments;
    private List<Observer> observers = new ArrayList<Observer>();

    public static synchronized ArrayList<Experiment> getInstance() {
        if (experiments == null){
            experiments = new ArrayList<Experiment>();
        }
        return experiments;
    }

    public static void addExperiment(Experiment experiment) throws Exception {
        if(!getInstance().contains(experiment)){
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

    public static void importExperiment(Experiment experiment){
        if(!getInstance().contains(experiment)){
            getInstance().add(experiment);
        }
    }


}
