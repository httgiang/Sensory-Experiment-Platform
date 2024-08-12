package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.sensoryexperimentplatform.models.*;
import main.sensoryexperimentplatform.models.Timer;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;

public class RunExperiment_VM {
    private Experiment experiment;

    public double count = 0.0;

    private String file_name;

    private ListProperty<String> items;

    private Map<String, Object> objectsMap;

    private List<Object> objectList;

    private ArrayList<Object> stages;

    private Set<String> stringSet;

    public RunExperiment_VM(Experiment e, String file_name){
        this.experiment = e;
        this.file_name = file_name;
        items = new SimpleListProperty<>(FXCollections.observableArrayList());
        objectsMap = new HashMap<>();
        objectList = new ArrayList<>();
        stages = e.getStages();
        stringSet = new LinkedHashSet<>();
        loadItems();
    }

    public String getFileName() {
        return file_name;
    }

    public void loadItems() {
        stringSet = new LinkedHashSet<>();
        int index = 0;
        for (Object o : stages) {
            if (o instanceof Stage) {
                if (o instanceof Timer) {
                    String key = index + " Wait";
                    objectsMap.put(key, o);
                    stringSet.add(key);
                    objectList.add(o); // Thêm đối tượng vào objectList
                    index++;
                    count++;
                } else {
                    String temp = " ";
                    if (o instanceof Vas)
                        temp = "Vas";
                    else if (o instanceof gLMS)
                        temp = "GLMS";
                    else if (o instanceof Notice)
                        temp = "Notice";
                    else if (o instanceof AudibleInstruction)
                        temp = "audio";
                    else if (o instanceof Question)
                        temp = "questionStage";
                    else if (o instanceof Start)
                        temp = "Start";

                    String key = index + " " + temp;
                    objectsMap.put(key, o);
                    stringSet.add(key);
                    objectList.add(o);
                    index++;
                    count++;
                }
            }
            if (o instanceof RatingContainer) {
                int i = 0;
                for (Object subO : ((RatingContainer) o).container) {
                    if (subO instanceof Vas) {
                        String subKey = "\t" + index + "." + i;
                        objectsMap.put(subKey, subO);
                        stringSet.add(subKey);
                        objectList.add(subO); // Thêm đối tượng con vào objectList
                        i++;
                        count++;
                    }
                    if (subO instanceof gLMS) {
                        String subKey = "\t" + index + "." + i;
                        objectsMap.put(subKey, subO);
                        stringSet.add(subKey);
                        objectList.add(subO); // Thêm đối tượng con vào objectList
                        i++;
                        count++;
                    }
                }
            }
        }
        items.setAll(stringSet);
    }
    public ObservableList<String> getItems() {
        return items.get();
    }

    public ListProperty<String> itemsProperty() {
        return items;
    }

    public Object getObjectByKey(String key) {
        return objectsMap.get(key);
    }

    public int getIndexOfObject(Object obj) {
        return objectList.indexOf(obj);
    }
    public Experiment getExperiment(){
        return experiment;
    }

}