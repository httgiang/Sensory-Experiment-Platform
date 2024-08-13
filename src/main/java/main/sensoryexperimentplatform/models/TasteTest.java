package main.sensoryexperimentplatform.models;

import java.util.ArrayList;
import java.util.Arrays;

public class TasteTest{
    private Notice initialNotice;
    private String noticeStageContent, consumptionInstruction, question, endInstruction;
    private String lowAnchorText, highAnchorText, buttonText, helpText;
    private int lowAnchorValue, highAnchorValue;
    private int timeWait;
    boolean isSwap, isRandomizedFood, isRandomizedRatings, isAlert;
    private Vas sampleVas; private gLMS sampleGLMS;

    //THREE LISTS CONTAINING DEFAULT OPTIONS TO BE CHOSEN
    private ArrayList<String> foodOptions, vasOptions, gLMSOptions;

    //THREE LISTS CONTAINING THE SELECTED FOOD
    private ArrayList<String> selectedFoods, selectedVAS, selectedGLMS;
    private ArrayList<String> foods;
    private ArrayList<Object> stages;

    public TasteTest(String noticeStageContent, String consumptionInstruction, String question,
                         String lowAnchorText, String highAnchorText, int lowAnchorValue,
                         int highAnchorValue, String buttonText,
                         boolean isSwap, String helpText, String endInstruction,
                         int timeWait, boolean isRandomizedFood, boolean isRandomizedRatings, boolean isAlert){

        this.noticeStageContent = noticeStageContent;
        //THE NOTICE STAGE AT THE BEGINNING OF THE TASTE TEST
        this.initialNotice = new Notice("Taste Test", noticeStageContent, buttonText, "", false);
        stages = new ArrayList<>();
        stages.add(initialNotice);

        //THE WORDING FOR PARTICIPANT TO DO WITH EACH SAMPLE
        this.consumptionInstruction = consumptionInstruction;

        //VAS AND GLMS
        this.question = question;
        this.lowAnchorText = lowAnchorText;
        this.highAnchorText = highAnchorText;
        this.lowAnchorValue = lowAnchorValue;
        this.highAnchorValue = highAnchorValue;
        this.isSwap = isSwap;

        this.sampleVas = new Vas(question, lowAnchorText, highAnchorText, lowAnchorValue, highAnchorValue,
                "Next", "",helpText,isSwap,isAlert);
        this.sampleGLMS = new gLMS(question,"Next","","",isAlert);

        //THE QUESTION FOR VAS AND GLMS WITH VARIABLE <food> AND <taste>
        this.endInstruction = endInstruction;
        this.timeWait = timeWait;
        this.isRandomizedFood = isRandomizedFood;
        this.isRandomizedRatings = isRandomizedRatings;
        this.isAlert = isAlert;

        initFoodOptions();
        initGLMSOptions();
        initVASOptions();

        foods = new ArrayList<>();
        selectedVAS = new ArrayList<>();
        selectedGLMS = new ArrayList<>();

    }


    private void initFoodOptions(){
        foodOptions = new ArrayList<>();
        foodOptions.add("Biscuits");
        foodOptions.add("Cake");
        foodOptions.add("Cereal");
        foodOptions.add("Cheese");
        foodOptions.add("Chocolate");
        foodOptions.add("Crisps");
        foodOptions.add("Ice Cream");
        foodOptions.add("Pasta");
        foodOptions.add("Porridge");
        foodOptions.add("Sandwiches");
        foodOptions.add("Soup");
        foodOptions.add("Tomatoes");
        foodOptions.add("Yoghurt");
    }

    private void initVASOptions(){
        vasOptions = new ArrayList<>();
        vasOptions.add("Alcoholic");
        vasOptions.add("Bitter");
        vasOptions.add("Creamy");
        vasOptions.add("Fruity");
        vasOptions.add("Novel");
        vasOptions.add("Pleasant");
        vasOptions.add("Salty");
        vasOptions.add("Savoury");
        vasOptions.add("Sour");
        vasOptions.add("Spicy");
        vasOptions.add("Strong");
        vasOptions.add("Sweet");
    }

    private void initGLMSOptions(){
        gLMSOptions = new ArrayList<>();
        gLMSOptions.add("Alcoholic");
        gLMSOptions.add("Bitter");
        gLMSOptions.add("Creamy");
        gLMSOptions.add("Fruity");
        gLMSOptions.add("Novel");
        gLMSOptions.add("Pleasant");
        gLMSOptions.add("Salty");
        gLMSOptions.add("Savoury");
        gLMSOptions.add("Sour");
        gLMSOptions.add("Spicy");
        gLMSOptions.add("Strong");
        gLMSOptions.add("Sweet");
    }

