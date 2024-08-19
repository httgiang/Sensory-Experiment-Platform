package main.sensoryexperimentplatform.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class TasteTest extends ModelContainer{
    private Notice initialNotice;
    private String noticeStageContent, consumptionInstruction, question, endInstruction;
    private String lowAnchorText, highAnchorText, buttonText, helpText;
    private int lowAnchorValue, highAnchorValue, timeWait;
    boolean isSwap, isRandomizedFood, isRandomizedRatings, isAlert;
    private Vas sampleVas; private gLMS sampleGLMS;

    //THREE LISTS CONTAINING DEFAULT OPTIONS TO BE CHOSEN
    private ObservableList<String> foodOptions, vasOptions, gLMSOptions;

    //THREE LISTS CONTAINING THE SELECTED FOOD
    private ObservableList<String> selectedFoods = FXCollections.observableArrayList();
    private ObservableList<String> selectedGLMS = FXCollections.observableArrayList();
    private ObservableList<String> selectedVAS = FXCollections.observableArrayList();
  


    public TasteTest(String noticeStageContent, String question, String consumptionInstruction, String endInstruction,
                     String lowAnchorText, String highAnchorText, String buttonText, String helpText,
                     int lowAnchorValue, int highAnchorValue, boolean isSwap,
                     boolean isRandomizedFood, boolean isRandomizedRatings, int timeWait, boolean isAlert){

        this.noticeStageContent = noticeStageContent;
        //THE NOTICE STAGE AT THE BEGINNING OF THE TASTE TEST
        this.initialNotice = new Notice("Taste Test", noticeStageContent, buttonText, "", false);
        children = new ArrayList<>();
        children.add(initialNotice);

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
        this.buttonText = buttonText;
        this.helpText = helpText;
        this.timeWait = timeWait;
        this.isRandomizedFood = isRandomizedFood;
        this.isRandomizedRatings = isRandomizedRatings;
        this.isAlert = isAlert;

        //foods = new ArrayList<>();
    }
    public TasteTest(TasteTest tasteTest){
        this.noticeStageContent = tasteTest.noticeStageContent;
        //THE NOTICE STAGE AT THE BEGINNING OF THE TASTE TEST
        this.initialNotice = new Notice("Taste Test", noticeStageContent, buttonText, "", false);
        children = new ArrayList<>();
        children.add(initialNotice);

        //THE WORDING FOR PARTICIPANT TO DO WITH EACH SAMPLE
        this.consumptionInstruction = consumptionInstruction;

        //VAS AND GLMS
        this.question = tasteTest.question;
        this.lowAnchorText = tasteTest.lowAnchorText;
        this.highAnchorText = tasteTest.highAnchorText;
        this.lowAnchorValue = tasteTest.lowAnchorValue;
        this.highAnchorValue = tasteTest.highAnchorValue;
        this.isSwap = tasteTest.isSwap;

        this.sampleVas = new Vas(question, lowAnchorText, highAnchorText, lowAnchorValue, highAnchorValue,
                "Next", "",helpText,isSwap,isAlert);
        this.sampleGLMS = new gLMS(question,"Next","","",isAlert);

        //THE QUESTION FOR VAS AND GLMS WITH VARIABLE <food> AND <taste>
        this.endInstruction = tasteTest.endInstruction;
        this.timeWait = tasteTest.timeWait;
        this.isRandomizedFood = tasteTest.isRandomizedFood;
        this.isRandomizedRatings = tasteTest.isRandomizedRatings;
        this.isAlert = tasteTest.isAlert;

        this.children = tasteTest.getChildren();
        generateTasteTest();
    }


    private void initFoodOptions(){
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
        for(String food : selectedFoods){
            String consumptionInst = convertConsumptionInstruction(food);
            Notice subNotice = new Notice("Taste Test",consumptionInst, initialNotice.getButtonText(), "",false);
            children.add(subNotice);
            for(String taste : selectedVAS){
                Vas vas = getVas(food, taste);
                children.add(vas);
            }
            for(String taste : selectedGLMS){
                gLMS glms = getGLMS(food,taste);
               // currFood.addGlmsRating(glms);
                children.add(glms);
            }

            Notice endNotice = new Notice("Rinsing", endInstruction, initialNotice.getButtonText(), "", false);
            children.add(endNotice);
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


    public String serializeTasteTest(StringBuilder builder) {
        String foodOptionsString = (foodOptions != null) ? String.join(",", foodOptions) : "";
        String vasOptionsString = (vasOptions != null) ? String.join(",", vasOptions) : "";
        String gLMSOptionsString = (gLMSOptions != null) ? String.join(",", gLMSOptions) : "";
        String selectedFoodsString = (selectedFoods != null) ? String.join(",", selectedFoods) : "";
        String selectedVASString = (selectedVAS != null) ? String.join(",", selectedVAS) : "";
        String selectedGLMSString = (selectedGLMS != null) ? String.join(",", selectedGLMS) : "";

        // Serialize the basic information of the TasteTest
        builder.append("tasteTest(\"")
                .append(noticeStageContent).append("\",\"")
                .append(question).append("\",\"")
                .append(consumptionInstruction).append("\",\"")
                .append(endInstruction).append("\",\"")

                .append(lowAnchorText).append("\",\"")
                .append(highAnchorText).append("\",\"")
                .append(buttonText).append("\",\"")

                .append(helpText).append("\",\"")
                .append(lowAnchorValue).append("\",\"")
                .append(highAnchorValue).append("\",\"")

                .append(isSwap).append("\",\"")

                .append(isRandomizedFood).append("\",\"")
                .append(isRandomizedRatings).append("\",\"")
                .append(timeWait).append("\",\"")
                .append(isAlert).append("\",\"")

                .append("{").append(foodOptionsString).append("}\",\"")
                .append("{").append(vasOptionsString).append("}\",\"")
                .append("{").append(gLMSOptionsString).append("}\",\"")

                .append("{").append(selectedFoodsString).append("}\",\"")
                .append("{").append(selectedVASString).append("}\",\"")
                .append("{").append(selectedGLMSString).append("}\")");
//                .append("{").append(String.join(",", foodOptions)).append("}\",\"")
//                .append("{").append(String.join(",", vasOptions)).append("}\",\"")
//                .append("{").append(String.join(",", gLMSOptions)).append("}\",\"")
//
//                .append("{").append(String.join(",", selectedFoods)).append("}\",\"")
//                .append("{").append(String.join(",", selectedVAS)).append("}\",\"")
//                .append("{").append(String.join(",", selectedGLMS)).append("}\")");



        return builder.toString();
    }
    @Override
    public String toString(){

        StringBuilder builder = new StringBuilder();
        return serializeTasteTest(builder);
    }
    public ArrayList<Model> getChildren() {
        return children;
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

    public int getTimeWait() {
        return timeWait;
    }
    public void setTime(int time){
        this.timeWait = time;
    }

    public ObservableList<String> getFoodOptions() {
        if (foodOptions == null) {
            foodOptions = FXCollections.observableArrayList();
            initFoodOptions();
        }
        return foodOptions;
    }


    public void setFoodOptions(ObservableList<String> foodOptions) {
        this.foodOptions = foodOptions;
    }

    public ObservableList<String> getVasOptions() {
        if (vasOptions == null) {
            vasOptions = FXCollections.observableArrayList();
            initVASOptions();
        }
        return vasOptions;
    }

    public void setVasOptions(ObservableList<String> vasOptions) {
        this.vasOptions = vasOptions;
    }

    public ObservableList<String> getGLMSOptions() {
        if (gLMSOptions == null) {
            gLMSOptions = FXCollections.observableArrayList();
            initGLMSOptions();
        }
        return gLMSOptions;
    }
    public void addFoodOptions(String food){
        if(!getFoodOptions().contains(food)){
            getFoodOptions().add(food);
        }
    }

    public void addVASOptions(String vas){
        if(!getVasOptions().contains(vas)){
            getVasOptions().add(vas);
        }
    }

    public void addGLMSOptions(String gLMS){
        if(!getGLMSOptions().contains(gLMS)){
            getGLMSOptions().add(gLMS);
        }
    }
    public void setGLMSOptions(ObservableList<String> gLMSOptions) {
        this.gLMSOptions = gLMSOptions;
    }

    public ObservableList<String> getSelectedVAS() {
        return selectedVAS;
    }


    public ObservableList<String> getSelectedGLMS() {
        return selectedGLMS;
    }

    public ObservableList<String> getSelectedFoods() {
        return selectedFoods;
    }

    public void setSelectedFoods(ObservableList<String> selectedFoods) {
        this.selectedFoods = selectedFoods;
    }

    public void setSelectedGLMS(ObservableList<String> selectedGLMS) {
        this.selectedGLMS = selectedGLMS;
    }

    public void setSelectedVAS(ObservableList<String> selectedVAS) {
        this.selectedVAS = selectedVAS;
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
