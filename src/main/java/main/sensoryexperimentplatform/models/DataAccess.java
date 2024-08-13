package main.sensoryexperimentplatform.models;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.sensoryexperimentplatform.utilz.Constants.*;

public class DataAccess {
    private String uid;
    public static String getCurrentFormattedTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS");
        Date now = new Date();
        return sdf.format(now);
    }
    public static String getCurrentFormattedDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date now = new Date();
        return sdf.format(now);
    }
    public static void saveData(ArrayList<Experiment> experiments) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath, false));
        for(Experiment e : experiments){
            writer.write(e.toString());
            writer.newLine();
        }
        writer.close();
    }

    public static void saveNewExperiment(Experiment experiment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath, true))) {
            writer.write(experiment.toString());
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void exportExperiments(String file_path, ArrayList<Experiment> experiments) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file_path,true));
        for (Experiment e : experiments){
            writer.write(e.toString());
            writer.newLine();
        }
        System.out.println("Saved in "+ file_path);
        //"Saved in "+ file_path
    }



    private static void initializeCaches(String experimentName, int version){
        File resultsDirectory = new File("results");
        if (!resultsDirectory.exists()) {
            resultsDirectory.mkdirs(); // Automatically creates the directory and any necessary parent directories
        }
        // Create directory for the experiment if it doesn't exist
        File experimentDirectory = new File("results/" + experimentName+"_"+version);
        if (!experimentDirectory.exists()) {
            experimentDirectory.mkdirs(); // Automatically creates the directory and any necessary parent directories
        } else {
//            System.out.println("Experiment's result directory already exists");
        }
    }
    //Save results of conducted experiment
    public static void quickSave(Experiment experiment, String uid) throws IOException {
        // Create directory for the experiment results if it doesn't exist
        String experimentName = experiment.getExperimentName();
        int version = experiment.getVersion();
        // catch does not exist file
        initializeCaches(experimentName,version);
        // Create file for saving results
        FileWriter writer = new FileWriter("results/" + experimentName+"_"+version + ".csv", false);
//        writer.write("Heading,Time,Vas/GLMS Result,Question,Low Anchor, High Anchor, Low Value, High Value\n");

        for (Object o : experiment.getStages()) {
            if(o instanceof RatingContainer){
                for(Object subO : ((RatingContainer) o).getContainer()){
                    saveResult(writer,subO,uid);
                }
            }
            saveResult(writer,o,uid);
        }
//        writer.write("Elapsed Time," + experiment.elapsedTime + ", seconds");


        writer.flush();
        writer.close();
        experiment.setNumber_of_results(countingResults(experiment));
    }
    // quickSave func use this
    private static void saveResult(Writer writer, Object subO, String uid) throws IOException {
        if( subO instanceof Vas){
            writer.append(uid).append(",").append("Vas,")
                    .append(((Vas) subO).getConducted())
                    .append(",")
                    .append(String.valueOf(((Vas) subO).getResult()).trim())
                    .append(",")
                    .append(((Vas) subO).getTitle())
                    .append(",")
                    .append(((Vas) subO).getLowAnchorText())
                    .append(",")
                    .append(((Vas) subO).getHighAnchorText())
                    .append(",")
                    .append(String.valueOf(((Vas) subO).getLowAnchorValue()))
                    .append(",")
                    .append(String.valueOf(((Vas) subO).getHighAnchorValue()));
            writer.append("\n");
        }
        if( subO instanceof gLMS){
            writer.append(uid).append(",").append("GLMS ,")
                    .append(((gLMS) subO).getConducted())
                    .append(",")
                    .append(String.format("%d",((gLMS) subO).getResult()))
                    .append(",")
                    .append(((gLMS) subO).getTitle());
            writer.append("\n");
        }
    }
    public static int countingResults(Experiment experiment){
        String directory = experiment.getExperimentName() + "_" + experiment.getVersion();
        initializeCaches(experiment.getExperimentName(),experiment.getVersion());
        int numOfResults = Objects.requireNonNull(new File("results/" + directory).list()).length;
        return numOfResults;
    }

    public static void importExperiment(String loadFilePath) throws Exception{
        Experiment currentExperiment = new Experiment(null,null,null,null,1,000,null);
        RatingContainer rc = null;
        boolean isContainer = false;
        String line;

        //notice, input, timer, vas, glms, question, rating container, course, audible instruction

        try(BufferedReader reader = new BufferedReader(new FileReader(loadFilePath))){
            while ((line = reader.readLine()) != null ){
                if (line.startsWith("ExperimentName")) {
                    currentExperiment.setExperimentName(line.split(": ")[1].trim());

                } else if (line.startsWith("ExperimenterName")) {
                    currentExperiment.setCreatorName(line.split(": ")[1].trim());

                } else if (line.startsWith("ExperimentID:")) {
                    currentExperiment.setId(Integer.parseInt(line.split(": ")[1].trim()));

                } else if (line.startsWith("Created on")) {
                    currentExperiment.setCreated_date(line.split(": ")[1].trim());

                } else if (line.startsWith("Version")) {
                    int version = Integer.parseInt(line.split(": ")[1].trim());
                    currentExperiment.version = version;

                } else if (line.startsWith("startExperiment")) {
                    Pattern patternExperiment = Pattern.compile("startExperiment\\(\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = patternExperiment.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addStartStage(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3));
                    }
                } else if (line.startsWith("noticeStage")) {
                    Pattern noticePattern = Pattern.compile("noticeStage\\(\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\"\\)");
                    Matcher matcher = noticePattern.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addNoticeStage(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                Boolean.parseBoolean(matcher.group(5))
                        );
                    }
                } else if (line.startsWith("inputStage")) {
                    Pattern inputPattern = Pattern.compile("inputStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = inputPattern.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addInputStage(matcher.group(1), matcher.group(2), matcher.group(3), Boolean.parseBoolean(matcher.group(4)));
                    }
                } else if (line.startsWith("wait")) {
                    Pattern timerPattern = Pattern.compile("wait\\(\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = timerPattern.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addTimerStage(matcher.group(1),
                                matcher.group(2),
                                Boolean.parseBoolean(matcher.group(3))
                        );
                    }
                }
                else if(line.startsWith("audio")){
                    Pattern audioPattern = Pattern.compile("audio\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = audioPattern.matcher(line);
                    if (matcher.find()) {
                        currentExperiment.addAudibleInstruction(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                    }

                }

                else if (line.startsWith("vasStage")) {
                    Pattern vasPattern = Pattern.compile("vasStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = vasPattern.matcher(line);

                    if (matcher.find()) {
                        if (isContainer && rc != null) {
                            rc.addVasStageContainer(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    Integer.parseInt(matcher.group(4)),
                                    Integer.parseInt(matcher.group(5)),
                                    matcher.group(6),
                                    matcher.group(7),
                                    matcher.group(8),
                                    Boolean.parseBoolean(matcher.group(9)),
                                    Boolean.parseBoolean(matcher.group(10))
                            );
                        } else {
                            currentExperiment.addVasStage(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    Integer.parseInt(matcher.group(4)),
                                    Integer.parseInt(matcher.group(5)),
                                    matcher.group(6),
                                    matcher.group(7),
                                    matcher.group(8),
                                    Boolean.parseBoolean(matcher.group(9)),
                                    Boolean.parseBoolean(matcher.group(10))
                            );
                        }
                    }
                } else if (line.startsWith("glmsStage")) {
                    Pattern glmsPattern = Pattern.compile("glmsStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = glmsPattern.matcher(line);

                    if (matcher.find()) {
                        if (isContainer && rc != null) {
                            rc.addGlmsStageContainer(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    matcher.group(4),
                                    Boolean.parseBoolean(matcher.group(5))
                            );
                        } else {
                            currentExperiment.addGlmsStage(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    matcher.group(4),
                                    Boolean.parseBoolean(matcher.group(5))
                            );
                        }
                    }
                } else if (line.startsWith("questionStage")) {
                    Pattern questionPattern = Pattern.compile("questionStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = questionPattern.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addQuestionStage(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                Boolean.parseBoolean(matcher.group(4))
                        );
                    }
                } else if (line.startsWith("ratingsContainer")) {
                    Pattern ratingsContainerPattern = Pattern.compile("ratingsContainer\\(\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = ratingsContainerPattern.matcher(line);

                    isContainer = true;
                    if (matcher.find()) {
                        currentExperiment.addRatingContainerStage(Boolean.parseBoolean(matcher.group(1)),
                                Integer.parseInt(matcher.group(2))
                        );
                        rc = (RatingContainer) currentExperiment.getStages().get(currentExperiment.getStages().size()-1);
                    }
                } else if (line.startsWith("endRatingsContainer")) {
                    rc = null;
                    isContainer = false;
                } else if (line.startsWith("endExperiment()")){
                    listOfExperiment.addExperiment(currentExperiment);
                    initializeCaches(currentExperiment.getExperimentName(),currentExperiment.getVersion());
                    currentExperiment.setNumber_of_results(DataAccess.countingResults(currentExperiment));
                    currentExperiment = new Experiment(null,null,null,null,1,000,null);
                }
            }
        }
    }

    public static void loadExperiments() throws Exception{
        Experiment currentExperiment = new Experiment(null,null,null,null,1,000,null);
        RatingContainer rc = null;
        boolean isContainer = false;
        String line;
        //notice, input, timer, vas, glms, question, rating container, course

        try(BufferedReader reader = new BufferedReader(new FileReader(loadFilePath))){
            while ((line = reader.readLine()) != null ){
                if (line.startsWith("ExperimentName:")) {
                    currentExperiment.setExperimentName(line.split(": ")[1].trim());

                } else if (line.startsWith("ExperimenterName:")) {
                    currentExperiment.setCreatorName(line.split(": ")[1].trim());

                } else if (line.startsWith("ExperimentID:")) {
                    currentExperiment.setId(Integer.parseInt(line.split(": ")[1].trim()));

                } else if (line.startsWith("Created on")) {
                    currentExperiment.setCreated_date(line.split(": ")[1].trim());

                } else if (line.startsWith("Version")) {
                    int version = Integer.parseInt(line.split(": ")[1].trim());
                    currentExperiment.version = version;

                } else if (line.startsWith("startExperiment")) {
                    Pattern patternExperiment = Pattern.compile("startExperiment\\(\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = patternExperiment.matcher(line);
                    if (matcher.find()) {
                        currentExperiment.addStartStage(matcher.group(1), matcher.group(2), matcher.group(3));
                    }
                } else if (line.startsWith("noticeStage")) {
                    Pattern noticePattern = Pattern.compile("noticeStage\\(\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\"\\)");
                    Matcher matcher = noticePattern.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addNoticeStage(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                Boolean.parseBoolean(matcher.group(5))
                        );
                    }
                } else if (line.startsWith("inputStage")) {
                    Pattern inputPattern = Pattern.compile("inputStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = inputPattern.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addInputStage(matcher.group(1), matcher.group(2), matcher.group(3), Boolean.parseBoolean(matcher.group(4)));
                    }
                } else if (line.startsWith("wait")) {
                    Pattern timerPattern = Pattern.compile("wait\\(\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = timerPattern.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addTimerStage(matcher.group(1),
                                matcher.group(2),
                                Boolean.parseBoolean(matcher.group(3))
                        );
                    }

                } else if(line.startsWith("audio")){
                    Pattern audiblePattern = Pattern.compile("audio\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = audiblePattern.matcher(line);
                    if (matcher.find()) {
                        currentExperiment.addAudibleInstruction(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4),matcher.group(5));
                    }

                } else if (line.startsWith("vasStage")) {
                    Pattern vasPattern = Pattern.compile("vasStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = vasPattern.matcher(line);

                    if (matcher.find()) {
                        if (isContainer && rc != null) {
                            rc.addVasStageContainer(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    Integer.parseInt(matcher.group(4)),
                                    Integer.parseInt(matcher.group(5)),
                                    matcher.group(6),
                                    matcher.group(7),
                                    matcher.group(8),
                                    Boolean.parseBoolean(matcher.group(9)),
                                    Boolean.parseBoolean(matcher.group(10))
                            );
                        } else {
                            currentExperiment.addVasStage(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    Integer.parseInt(matcher.group(4)),
                                    Integer.parseInt(matcher.group(5)),
                                    matcher.group(6),
                                    matcher.group(7),
                                    matcher.group(8),
                                    Boolean.parseBoolean(matcher.group(9)),
                                    Boolean.parseBoolean(matcher.group(10))
                            );
                        }
                    }
                } else if (line.startsWith("glmsStage")) {
                    Pattern glmsPattern = Pattern.compile("glmsStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = glmsPattern.matcher(line);

                    if (matcher.find()) {
                        if (isContainer && rc != null) {
                            rc.addGlmsStageContainer(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    matcher.group(4),
                                    Boolean.parseBoolean(matcher.group(5))
                            );
                        } else {
                            currentExperiment.addGlmsStage(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    matcher.group(4),
                                    Boolean.parseBoolean(matcher.group(5))
                            );
                        }
                    }
                } else if (line.startsWith("questionStage")) {
                    Pattern questionPattern = Pattern.compile("questionStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = questionPattern.matcher(line);

                    if (matcher.find()) {
                        currentExperiment.addQuestionStage(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                Boolean.parseBoolean(matcher.group(4))
                        );
                    }
                } else if (line.startsWith("ratingsContainer")) {
                    Pattern ratingsContainerPattern = Pattern.compile("ratingsContainer\\(\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = ratingsContainerPattern.matcher(line);

                    isContainer = true;
                    if (matcher.find()) {
                        currentExperiment.addRatingContainerStage(Boolean.parseBoolean(matcher.group(1)),
                                Integer.parseInt(matcher.group(2))
                        );
                        rc = (RatingContainer) currentExperiment.getStages().get(currentExperiment.getStages().size()-1);
                    }
                } else if (line.startsWith("endRatingsContainer")) {
                    rc = null;
                    isContainer = false;
                } else if (line.startsWith("endExperiment()")){
                    listOfExperiment.addExperiment(currentExperiment);
                    currentExperiment.setNumber_of_results(DataAccess.countingResults(currentExperiment));
                    currentExperiment = new Experiment(null,null,null,null,1,999,null);

                }
            }
        }

    }

    public static void updateFile() throws Exception {
        ArrayList<Experiment> experiments = listOfExperiment.getInstance();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath))) {
            for (Experiment experiment : experiments) {
                writer.write(experiment.toString());
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}


