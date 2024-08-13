package main.sensoryexperimentplatform.models;

public class SurveyEntry {
    private final String uid;
    private final String type;
    private final String time;
    private final String result;
    private final String question;
    private final String lowAnchor;
    private final String highAnchor;
    private final String lowValue;
    private final String highValue;

    public SurveyEntry(String uid,String type, String time, String result, String question,
                       String lowAnchor, String highAnchor, String lowValue, String highValue) {
        this.uid = uid;
        this.type = type;
        this.time = time;
        this.result = result;
        this.question = question;
        this.lowAnchor = lowAnchor;
        this.highAnchor = highAnchor;
        this.lowValue = lowValue;
        this.highValue = highValue;
    }
    public String getUid(){
        return uid;
    }
    public String getType() { return type; }
    public String getTime() { return time; }
    public String getResult() { return result; }
    public String getQuestion() { return question; }
    public String getLowAnchor() { return lowAnchor; }
    public String getHighAnchor() { return highAnchor; }
    public String getLowValue() { return lowValue; }
    public String getHighValue() { return highValue; }
}
