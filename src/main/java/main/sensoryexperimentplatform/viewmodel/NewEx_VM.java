package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import main.sensoryexperimentplatform.utilz.Observer;
import main.sensoryexperimentplatform.models.DataAccess;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.ExperimentList;

import static java.util.Arrays.setAll;

public class NewEx_VM implements Observer {
    private StringProperty experimentName;
    private StringProperty creatorName;
    private StringProperty additionNote;
    private StringProperty description;
    private Experiment experiment;

    private ExperimentList ExperimentList;
    private final ListProperty<Experiment> items = new SimpleListProperty<>(FXCollections.observableArrayList());
    
    public NewEx_VM() {
        this.experiment = new Experiment(null,null,null, null,1,000,DataAccess.getCurrentFormattedDate());
        this.experimentName = new SimpleStringProperty(experiment.getExperimentName());
        this.creatorName = new SimpleStringProperty(experiment.getCreatorName());
        this.additionNote = new SimpleStringProperty(experiment.getNote());
        this.description = new SimpleStringProperty(experiment.getNote());

        experimentName.addListener((observable, oldValue, newValue) -> {
           setExperimentName(newValue);
//            System.out.println(experiment);
        });

        creatorName.addListener((observable, oldValue, newValue) -> {
            setCreatorName(newValue);
        });

        additionNote.addListener((observable, oldValue, newValue) -> {
            setNote(newValue);
        });

        description.addListener((observable, oldValue, newValue) -> {
            setDescription(newValue);
        });


    }
    public StringProperty ExperimentNameProperty() {
        return experimentName;
    }
    public StringProperty CreatorNameProperty() {
        return creatorName;
    }
    public StringProperty noteProperty() {
        return additionNote;
    }
    public StringProperty DescriptionProperty() {
        return description;
    }

    public void setExperimentName(String newValue) {
        experiment.setExperimentName(newValue);
    }
    public void setCreatorName(String newValue) {
        experiment.setCreatorName(newValue);
    }
    public void setDescription(String newValue) {
        experiment.setDescription(newValue);
    }


    public void setNote(String newValue) {
        experiment.setNote(newValue);
    }
    public String getExperimentName(){
        return experimentName.get();
    }
    public String getCreatorName(){
        return creatorName.get();
    }
    public String getNote(){
        return additionNote.get();
    }
    public String getDescription(){
        return description.get();
    }


    public ListProperty<Experiment> itemsProperty() {
        return items;
    }
    public void saveItems() throws Exception {
        ExperimentList.addExperiment(experiment);
        DataAccess.saveNewExperiment(experiment);
        //Notification.notify(experiment);


    }

    @Override
    public void update() {
        items.setAll(ExperimentList.getInstance());
    }
}