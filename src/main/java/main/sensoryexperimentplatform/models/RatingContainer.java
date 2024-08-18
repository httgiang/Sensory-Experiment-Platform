package main.sensoryexperimentplatform.models;

import java.util.ArrayList;
import java.util.Collections;

public class RatingContainer extends ModelContainer{
    private boolean isRandomize;
    private int minTime;
    public RatingContainer(boolean isRandomize,int minTime){
        this.minTime = minTime;
        this.isRandomize = isRandomize;
        children = new ArrayList<>();
    }
    public RatingContainer(RatingContainer other){
        this.isRandomize = other.isRandomize;
        this.minTime = other.minTime;
        children = new ArrayList<>();
        for (Model subO : other.children) {
            if (subO instanceof Vas) {
                Vas temp = new Vas((Vas) subO);
                addChildren(temp);
            }
            if (subO instanceof gLMS) {
                gLMS temp = new gLMS((gLMS) subO);
                addChildren(temp);
            }
        }
    }
    public void randomizeContainer(){
        if(isRandomize){
            Collections.shuffle(children);
        }

    }
    public void addStage(Model s){
        children.add(s);
    }
    public void addVasStageContainer(String title, String lowAnchorText, String highAnchorText,
                                     int lowAnchorValue, int highAnchorValue, String buttonText,
                                     String content, String helpText, boolean isSwap, boolean alert){

        Vas stage = new Vas(title, lowAnchorText, highAnchorText,
                lowAnchorValue, highAnchorValue, buttonText, content,
                helpText, isSwap, alert);

        children.add(stage);
    }

    public void addGlmsStageContainer(String question, String buttonText, String content,
                                      String helpText, boolean alert){

        gLMS stage = new gLMS(question, buttonText, content, helpText, alert);

        children.add(stage);
    }

    public void countMinTime(){
        Timer timer = new Timer("Minimum Time is",String.valueOf(minTime));
        timer.start();
    }
    public void showContainer(){
        ArrayList<Object> show = new ArrayList<>(children);
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


    public String stageToString(){
        StringBuilder sb = new StringBuilder();
        for(Object s : children){
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