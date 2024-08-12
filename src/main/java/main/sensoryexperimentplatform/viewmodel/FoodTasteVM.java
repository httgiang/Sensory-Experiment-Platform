package main.sensoryexperimentplatform.viewmodel;

import main.sensoryexperimentplatform.models.TasteTest;

import java.util.ArrayList;

public class FoodTasteVM {
    private TasteTest tasteTest;
    private ArrayList<String> listFoods;
    private ArrayList<String> foodOptions;
    private ArrayList<String> gLMSOptions;
    private ArrayList<String> vasOptions;
    private ArrayList<String> listVas;
    private ArrayList<String> listGLMS;

    public ArrayList<String> getListGLMSShow() {
        return gLMSOptions;
    }
    public ArrayList<String> getListVasShow() {
        return vasOptions;
    }

    public ArrayList<String> getListVas() {
        return listVas;
    }

    public ArrayList<String> getListGLMS() {
        return listGLMS;
    }

    public FoodTasteVM(TasteTest tasteTest){
        this.tasteTest = tasteTest;
        listFoods = tasteTest.getFoods();
        foodOptions = tasteTest.getFoodsShow();
        listVas = tasteTest.getVasList();
        gLMSOptions = tasteTest.getGlmsListShow();
        vasOptions = tasteTest.getVasListShow();
        listGLMS = tasteTest.getGlmsList();
    }

    public ArrayList<String> getListFoodsShow() {
        return foodOptions;
    }

    public void addListFoodsShow(String food) {
        foodOptions.add(food);
        tasteTest.getFoodsShow().add(food);
    }
    public void addVasShow(String food){vasOptions.add(food);
        tasteTest.getVasListShow().add(food);}
    public void addGLMSShow (String food){gLMSOptions.add(food);
        tasteTest.getGlmsListShow().add(food);}

    public ArrayList<String> getListFoods() {
        return listFoods;
    }

    public void addListFoods(String food) {
        listFoods.add(food);
        tasteTest.getFoods().add(food);
    }
    public void addVas(String food){
        listVas.add(food);
    }
    public void addGLMS(String food){
        listGLMS.add(food);
    }

}
