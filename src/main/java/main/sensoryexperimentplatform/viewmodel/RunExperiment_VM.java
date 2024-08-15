package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.sensoryexperimentplatform.models.*;
import main.sensoryexperimentplatform.models.Timer;

import java.util.*;

public class RunExperiment_VM {
    private Experiment experiment;

    public double count = 0.0;

    private String uid;

    private ListProperty<String> items;

    private Map<String, Object> objectsMap;

    private List<Object> objectList;

    private ArrayList<Object> stages;

    private Set<String> stringSet;

    public RunExperiment_VM(Experiment e, String uid){
        this.experiment = e;
        this.uid = uid;
        items = new SimpleListProperty<>(FXCollections.observableArrayList());
        objectsMap = new HashMap<>();
        objectList = new ArrayList<>();
        stages = e.getStages();
        stringSet = new LinkedHashSet<>();
        loadItems();
    }

    public String getUid() {
        return uid;
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
                    else if (o instanceof Input)
                        temp = "inputStage";
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
            if(o instanceof TasteTest){
                for(Object subO : ((TasteTest) o).getStages()){
                    int i = 0;
                    if (subO instanceof Notice) {
                        String subKey = "\t" + index + "." + i;
                        objectsMap.put(subKey, subO);
                        stringSet.add(subKey);
                        objectList.add(subO); // Thêm đối tượng con vào objectList
                        i++;
                        count++;
                    }
                    if (subO instanceof RatingContainer) {
                        int j = 0;
                        for (Object containerObject : ((RatingContainer) o).container) {
                            if (containerObject instanceof Vas) {
                                String subKey = "\t" + i + "." + j;
                                objectsMap.put(subKey, containerObject);
                                stringSet.add(subKey);
                                objectList.add(containerObject); // Thêm đối tượng con vào objectList
                                j++;
                                count++;
                            }
                            if (containerObject instanceof gLMS) {
                                String subKey = "\t" + i + "." + j;
                                objectsMap.put(subKey, containerObject);
                                stringSet.add(subKey);
                                objectList.add(containerObject); // Thêm đối tượng con vào objectList
                                j++;
                                count++;
                            }
                        }
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