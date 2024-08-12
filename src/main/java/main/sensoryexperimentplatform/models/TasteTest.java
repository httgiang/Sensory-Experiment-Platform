package main.sensoryexperimentplatform.models;

import java.util.ArrayList;
import java.util.Arrays;

public class TasteTest {
    private final String question;
    private String consumptionInstruction;
    private String endInStruction;
    private ArrayList<String> foods;
    private ArrayList<String> foodsShow;

    private final ArrayList<Object> list;
    private final Notice initialNotice;
    private int timeWait;
    boolean randomizeFood, randomizeRatingVas, randomizeRatingGLMS;
    private ArrayList<String> vasList;
    private ArrayList<String> glmsList;
    private ArrayList<String> vasListShow;
    private ArrayList<String> glmsListShow;
    private final Vas sampleVas;
    private final gLMS sampleGLMS;
    private Periodic periodic;
    public void setPeriodic (Periodic periodic){
        this.periodic = periodic;
    }

    public ArrayList<String> getVasListShow() {
        return vasListShow;
    }

    public ArrayList<String> getGlmsListShow() {
        return glmsListShow;
    }

    public ArrayList<String> getVasList() {
        return vasList;
    }

    public ArrayList<String> getGlmsList() {
        return glmsList;
    }

    public Vas returnsampleVas(){
        return sampleVas;
    }
    public gLMS getSampleGLMS(){
        return sampleGLMS;
    }

    public boolean isRandomizeFood() {
        return randomizeFood;
    }

    public boolean isRandomizeRatingVas() {
        return randomizeRatingVas;
    }

    public boolean isRandomizeRatingGLMS() {
        return randomizeRatingGLMS;
    }

    public void setRandomizeRatingGLMS(boolean randomizeRatingGLMS) {
        this.randomizeRatingGLMS = randomizeRatingGLMS;
    }

    public void setRandomizeRatingVas(boolean randomizeRatingVas) {
        this.randomizeRatingVas = randomizeRatingVas;
    }

    public void setRandomizeFood(boolean randomizeFood) {
        this.randomizeFood = randomizeFood;
    }

    public TasteTest(String noticeStageContent, String consumptionInstruction, String question,
                     String lowAnchorText, String highAnchorText, int lowAnchorValue,
                     int highAnchorValue, String buttonText,
                     boolean isSwap, String helpText, String endInstruction,
                     int timeWait, boolean randomizeFood, boolean randomizeRatingVas, boolean randomizeRatingGLMS){

        this.initialNotice = new Notice("Taste Test",noticeStageContent, buttonText, "", false);
        initialNotice.setContent(noticeStageContent);
        this.sampleVas = new Vas(question, lowAnchorText, highAnchorText, lowAnchorValue, highAnchorValue,
                "Next", "",helpText,isSwap,false);
        this.sampleGLMS = new gLMS(question,"Next","","",false);
        this.question = question;
        this.consumptionInstruction = consumptionInstruction;
        this.endInStruction = endInstruction;
        this.timeWait = timeWait;
        this.randomizeFood =  randomizeFood;
        this.randomizeRatingVas = randomizeRatingVas;
        this.randomizeRatingGLMS = randomizeRatingGLMS;
        foodsShow = new ArrayList<>();
        foodsShow.add("Biscuits");
        foodsShow.add("Cake");
        foodsShow.add("Cereal");
        foodsShow.add("Cheese");
        foodsShow.add("Chocolate");
        foodsShow.add("Crisps");
        foodsShow.add("Ice Cream");
        foodsShow.add("Pasta");
        foodsShow.add("Porridge");
        foodsShow.add("Sandwiches");
        foodsShow.add("Soup");
        foodsShow.add("Tomatoes");
        foodsShow.add("Yoghurt");
        foods = new ArrayList<>();
        list = new ArrayList<>();
        vasList = new ArrayList<>();
        glmsList = new ArrayList<>();
        vasListShow = new ArrayList<>();
        glmsListShow = new ArrayList<>();
        vasListShow.add("Alcoholic");
        vasListShow.add("Bitter");
        vasListShow.add("Creamy");
        vasListShow.add("Fruity");
        vasListShow.add("Novel");
        vasListShow.add("Pleasant");
        vasListShow.add("Salty");
        vasListShow.add("Savoury");
        vasListShow.add("Sour");
        vasListShow.add("Spicy");
        vasListShow.add("Strong");
        vasListShow.add("Sweet");
        glmsListShow.add("Alcoholic");
        glmsListShow.add("Bitter");
        glmsListShow.add("Creamy");
        glmsListShow.add("Fruity");
        glmsListShow.add("Novel");
        glmsListShow.add("Pleasant");
        glmsListShow.add("Salty");
        glmsListShow.add("Savoury");
        glmsListShow.add("Sour");
        glmsListShow.add("Spicy");
        glmsListShow.add("Strong");
        glmsListShow.add("Sweet");
        list.add(initialNotice);

    }

