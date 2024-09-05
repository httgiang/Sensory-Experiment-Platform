package main.sensoryexperimentplatform.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.sensoryexperimentplatform.utilz.Observer;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.ExperimentList;

import java.util.List;

public class DashBoard_VM implements Observer {
    //private ObservableList<Experiment> experiments;
    private ListProperty<Experiment> experiments;
    private int size;

    public DashBoard_VM() {
        ExperimentList.attach(this);
        experiments = new SimpleListProperty<>(FXCollections.observableArrayList());
        reload();
    }

    public void reload() {
        try {
            loadItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void addExperiment(Experiment e) throws Exception {
//        listOfExperiment.getInstance().add(e);
//        experiments.add(e);
//    }
//    public void importExperiment(Experiment e){
//        experiments.add(e);
//    }
//
//    public void deleteExperiment(Experiment e) throws Exception {
//        listOfExperiment.deleteExperiment(e);
//        experiments.remove(e);
//    }

    public void loadItems() throws Exception {
        new Thread(() -> {
            List<Experiment> experimentList = ExperimentList.getInstance();
            Platform.runLater(() -> {
                experiments.setAll(experimentList);
                size = experiments.size();
               // System.out.println(size);
            });
        }).start();

    }

    public ListProperty<Experiment> experimentsProperty() {
        return experiments;
    }

    public ObservableList<Experiment> getExperiments() {
        return experiments;
    }

    @Override
    public void update() {
        try {
            experiments.setAll(ExperimentList.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