    public void generateTasteTest(){
        for(String food : foods){
            String consumptionInst = convertConsumptionInstruction(food);
            Notice subNotice = new Notice("Taste Test",consumptionInst, initialNotice.getButtonText(), "",false);
            stages.add(subNotice);
            Food currFood = new Food(food, isRandomizedRatings, isRandomizedRatings, timeWait);
            for(String taste : selectedVAS){
                Vas vas = getVas(currFood.getName(), taste);
                currFood.addVasRating(vas);
            }
            for(String taste : selectedGLMS){
                gLMS glms = getGLMS(currFood.getName(),taste);
                currFood.addGlmsRating(glms);
            }
            stages.add(food);
            Notice endNotice = new Notice("Rinsing", endInstruction, initialNotice.getButtonText(), "", false);
            stages.add(endNotice);
        }
    }
    private String convertConsumptionInstruction(String foodName){
        String convertedConsumption = consumptionInstruction.replace("<food>","%s");
        return String.format(convertedConsumption, foodName);
    }

    private gLMS getGLMS(String foodName, String taste){
        //USED TO GENERATE QUESTIONS IN GLMS
        String convertedQuestion = question.replace("<taste>","%s").replace("<food>","%s");
        convertedQuestion = String.format(convertedQuestion, taste, foodName);

        return new gLMS(convertedQuestion, sampleGLMS.getButtonText(), sampleGLMS.getContent(),
                sampleGLMS.getHelpText(), sampleGLMS.getAlert());
    }
    private Vas getVas(String foodName, String taste) {
        //USED TO GENERATE QUESTIONS IN VAS
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

    @Override
    public String toString(){
        return "tasteTest(\"" + noticeStageContent + "\",\"" + consumptionInstruction + "\",\"" +
                question + "\",\"" + lowAnchorText + "\",\"" + highAnchorText + "\",\"" + lowAnchorValue + "\",\""
                + highAnchorValue + "\",\"" + buttonText + "\",\"" + isSwap + "\",\"" + helpText + "\",\"" +
                endInstruction + "\",\"" + timeWait + "\",\"" + isRandomizedFood + "\",\"" + isRandomizedRatings + "\",\"" + isAlert + "\")";
    }

    public ArrayList<Object> getStages() {
        return stages;
    }

    public String getNoticeStageContent() {
        return noticeStageContent;
    }

    public void setNoticeStageContent(String noticeStageContent) {
        this.noticeStageContent = noticeStageContent;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEndInstruction() {
        return endInstruction;
    }

    public boolean isAlert() {
        return isAlert;
    }

    public String getLowAnchorText() {
        return lowAnchorText;
    }

    public void setLowAnchorText(String lowAnchorText) {
        this.lowAnchorText = lowAnchorText;
    }

    public String getHighAnchorText() {
        return highAnchorText;
    }

    public void setHighAnchorText(String highAnchorText) {
        this.highAnchorText = highAnchorText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public int getLowAnchorValue() {
        return lowAnchorValue;
    }

    public void setLowAnchorValue(int lowAnchorValue) {
        this.lowAnchorValue = lowAnchorValue;
    }

    public int getHighAnchorValue() {
        return highAnchorValue;
    }

    public void setHighAnchorValue(int highAnchorValue) {
        this.highAnchorValue = highAnchorValue;
    }

    public void setTimeWait(int timeWait) {
        this.timeWait = timeWait;
    }

    public boolean isSwap() {
        return isSwap;
    }

    public void setSwap(boolean swap) {
        isSwap = swap;
    }

    public boolean isRandomizedRatings() {
        return isRandomizedRatings;
    }

    public void setRandomizedRatings(boolean randomizedRatings) {
        isRandomizedRatings = randomizedRatings;
    }

    public void setSampleVas(Vas sampleVas) {
        this.sampleVas = sampleVas;
    }

    public void setSampleGLMS(gLMS sampleGLMS) {
        this.sampleGLMS = sampleGLMS;
    }

    public String getConsumptionInstruction() {
        return consumptionInstruction;
    }

    public void setEndInstruction(String endInstruction){
        this.endInstruction = endInstruction;
    }

    public ArrayList<String> getFoods() {
        return foods;
    }
    public int getTimeWait() {
        return timeWait;
    }
    public void setTime(int time){
        this.timeWait = time;
    }
    public ArrayList<String> getVasOptions() {
        return vasOptions;
    }

    public ArrayList<String> getGLMSOptions() {
        return gLMSOptions;
    }

    public ArrayList<String> getVasList() {
        return selectedVAS;
    }

    public ArrayList<String> getGlmsList() {
        return selectedGLMS;
    }

    public Vas getSampleVas(){
        return sampleVas;
    }
    public gLMS getSampleGLMS(){
        return sampleGLMS;
    }

    public boolean isRandomizedFood() {
        return isRandomizedFood;
    }



    public void setRandomizedFood(boolean randomizedFood) {
        this.isRandomizedFood = randomizedFood;
    }
    public ArrayList<String> getFoodOptions() {
        return foodOptions;
    }

    public void setConsumptionInstruction(String consumptionInstruction) {
        this.consumptionInstruction = consumptionInstruction;
    }

    public boolean isRandomizeRatings() {
        return isRandomizedRatings;
    }

    public void setRandomizeRatings(Boolean newValue) {
        this.isRandomizedRatings = newValue;
    }

    public void setAlert(Boolean newValue) {
        this.isAlert = newValue;
    }
}
