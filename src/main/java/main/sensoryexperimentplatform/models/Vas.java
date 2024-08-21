package main.sensoryexperimentplatform.models;

import main.sensoryexperimentplatform.controllers.SoundSingleton;

public class Vas extends Stage implements containerObject, Model{
    private String lowAnchorText, highAnchorText, helpText, buttonText ;
    private int lowAnchorValue, highAnchorValue;
    private int result;
    private String conducted;
    private boolean alert, isSwap;
    private Sound sound;

    public Vas(String title, String lowAnchorText, String highAnchorText,
               int lowAnchorValue, int highAnchorValue, String buttonText,
               String content, String helpText, boolean isSwap, boolean alert) {

        super(title,content);
        this.lowAnchorValue = lowAnchorValue;
        this.highAnchorValue = highAnchorValue;
        this.lowAnchorText = lowAnchorText;
        this.highAnchorText = highAnchorText;
        this.buttonText = buttonText;
        this.helpText = helpText;
        this.isSwap = isSwap;
        this.alert = alert;
        sound = SoundSingleton.getInstance();
        setDefaultResult();
    }
    public Vas(Vas v){
        super(v.getTitle(), v.getContent());
        this.lowAnchorValue = v.lowAnchorValue;
        this.highAnchorValue = v.highAnchorValue;
        this.lowAnchorText = v.getLowAnchorText();
        this.highAnchorText = v.getHighAnchorText();
        this.buttonText = v.getButtonText();
        this.isSwap = v.getIsSwap();
        this.helpText = v.getHelpText();
        this.alert = v.getAlert();
        sound = SoundSingleton.getInstance();
        setDefaultResult();
    }

    public Vas(String title, String content){
        super(title, content);
        this.lowAnchorValue = 0;
        this.highAnchorValue = 100;
        this.lowAnchorText = "Not at all";
        this.highAnchorText = "Extremely";
        this.buttonText = "Continue";
        this.isSwap = false;
        this.helpText = "help text";
        this.alert = false;
        setDefaultResult();
    }
    public void swapPolarities(){
        //swap values
        while(isSwap){
            int temp = getLowAnchorValue();
            setLowAnchorValue(getHighAnchorValue());
            setHighAnchorValue(temp);
            //swap text
            String tempStr = lowAnchorText;
            setLowAnchorText(getHighAnchorText());
            setHighAnchorText(tempStr);
        }
    }
    public void setDefaultResult(){
        result = (highAnchorValue+lowAnchorValue)/2;
    }
    public void setConducted(String conduct_day){
        this.conducted = conduct_day;
    }
    public String getConducted(){
        return conducted;
    }

    public boolean getAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
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

    public boolean getIsSwap() {
        return isSwap;
    }

    public void setSwap(boolean swap) {
        isSwap = swap;
    }

    public void setLowAnchorValue(int lowAnchorValue) {
        this.lowAnchorValue = lowAnchorValue;
    }

    public void setHighAnchorValue(int highAnchorValue) {
        this.highAnchorValue = highAnchorValue;
    }

    public int getLowAnchorValue() {
        return lowAnchorValue;
    }

    public int getHighAnchorValue() {
        return highAnchorValue;
    }

    public String getLowAnchorText(){
        return lowAnchorText;
    }

    public String getHighAnchorText(){
        return  highAnchorText;
    }

    public void setLowAnchorText(String string) {
        this.lowAnchorText = string;
    }

    public void setHighAnchorText(String string) {
        this.highAnchorText = string;
    }
    public void setResult(int result){this.result = result;}
    public int getResult(){return result;}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void playAlertSound(){
       sound.playSound("boop");
    }
    @Override
    public String toString() {
        return "vasStage(\"" + title + "\",\"" + lowAnchorText + "\",\""+
                highAnchorText + "\",\"" + lowAnchorValue + "\",\"" +
                highAnchorValue + "\",\"" + buttonText + "\",\"" +
                content + "\",\"" + helpText + "\",\"" + isSwap + "\",\"" +
                alert +"\")";
    }


}