    public ArrayList<String> getFoodsShow() {
        return foodsShow;
    }
    public void addFoodShow (String food){
        foodsShow.add(food);
    }


    public void setEndInStruction(String endInStruction) {
        this.endInStruction = endInStruction;
    }

    public String getEndInStruction() {
        return endInStruction;
    }

    public void selectVas(String[] selectedVas){
        vasList.addAll(Arrays.asList(selectedVas));
    }
    public void selectgLMS(String[] selectedGLMS){
        glmsList.addAll(Arrays.asList(selectedGLMS));
    }

    public void addFood(String[] foods){
        this.foods.addAll(Arrays.asList(foods));
    }

    public int getTimeWait() {
        return timeWait;
    }
    public void setTime(int time){
        this.timeWait = time;
    }

    public ArrayList<String> getFoods() {
        return foods;
    }

    //op: Arraylist<Object>
    public void generator(){
        for(String foodName : foods){
            String convertedConsumption = consumptionInstruction.replace("<food>","%s");
            String consumption = String.format(convertedConsumption, foodName);

            Notice notice1 = new Notice("Taste Test",consumption, initialNotice.getButtonText(), "",false);
            notice1.setContent(consumption);
            list.add(notice1);

            Food currentFood = new Food(foodName,randomizeRatingVas,randomizeRatingGLMS,timeWait);
            for(String taste : vasList){
                Vas temp = getVas(foodName, taste);
                currentFood.addVasRating(temp);
            }
            for(String taste : glmsList){
                gLMS temp = getGLMS(foodName,taste);
                currentFood.addGlmsRating(temp);
            }
            list.add(currentFood);
            Notice notice2 = new Notice("Rinsing", endInStruction, initialNotice.getButtonText(), "", false);
            list.add(notice2);
            }
        }
    private gLMS getGLMS(String foodName, String taste){
        String convertedQuestion = question.replace("<taste>","%s").replace("<food>","%s");
        convertedQuestion = String.format(convertedQuestion, taste, foodName);

        return new gLMS(convertedQuestion, sampleGLMS.getButtonText(), sampleGLMS.getContent(),
                sampleGLMS.getHelpText(), sampleGLMS.getAlert());
    }
    private Vas getVas(String foodName, String taste) {
        String convertedQuestion = question.replace("<taste>","%s").replace("<food>","%s");
        convertedQuestion = String.format(convertedQuestion, taste, foodName);

        String convertedLowAnchor = sampleVas.getLowAnchorText().replace("<taste>","%s");
        convertedLowAnchor = String.format(convertedLowAnchor, taste);

        String convertedHighAnchor = sampleVas.getHighAnchorText().replace("<taste>","%s");
        convertedHighAnchor = String.format(convertedHighAnchor, taste);

        return new Vas(convertedQuestion, convertedLowAnchor,convertedHighAnchor,
                sampleVas.getLowAnchorValue(), sampleVas.getHighAnchorValue(), sampleVas.getButtonText(),
                sampleVas.getContent(),sampleVas.getHelpText(), sampleVas.getIsSwap(), sampleVas.getAlert());
    }
    public String getType(){
        return "Taste Test";
    }

    public String getConsumptionInstruction() {
        return consumptionInstruction;
    }

    public void setConsumptionInstruction(String consumptionInstruction) {
        this.consumptionInstruction = consumptionInstruction;
    }

    public String toString(){
            StringBuilder stringBuilder = new StringBuilder();
            for(Object o : list){
                stringBuilder.append(o.toString()).append("\n");
            }return stringBuilder.toString();
        }

}
