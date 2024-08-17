package main.sensoryexperimentplatform.models;

import java.util.ArrayList;
import java.util.Collections;

public class RatingContainer {
    private boolean isRandomize;
    private int minTime;
    public ArrayList<containerObject> container;
    public RatingContainer(boolean isRandomize,int minTime){
        this.minTime = minTime;
        this.isRandomize = isRandomize;
        container = new ArrayList<>();
    }
    public RatingContainer(RatingContainer other){
        this.isRandomize = other.isRandomize;
        this.minTime = other.minTime;
        container = new ArrayList<>();
        for (Object subO : other.container) {
            if (subO instanceof Vas) {
                Vas temp = new Vas((Vas) subO);
                addStage(temp);
            }
            if (subO instanceof gLMS) {
                gLMS temp = new gLMS((gLMS) subO);
                addStage(temp);
            }
        }
    }
    public void randomizeContainer(){
        if(isRandomize){
            Collections.shuffle(container);
        }

    }

    public void addStage(containerObject s){
        container.add(s);
    }
    public void addVasStageTest_newExperiment(Vas stage){
        container.add(stage);
    }
    public void addGlms_newExperiment(gLMS glms){
        container.add(glms);
    }
    public void addVasStageContainer(String title, String lowAnchorText, String highAnchorText,
                                     int lowAnchorValue, int highAnchorValue, String buttonText,
                                     String content, String helpText, boolean isSwap, boolean alert){

        Vas stage = new Vas(title, lowAnchorText, highAnchorText,
                lowAnchorValue, highAnchorValue, buttonText, content,
                helpText, isSwap, alert);

        container.add(stage);
    }

    public void addGlmsStageContainer(String question, String buttonText, String content,
                                      String helpText, boolean alert){

        gLMS stage = new gLMS(question, buttonText, content, helpText, alert);

        container.add(stage);
    }

    public void countMinTime(){
        Timer timer = new Timer("Minimum Time is",String.valueOf(minTime));
        timer.start();
    }
    public void showContainer(){
        ArrayList<Object> show = new ArrayList<>(container);
        if (isRandomize){
            Collections.shuffle(show);
            StringBuilder sb = new StringBuilder();
            for(Object o : show){
                sb.append(o.toString()).append("\n");
            }
            System.out.println(sb);
        }
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    public boolean isRandomize() {
        return isRandomize;
    }

    public void setRandomize(boolean randomize) {
        isRandomize = randomize;
    }

    public int getMinTime() {
        return minTime;
    }

    public void setContainer(ArrayList<containerObject> container) {
        this.container = container;
    }

    public ArrayList<containerObject> getContainer() {
        return container;
    }
    public String stageToString(){
        StringBuilder sb = new StringBuilder();
        for(Object s : container){
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
    public String getType(){
        return "Rating Container";
    }
    public String toString(){
        return "ratingsContainer(\"" + isRandomize + "\",\"" + minTime +"\")\n" +
                stageToString()+"endRatingsContainer()";

    }


}