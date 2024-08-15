package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.*;

import java.util.ArrayList;

public class FoodTasteVM {
    private TasteTest tasteTest;


    //THE LISTS OF DISPLAYED OPTIONS FOR FOOD AND TASTE
    private ArrayList<String> foodOptions;
    private ArrayList<String> gLMSOptions;
    private ArrayList<String> vasOptions;



    //THE LIST OF SELECTIONS OF USER
    private ArrayList<String> selectedFoods;
    private ArrayList<String> selectedVAS;
    private ArrayList<String> selectedGLMS;


    public FoodTasteVM(TasteTest tasteTest){
        this.tasteTest = tasteTest;
        foodOptions = tasteTest.getFoodOptions();
        gLMSOptions = tasteTest.getGLMSOptions();
        vasOptions = tasteTest.getVasOptions();

        selectedFoods = tasteTest.getSelectedFoods();
        selectedVAS = tasteTest.getSelectedVAS();
        selectedGLMS = tasteTest.getSelectedGLMS();
    }

    public ArrayList<String> getSelectedFoods() {
        return selectedFoods;
    }

    public ArrayList<String> getSelectedVAS() {
        return selectedVAS;
    }

    public ArrayList<String> getSelectedGLMS() {
        return selectedGLMS;
    }

    public ArrayList<String> getGLMSOptions() {
        return gLMSOptions;
    }
    public ArrayList<String> getVASOptions() {
        return vasOptions;
    }

    public ArrayList<String> getFoodOptions() {
        return foodOptions;
    }

    public void addFoodOptions(String food) {
        foodOptions.add(food);
        tasteTest.getFoodOptions().add(food);
    }
    public void addVASOptions(String food){vasOptions.add(food);
        tasteTest.getVasOptions().add(food);}
    public void addGLMSOptions(String food){gLMSOptions.add(food);
        tasteTest.getGLMSOptions().add(food);}

    public void addSelectedFoods(String food) {
        if(!selectedFoods.contains(food)){
            selectedFoods.add(food);
            tasteTest.getSelectedFoods().add(food);
        }

    }
    public void addSelectedVAS(String tasteVas){
        if(!selectedVAS.contains(tasteVas)){
            selectedVAS.add(tasteVas);
            tasteTest.getSelectedVAS().add(tasteVas);
        }
    }
    public void addSelectedGLMS(String tasteGLMS){
        if(!selectedGLMS.contains(tasteGLMS)){
            selectedGLMS.add(tasteGLMS);
            tasteTest.getSelectedGLMS().add(tasteGLMS);
        }
    }

    public TasteTest getTasteTest() {
        return tasteTest;
    }
}
