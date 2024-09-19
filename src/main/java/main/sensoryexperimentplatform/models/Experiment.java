package main.sensoryexperimentplatform.models;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.paint.Color;

import java.util.*;

public class Experiment implements Observable {
    private String creatorName, experimentName, description, note, last_modified;
    public int version, number_of_results, id, elapsedTime;
    ArrayList<Model> stages;
    private List<InvalidationListener> listeners = new ArrayList<>();
    public Experiment(){
        super();
        Random random = new Random();
        this.id = random.nextInt(999);
        stages = new ArrayList<>();
        version = 1;
        elapsedTime= 0;
//       // start = new Start("default","default","default",
//                false,null, null,
//                0,100,null);
        this.last_modified = DataAccess.getCurrentFormattedDate();
    }

    public Experiment(String creatorName, String experimentName, String description, String note, int version, int id, String last_modified) {
        this.creatorName = creatorName;
        this.experimentName = experimentName;
        this.description = description;
        this.note = note;
        this.version = version;
        this.id = id;
        this.last_modified = last_modified;
        stages = new ArrayList<>();
        elapsedTime = 0;
        //start = new Start("default","default","default",false,null, null,0,100,null);
    }
    public Experiment(Experiment selectedExperiment) {
        this.creatorName = selectedExperiment.getCreatorName();
        this.experimentName = selectedExperiment.getExperimentName();
        this.description = selectedExperiment.getDescription();
        this.note = selectedExperiment.getNote();
        this.version = selectedExperiment.getVersion();
        this.id = selectedExperiment.getId();
        if (selectedExperiment.getLast_modified()!= null){
            this.last_modified = selectedExperiment.getLast_modified();
        }else this.last_modified = "";
        elapsedTime= 0;
        stages = new ArrayList<>();
        for (Object o : selectedExperiment.getStages()) {
            if(o instanceof Start){
                Start temp = new Start((Start) o);
                stages.add(temp);

            }else if (o instanceof Vas) {
                Vas temp = new Vas((Vas) o);
                stages.add(temp);

            }else if (o instanceof gLMS) {
                gLMS temp = new gLMS((gLMS) o);
                stages.add(temp);

            }else if(o instanceof Notice) {
                Notice temp = new Notice((Notice) o);
                stages.add(temp);

            }else if(o instanceof Timer) {
                Timer temp = new Timer((Timer) o);
                stages.add(temp);

            }else if (o instanceof RatingContainer) {
                RatingContainer ratingContainer = new RatingContainer((RatingContainer) o);
                stages.add(ratingContainer);

            }else if (o instanceof AudibleInstruction) {
                AudibleInstruction audibleInstruction = new AudibleInstruction((AudibleInstruction) o);
                stages.add(audibleInstruction);

            }
            else if (o instanceof Question) {
                Question question = new Question((Question) o);
                stages.add(question);

            }
            else if (o instanceof Input){
                Input input = new Input((Input) o);
                stages.add(input);
            }
            else if (o instanceof Course){
                Course course = new Course((Course) o);
                stages.add(course);
            }
            else if(o instanceof TasteTest){
                TasteTest tasteTest = new TasteTest((TasteTest) o);
                stages.add(tasteTest);
            }
            else if(o instanceof ConditionalStatement){
                ConditionalStatement conditionalStatement = new ConditionalStatement((ConditionalStatement) o);
                stages.add(conditionalStatement);
            }

        }
    }

    public void addNoticeStage(String title, String content, String buttonText,
                               String helpText, boolean alert){

        Notice stage = new Notice(title,content,buttonText,helpText, alert);

        stages.add(stage);
    }
    public void addInputStage(String title, String content, String buttonText, boolean alert){
        Input stage = new Input(title, content, buttonText, alert );

        stages.add(stage);
    }
    public void addTimerStage(String instruction, String timeWait, boolean alert){
        Timer stage = new Timer(instruction,timeWait, alert);

        stages.add(stage);
    }
    public void addVasStage (Vas vas){
        stages.add(vas);
    }
    public void addGlmsStage(gLMS gLMS){
        stages.add(gLMS);
    }
    public void addNoticeStage(Notice notice){
        stages.add(notice);
    }
    public void addTimerStage (Timer timer){
        stages.add(timer);
    }
    public void addVasStage(String title, String lowAnchorText, String highAnchorText,
                            int lowAnchorValue, int highAnchorValue, String buttonText,
                            String helpText, boolean isSwap, boolean alert){
        Vas stage = new Vas(title, lowAnchorText, highAnchorText,
                lowAnchorValue, highAnchorValue, buttonText,
                helpText, isSwap, alert);

        stages.add(stage);
    }

