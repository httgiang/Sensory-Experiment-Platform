package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import main.sensoryexperimentplatform.models.*;

import java.util.ArrayList;

public class FoodTasteVM {
    private TasteTest tasteTest;


    //THE LISTS OF DISPLAYED OPTIONS FOR FOOD AND TASTE
    private ListProperty<String> foodOptions;
    private ListProperty<String> gLMSOptions;
    private ListProperty<String> vasOptions;



    //THE LIST OF SELECTIONS OF USER
    private ListProperty<String> selectedFoods;
    private ListProperty<String>  selectedVAS;
    private ListProperty<String> selectedGLMS;


    public FoodTasteVM(TasteTest tasteTest){
        this.tasteTest = tasteTest;
        foodOptions = new SimpleListProperty<>(tasteTest.getFoodOptions());
        gLMSOptions = new SimpleListProperty<>(tasteTest.getGLMSOptions());
        vasOptions = new SimpleListProperty<>(tasteTest.getVasOptions());

        selectedFoods = new SimpleListProperty<>(tasteTest.getSelectedFoods());
        selectedVAS = new SimpleListProperty<>(tasteTest.getSelectedVAS());
        selectedGLMS = new SimpleListProperty<>(tasteTest.getSelectedGLMS());

        initBinding();
    }
    private void initBinding(){
        foodOptions.addListener((observableValue, oldValue, newValue) -> changeFoodOptions(newValue));
        gLMSOptions.addListener((observableValue, oldValue, newValue) -> changeGLMSOptions(newValue));
        vasOptions.addListener((observableValue, oldValue, newValue) -> changeVASOptions(newValue));
        selectedFoods.addListener((observableValue, oldValue, newValue) -> changeSelectedFoods(newValue));
        selectedVAS.addListener((observableValue, oldValue, newValue) -> changeSelectedVAS(newValue));
        selectedGLMS.addListener((observableValue, oldValue, newValue) -> changeSelectedGLMS(newValue));
    }

    private void changeVASOptions(ObservableList<String> newValue) {
        tasteTest.setVasOptions(newValue);
    }

    private void changeGLMSOptions(ObservableList<String> newValue) {
        tasteTest.setGLMSOptions(newValue);
    }

    private void changeFoodOptions(ObservableList<String> newValue) {
        tasteTest.setFoodOptions(newValue);
    }

    private void changeSelectedGLMS(ObservableList<String> newValue) {
        tasteTest.setSelectedGLMS(newValue);
    }

    private void changeSelectedVAS(ObservableList<String> newValue) {
        tasteTest.setSelectedVAS(newValue);
    }

    private void changeSelectedFoods(ObservableList<String> newValue) {
        tasteTest.setSelectedFoods(newValue);
    }

    public ObservableList<String> getFoodOptions() {
        return foodOptions.get();
    }

    public ListProperty<String> foodOptionsProperty() {
        return foodOptions;
    }

    public ObservableList<String> getgLMSOptions() {
        return gLMSOptions.get();
    }

    public ListProperty<String> gLMSOptionsProperty() {
        return gLMSOptions;
    }

    public ObservableList<String> getVasOptions() {
        return vasOptions.get();
    }

    public ListProperty<String> vasOptionsProperty() {
        return vasOptions;
    }

    public ObservableList<String> getSelectedFoods() {
        return selectedFoods.get();
    }

    public ListProperty<String> selectedFoodsProperty() {
        return selectedFoods;
    }

    public ObservableList<String> getSelectedVAS() {
        return selectedVAS.get();
    }

    public ListProperty<String> selectedVASProperty() {
        return selectedVAS;
    }

    public ObservableList<String> getSelectedGLMS() {
        return selectedGLMS.get();
    }

    public ListProperty<String> selectedGLMSProperty() {
        return selectedGLMS;
    }


    public void addFoodOptions(String food) {
        foodOptions.add(food);
    }
    public void addVASOptions(String vasRatings){vasOptions.add(vasRatings);}
    public void addGLMSOptions(String gLMSRatings)
        {gLMSOptions.add(gLMSRatings);}

//    public void addSelectedFoods(String food) {
//        if(!selectedFoods.contains(food)){
//            selectedFoods.add(food);
//            tasteTest.getSelectedFoods().add(food);
//        }
//
//    }
//    public void addSelectedVAS(String tasteVas){
//        if(!selectedVAS.contains(tasteVas)){
//            selectedVAS.add(tasteVas);
//            tasteTest.getSelectedVAS().add(tasteVas);
//        }
//    }
//    public void addSelectedGLMS(String tasteGLMS){
//        if(!selectedGLMS.contains(tasteGLMS)){
//            selectedGLMS.add(tasteGLMS);
//            tasteTest.getSelectedGLMS().add(tasteGLMS);
//        }
//    }

    public TasteTest getTasteTest() {
        return tasteTest;
    }
}