    public void addGlmsStage(String question, String buttonText, String content,
                             String helpText, boolean alert,String choosenVariable){

        gLMS stage = new gLMS(question, buttonText, helpText, alert);

        stages.add(stage);
    }

    public void addQuestionStage(String question,String leftButtonText, String rightButtonText, String leftButtonValue, String rightButtonValue,
                                 String helpText, boolean alert,String chosenVariable){

        Question stage = new Question(question,leftButtonText,rightButtonText,leftButtonValue,rightButtonValue,helpText,alert,chosenVariable);

        stages.add(stage);
    }
    public void addRatingContainerStage(boolean isRandomize, int time){
        RatingContainer container = new RatingContainer(isRandomize,time);
        stages.add(container);
    }
    public void addRatingContainerStage(RatingContainer ratingContainer){
        stages.add(ratingContainer);
    }

    public void addNewTasteTest(TasteTest tasteTest){
        stages.add(tasteTest);
        tasteTest.generateTasteTest();
    }
    public void addConditonalStatement(boolean value1, boolean value2, boolean variable1, boolean variable2, String value1Text,
                                       String value2Text, String variable1Choice, String variable2Choice, String compare){
        ConditionalStatement conditionalStatement = new ConditionalStatement(value1,value2, variable1,variable2,value1Text,value2Text,variable1Choice,variable2Choice,compare);
        stages.add(conditionalStatement);

    }
    // Audible instruction stage
    public void addAudibleInstruction(String title, String content, String buttonText, String helpText,String soundName,String filePath) {
        AudibleInstruction temp = new AudibleInstruction(title, content, buttonText, helpText,soundName,filePath);
        stages.add(temp);
    }
    public void addCourse (Course course){
        stages.add(course);
    }

    public void addCourseStage(String title, String content, String buttonText, String endStatement,
                               int refillWeight, int duration, int quantity, String helpText, boolean alert){
        Course temp = new Course(title,content,buttonText,endStatement,refillWeight,duration,quantity,helpText,alert);
        stages.add(temp);
    }
    public void addQuestion (Question question){
        stages.add(question);
    }

    public void addAudibleInstruction (AudibleInstruction audibleInstruction){
        stages.add(audibleInstruction);
    }

    public void addInput(Input input){
        stages.add(input);
    }

    public void addConditionalStatement(ConditionalStatement ConditionalStatement){
        stages.add(ConditionalStatement);
    }


    public ArrayList<Model> getStages() {
        return stages;
    }

    public void setStages(ArrayList<Model> stages) {
        this.stages = stages;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getNumber_of_results() {
        return number_of_results;
    }

    public void setNumber_of_results(int number_of_results) {
        this.number_of_results = number_of_results;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getExperimentName() {
        return experimentName;
    }


    public String getNote() {
        return note;
    }

    public String getDescription() {
        return description;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void updateVersion(){
        System.out.println("Version updated successfully");
        version+=1;
    }

    public String stagesToString(){
        StringBuilder sb = new StringBuilder();
        for(Object o : stages){
            sb.append(o.toString()).append("\n");
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        return "ExperimentName: " + experimentName + "\nExperimenterName: " + creatorName +
                "\nExperimentID: "+ id + "\nLast modified: "+ last_modified +
                "\nVersion: "+ version + "\n"+
                stagesToString() +"endExperiment()\n";
    }

    public void addStartStage(String title, String content, String buttonText, Boolean requireBalance, Color backGroundColor, Color TextColor
            , long StartOfStageDelay, long EndOfStageDelay, Color disableButtonColor){
        Start stage = new Start(title,
                content,
                buttonText,
                requireBalance,
                backGroundColor,
                TextColor,StartOfStageDelay,
                EndOfStageDelay, disableButtonColor);
        stages.add(stage);
    }
    public void addStartStage(String title, String content, String buttonText){
        Start stage = new Start(title, content,  buttonText, false, null, null, 0, 0, null);
        stages.add(stage);
    }

    public Start getStart() {
        if(stages.isEmpty()){
            return null;
        }
        else {
            for(Object stage : stages){
                if(stage instanceof Start){
                    return (Start)stage;
                }
            }
        }
        return null;
    }




    public void addStart(Start start) {
        stages.add(start);
    }

    public void addStage(Model stage) {
        stages.add(stage);
    }


    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.remove(invalidationListener);
    }
}