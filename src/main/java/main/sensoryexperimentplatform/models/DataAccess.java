package main.sensoryexperimentplatform.models;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

import static main.sensoryexperimentplatform.utilz.Constants.*;

public class DataAccess {
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



    public static void initializeCaches(String experimentName) throws IOException {
        //./results
        File resultsDirectory = new File(saveResultPath);

        // Ensure the results directory exists, create it if not
        if (!resultsDirectory.exists()) {
            resultsDirectory.mkdirs(); // Automatically creates the directory and any necessary parent directories
        }

        // Ensure the experiment directory exists, create it if not
        //./results/exname_version
        File experimentDirectory = new File(saveResultPath + "/" + experimentName);
        if (!experimentDirectory.exists()) {
            experimentDirectory.mkdirs(); // Automatically creates the directory and any necessary parent directories
        }

        // Check if the central CSV file for the experiment exists
        String resultFilePath = saveResultPath + "/" + experimentName + ".csv";
        File resultFile = new File(resultFilePath);

        // If the CSV file doesn't exist, create it and add headers (optional)
        if (!resultFile.exists()) {
            resultFile.createNewFile(); // Creates the blank CSV file if it doesn't exist

            try (FileWriter writer = new FileWriter(resultFile)) {
                // Optionally write headers only for new files
                writer.write("uid,type,time,result,question,lowAnchorText,highAnchorText,lowAnchorValue,highAnchorValue\n");
                writer.flush();
            }
        }
    }

    //Save results of conducted experiment
    public static void quickSave(Experiment experiment, String uid) throws IOException {
        // Create directory for the experiment results if it doesn't exist
        String experimentName = experiment.getExperimentName();
        initializeCaches(experimentName);
        // Create or open the file for saving results
        String resultFilePath = saveResultPath + "/" + experimentName + ".csv";
        // Create a new file for the specific UID
        FileWriter writer2 = new FileWriter(saveResultPath + "/" + experimentName + "/" + uid + ".csv", false);
        for (Object o : experiment.getStages()) {
            if (o instanceof RatingContainer) {
                for (Object subO : ((RatingContainer) o).getChildren()) {
                    saveResult(writer2, subO, uid);
                }
            }
            saveResult(writer2, o, uid);
        }
        writer2.flush();
        writer2.close();
        FileWriter writer = new FileWriter(resultFilePath, false);
        // Write existing data to the file - this file stores all conducted surveys
        writeExistedData(writer, experiment);
        writer.flush();
        writer.close();
        experiment.setNumber_of_results(countingResults(experiment));
    }
    private static void writeExistedData(Writer writer, Experiment experiment) throws IOException {
        String directoryPath = saveResultPath + "/" + experiment.getExperimentName();
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            List<Path> csvFiles = paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".csv"))
                    .collect(Collectors.toList());

            for (Path csvFile : csvFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(csvFile.toFile()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.write("\n");
                    }
                }
            }
        }
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
        if( subO instanceof Question){
            writer.append(uid).append(",").append("questionStage ,")
                    .append(",")
                    .append(((Question) subO).getResult())
                    .append(",")
                    .append(((Question) subO).getQuestion())
                    .append(",")
                    .append(((Question) subO).getLeftButtonText())
                    .append(",")
                    .append(((Question) subO).getRightButtonText())
                    .append(",")
                    .append(((Question) subO).getLeftButtonValue())
                    .append(",")
                    .append(((Question) subO).getRightButtonValue());

            writer.append("\n");
        }
        if( subO instanceof Input){
            writer.append(uid).append(",").append("input ,")
                    .append(",")
                    .append(((Input) subO).getResult())
                    .append(",")
                    .append(((Input) subO).getQuestionText());
            writer.append("\n");
        }

    }
    public static int countingResults(Experiment experiment) throws IOException {
        String directory = experiment.getExperimentName();
        initializeCaches(experiment.getExperimentName());
        int numOfResults = Objects.requireNonNull(new File(saveResultPath + "/" + directory).list()).length;
        return numOfResults;
    }

    public static void importExperiment(String loadFilePath) throws Exception{
        Experiment currentExperiment = createNewExperiment();
        RatingContainer rc = null;
        TasteTest tasteTest = null;
        Course course = null;
        ConditionalStatement conditionalStatement = null;
        boolean isContainer = false, isCourse = false;
        AudibleInstruction audibleInstruction = null;
        String line;
        boolean isIf = false, isElse = false;
        boolean isConditionalStatement = false;
        //notice, input, timer, vas, glms, question, rating container, course, audible instruction

        File file = new File(loadFilePath);
        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        // Get the creation time
        FileTime fileTime = attr.lastModifiedTime();

        // Convert FileTime to LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());

        // Format the date to dd.MM.yyyy
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = dateTime.format(formatter);
        currentExperiment.setLast_modified(formattedDate);
//        System.out.println("creationTime: " + attr.creationTime());
//        System.out.println("lastAccessTime: " + attr.lastAccessTime());
//        System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

        try(BufferedReader reader = new BufferedReader(new FileReader(loadFilePath))){
            while ((line = reader.readLine()) != null ){
                if (line.startsWith("ExperimentName")) {
                    currentExperiment.setExperimentName(line.split(": ")[1].trim());

                } else if (line.startsWith("ExperimenterName")) {
                    currentExperiment.setCreatorName(line.split(": ")[1].trim());

                } else if (line.startsWith("ExperimentID")) {
                    currentExperiment.setId(Integer.parseInt(line.split(": ")[1].trim()));

                } else if (line.startsWith("Last modified")) {
                    currentExperiment.setLast_modified(line.split(": ")[1].trim());

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
                }
                else if (line.startsWith("noticeStage")) {
                    Pattern noticePattern = Pattern.compile("noticeStage\\(\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\",\"([^\"]*?)\"\\)");
                    Matcher matcher = noticePattern.matcher(line);

                    if (matcher.find()) {
                        Notice stage = new Notice(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                Boolean.parseBoolean(matcher.group(5)));
                        if (isCourse && course != null){
                            course.addChildren(stage);
                        }
                        else if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(stage);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(stage);
                        }
                        else {
                            currentExperiment.addStage(stage);
                        }
                    }
                }
                else if (line.startsWith("inputStage")) {
                    Pattern inputPattern = Pattern.compile("inputStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = inputPattern.matcher(line);

                    if (matcher.find()) {
                        Input stage = new Input(matcher.group(1), matcher.group(2), matcher.group(3), Boolean.parseBoolean(matcher.group(4)));

                        if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(stage);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(stage);
                        }
                        else {
                            currentExperiment.addStage(stage);
                        }
                    }
                }
                else if(line.startsWith("audio")){
                    Pattern audioPattern = Pattern.compile("audio\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = audioPattern.matcher(line);
                    if (matcher.find()) {
                        audibleInstruction = new AudibleInstruction(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                matcher.group(5),
                                matcher.group(6));

                        String soundName = Arrays.toString(matcher.group(5).split(","));
                        String formattedSoundName = soundName.substring(1, soundName.length() - 1);
                        String soundPath  = Arrays.toString(matcher.group(6).split(","));
                        String formattedSoundPath = soundPath.substring(1, soundPath.length() - 1);

                        audibleInstruction.addSoundList(formattedSoundName);
                        audibleInstruction.loadSound(formattedSoundName,formattedSoundPath);


                        if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(audibleInstruction);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(audibleInstruction);
                        }
                        else {
                            currentExperiment.addStage(audibleInstruction);
                        }
                    }

                } else if (line.startsWith("tasteTest")){
                    Pattern audiblePattern = Pattern.compile("tasteTest\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\"\\)");
                    Matcher matcher = audiblePattern.matcher(line);

                    if (matcher.find()) {
                        tasteTest = new TasteTest(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                matcher.group(5),
                                matcher.group(6),
                                matcher.group(7),
                                matcher.group(8),
                                Integer.parseInt(matcher.group(9)),
                                Integer.parseInt(matcher.group(10)),
                                Boolean.parseBoolean(matcher.group(11)),
                                Boolean.parseBoolean(matcher.group(12)),
                                Boolean.parseBoolean(matcher.group(13)),
                                Integer.parseInt(matcher.group(14)),
                                Boolean.parseBoolean(matcher.group(15)));

                        String[] foodsOptions = matcher.group(16).split(",");
                        for (String food : foodsOptions) {
                            if (!food.isEmpty()) {
                                tasteTest.addFoodOptions(food.trim());
                            }
                        }

                        String[] vasOptions = matcher.group(17).split(",");
                        for (String vasItem : vasOptions) {
                            if (!vasItem.isEmpty()) {
                                tasteTest.addVASOptions(vasItem.trim());
                            }
                        }

                        String[] gLMSOptions = matcher.group(18).split(",");
                        for (String glmsItem : gLMSOptions) {
                            if (!glmsItem.isEmpty()) {
                                tasteTest.addGLMSOptions(glmsItem.trim());
                            }
                        }

                        String[] selectedFoods = matcher.group(19).split(",");
                        for (String food : selectedFoods) {
                            if (!food.isEmpty()) {
                                tasteTest.getSelectedFoods().add(food.trim());
                            }
                        }

                        String[] selectedVAS = matcher.group(20).split(",");
                        for (String vasItem : selectedVAS) {
                            if (!vasItem.isEmpty()) {
                                tasteTest.getSelectedVAS().add(vasItem.trim());
                            }
                        }

                        String[] selectedGLMS = matcher.group(21).split(",");
                        for (String glmsItem : selectedGLMS) {
                            if (!glmsItem.isEmpty()) {
                                tasteTest.getSelectedGLMS().add(glmsItem.trim());
                            }
                        }

                        if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(tasteTest);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(tasteTest);
                        }
                        else {
                            currentExperiment.addStage(tasteTest);
                        }


                    }
                } else if(line.startsWith("startEating")){
                    //startEating("aaaa","rrrr","aaaa","qqqq","1111","000","00","bbbbb","xxx")
                    Pattern coursePattern = Pattern.compile("startEating\\(\"(.?)\",\"(.?)\",\"(.?)\",\"(.?)\",\"(.?)\",\"(.?)\",\"(.?)\",\"(.?)\",\"(.?)\"\\)");
                    Matcher matcher = coursePattern.matcher(line);
                    isCourse = true;
                    if (matcher.find()) {
                        currentExperiment.addCourseStage(matcher.group(1), matcher.group(2),matcher.group(3),
                                matcher.group(4),Integer.parseInt(matcher.group(5)),
                                Integer.parseInt(matcher.group(6)),Integer.parseInt(matcher.group(7)),
                                matcher.group(8),Boolean.parseBoolean(matcher.group(9)));
                        course = (Course) currentExperiment.getStages().get(createNewExperiment().getStages().size()-1);
                    }
                } else if (line.startsWith("endEating")){
                    course = null;
                    isCourse = false;
                } else if (line.startsWith("vasStage")) {
                    Pattern vasPattern = Pattern.compile("vasStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = vasPattern.matcher(line);

                    if (matcher.find()) {
                        Vas stage = new Vas(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                Integer.parseInt(matcher.group(4)),
                                Integer.parseInt(matcher.group(5)),
                                matcher.group(6),
                                matcher.group(7),
                                matcher.group(8),
                                Boolean.parseBoolean(matcher.group(9)),
                                Boolean.parseBoolean(matcher.group(10)));
                        if (isContainer && rc != null) {
                            rc.addStage(stage);
                        } else if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(stage);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(stage);
                        }
                        else {
                            currentExperiment.addStage(stage);
                        }
                    }
                } else if (line.startsWith("glmsStage")) {
                    Pattern glmsPattern = Pattern.compile("glmsStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"\\{(.*?)\\}\"\\)");
                    Matcher matcher = glmsPattern.matcher(line);


                    if (matcher.find()) {


                        gLMS stage = new gLMS(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                Boolean.parseBoolean(matcher.group(5)),matcher.group(6));

                        String[] variable = matcher.group(7).split(",");
                        for (String variableItem : variable) {
                            if (!variableItem.isEmpty()) {
                                stage.addVariable(variableItem.trim());
                            }
                        }

                        if (isContainer && rc != null) {
                            rc.addStage(stage);
                        } else if(isCourse && course != null){
                            course.addChildren(stage);
                        }
                        else if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(stage);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(stage);
                        }
                        else {
                            currentExperiment.addStage(stage);
                        }
                    }


                } else if (line.startsWith("questionStage")) {
                    Pattern questionPattern = Pattern.compile("questionStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = questionPattern.matcher(line);

                    if (matcher.find()) {
                        Question question = new Question(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                matcher.group(5),
                                matcher.group(6),
                                Boolean.parseBoolean(matcher.group(7))
                        );
                        if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(question);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(question);
                        }
                        else {
                            currentExperiment.addStage(question);
                        }
                    }
                }
                else if(line.startsWith("conditionalStatement")){

                    Pattern conditionalStatementPattern = Pattern.compile("conditionalStatement\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = conditionalStatementPattern.matcher(line);
                    isConditionalStatement = true;
                    if (matcher.find()) {
                       conditionalStatement = new ConditionalStatement(Boolean.parseBoolean(matcher.group(1)),
                                Boolean.parseBoolean(matcher.group(2)),
                                Boolean.parseBoolean(matcher.group(3)),
                                Boolean.parseBoolean(matcher.group(4)),
                                matcher.group(5),
                                matcher.group(6),
                                matcher.group(7),
                                matcher.group(8),
                                matcher.group(9));
                        currentExperiment.addConditionalStatement(conditionalStatement);
                    }
                }
                else if(line.startsWith("If()")){
                    isIf = true;
                }
                else if (line.startsWith("EndIf() ")){
                    isIf = false;
                }

                else if(line.startsWith("Else()")){
                    isElse = true;
                }
                else if(line.startsWith("EndElse()")){
                    isElse = false;
                }
                else if(line.startsWith("EndConditionalStatement()")){
                    conditionalStatement = null;
                    isConditionalStatement = false;
                    isElse = false;
                    isIf = false;
                }

                else if (line.startsWith("ratingsContainer")) {
                    Pattern ratingsContainerPattern = Pattern.compile("ratingsContainer\\(\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = ratingsContainerPattern.matcher(line);

                    isContainer = true;
                    if (matcher.find()) {
                        currentExperiment.addRatingContainerStage(Boolean.parseBoolean(matcher.group(1)),
                                Integer.parseInt(matcher.group(2))
                        );
                        rc = (RatingContainer) currentExperiment.getStages().get(currentExperiment.getStages().size()-1);
                        if(isCourse && course != null){
                            course.addChildren(rc);
                        }
                        else if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(rc);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(rc);
                        }
                    }
                } else if (line.startsWith("endRatingsContainer")) {
                    rc = null;
                    isContainer = false;
                }
                else if (line.startsWith("endExperiment()")){
                    ExperimentList.addExperiment(currentExperiment);
                    currentExperiment.setNumber_of_results(DataAccess.countingResults(currentExperiment));
                    currentExperiment = createNewExperiment();

                }
            }
            updateFile();
        }
    }
    private static Experiment createNewExperiment(){
        return new Experiment(null,null,null,null,1,999,null);
    }

    public static void loadExperiments() throws Exception{
        Experiment currentExperiment = createNewExperiment();
        RatingContainer rc = null;
        Course course = null;
        TasteTest tasteTest = null;
        boolean isConditionalStatement = false;
        boolean isContainer = false;
        boolean isCourse = false;
        AudibleInstruction audibleInstruction = null;
        boolean isIf = false;
        boolean isElse = false;
        ConditionalStatement conditionalStatement = null;

        String line;
        //notice, input, timer, vas, glms, question, rating container, course
        File file = new File(loadFilePath);
        if (!file.exists()){
            file.createNewFile();
        }
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            while ((line = reader.readLine()) != null ){
                if (line.startsWith("ExperimentName:")) {
                    currentExperiment.setExperimentName(line.split(": ")[1].trim());

                } else if (line.startsWith("ExperimenterName:")) {
                    currentExperiment.setCreatorName(line.split(": ")[1].trim());

                } else if (line.startsWith("ExperimentID:")) {
                    currentExperiment.setId(Integer.parseInt(line.split(": ")[1].trim()));

                } else if (line.startsWith("Last modified")) {
                    currentExperiment.setLast_modified(line.split(": ")[1].trim());

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
                        Notice stage = new Notice(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                Boolean.parseBoolean(matcher.group(5)));
                        if (isCourse && course != null){
                            course.addChildren(stage);
                        }
                        else if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(stage);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(stage);
                        }
                        else {
                            currentExperiment.addStage(stage);
                        }
                    }
                } else if (line.startsWith("inputStage")) {
                    Pattern inputPattern = Pattern.compile("inputStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = inputPattern.matcher(line);

                    if (matcher.find()) {
                        Input stage = new Input(matcher.group(1), matcher.group(2), matcher.group(3), Boolean.parseBoolean(matcher.group(4)));

                         if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(stage);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(stage);
                        }
                         else {
                             currentExperiment.addStage(stage);
                         }
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
                    Pattern audioPattern = Pattern.compile("audio\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = audioPattern.matcher(line);
                    if (matcher.find()) {
                        audibleInstruction = new AudibleInstruction(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                matcher.group(5),
                                matcher.group(6));

                        String soundName = Arrays.toString(matcher.group(5).split(","));
                        String formattedSoundName = soundName.substring(1, soundName.length() - 1);
                        String soundPath = Arrays.toString(matcher.group(6).split(","));
                        String formattedSoundPath = soundPath.substring(1, soundPath.length() - 1);

                        audibleInstruction.addSoundList(formattedSoundName);
                        audibleInstruction.loadSound(formattedSoundName,formattedSoundPath);


                        if (isIf && conditionalStatement != null && isConditionalStatement){
                            conditionalStatement.addIf(audibleInstruction);
                        }
                        else if (isElse && conditionalStatement != null && isConditionalStatement){
                            conditionalStatement.addElse(audibleInstruction);
                        }
                        else {
                            currentExperiment.addStage(audibleInstruction);
                        }
                    }
                } else if (line.startsWith("tasteTest")){
                    Pattern tasteTestPattern = Pattern.compile("tasteTest\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\",\"\\{(.*?)\\}\"\\)");
                    Matcher matcher = tasteTestPattern.matcher(line);

                    if (matcher.find()) {
                        tasteTest = new TasteTest(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                matcher.group(5),
                                matcher.group(6),
                                matcher.group(7),
                                matcher.group(8),
                                Integer.parseInt(matcher.group(9)),
                                Integer.parseInt(matcher.group(10)),
                                Boolean.parseBoolean(matcher.group(11)),
                                Boolean.parseBoolean(matcher.group(12)),
                                Boolean.parseBoolean(matcher.group(13)),
                                Integer.parseInt(matcher.group(14)),
                                Boolean.parseBoolean(matcher.group(15)));

                        String[] foodsOptions = matcher.group(16).split(",");
                        for (String food : foodsOptions) {
                            if (!food.isEmpty()) {
                                tasteTest.addFoodOptions(food.trim());
                            }
                        }

                        String[] vasOptions = matcher.group(17).split(",");
                        for (String vasItem : vasOptions) {
                            if (!vasItem.isEmpty()) {
                                tasteTest.addVASOptions(vasItem.trim());
                            }
                        }

                        String[] gLMSOptions = matcher.group(18).split(",");
                        for (String glmsItem : gLMSOptions) {
                            if (!glmsItem.isEmpty()) {
                                tasteTest.addGLMSOptions(glmsItem.trim());
                            }
                        }

                        String[] selectedFoods = matcher.group(19).split(",");
                        for (String food : selectedFoods) {
                            if (!food.isEmpty()) {
                                tasteTest.getSelectedFoods().add(food.trim());
                            }
                        }

                        String[] selectedVAS = matcher.group(20).split(",");
                        for (String vasItem : selectedVAS) {
                            if (!vasItem.isEmpty()) {
                                tasteTest.getSelectedVAS().add(vasItem.trim());
                            }
                        }

                        String[] selectedGLMS = matcher.group(21).split(",");
                        for (String glmsItem : selectedGLMS) {
                            if (!glmsItem.isEmpty()) {
                                tasteTest.getSelectedGLMS().add(glmsItem.trim());
                            }
                        }
                        if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(tasteTest);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(tasteTest);
                        }
                        else {
                            currentExperiment.addStage(tasteTest);
                        }


                    }
                } else if(line.startsWith("startEating")){
                    //startEating("title","content","button","end","-1","-1","-1","help","alert")
                    Pattern coursePattern = Pattern.compile("startEating\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");

                    Matcher matcher = coursePattern.matcher(line);
                    isCourse = true;
                    if (matcher.find()) {
                        course = new Course(matcher.group(1), matcher.group(2),matcher.group(3),
                                matcher.group(4),Integer.parseInt(matcher.group(5)),
                                Integer.parseInt(matcher.group(6)),Integer.parseInt(matcher.group(7)),
                                matcher.group(8),Boolean.parseBoolean(matcher.group(9)));
                        currentExperiment.addStage(course);
                        course = (Course) currentExperiment.getStages().get(currentExperiment.getStages().size()-1);
                    }
                } else if (line.startsWith("endEating")){
                    course = null;
                    isCourse = false;
                } else if (line.startsWith("vasStage")) {
                    Pattern vasPattern = Pattern.compile("vasStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = vasPattern.matcher(line);

                    if (matcher.find()) {
                        Vas stage = new Vas(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                Integer.parseInt(matcher.group(4)),
                                Integer.parseInt(matcher.group(5)),
                                matcher.group(6),
                                matcher.group(7),
                                matcher.group(8),
                                Boolean.parseBoolean(matcher.group(9)),
                                Boolean.parseBoolean(matcher.group(10)));
                        if (isContainer && rc != null) {
                            rc.addStage(stage);
                        } else if(isCourse && course != null){
                            course.addChildren(stage);
                        }
                        else if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(stage);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(stage);
                        }
                        else {
                            currentExperiment.addStage(stage);
                        }
                    }
                } else if (line.startsWith("glmsStage")) {

                    Pattern glmsPattern = Pattern.compile("glmsStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"\\{(.*?)\\}\"\\)");
                    Matcher matcher = glmsPattern.matcher(line);


                    if (matcher.find()) {

                        gLMS stage = new gLMS(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                Boolean.parseBoolean(matcher.group(5)), matcher.group(6));


                        String[] variable = matcher.group(7).split(",");
                        for (String variableItem : variable) {
                            if (!variableItem.isEmpty()) {
                                stage.addVariable(variableItem.trim());
                            }
                        }



                        if (isContainer && rc != null) {
                            rc.addStage(stage);
                        } else if(isCourse && course != null){
                            course.addChildren(stage);
                        }
                        else if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(stage);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(stage);
                        }
                        else {
                            currentExperiment.addStage(stage);
                        }
                    }
                } else if (line.startsWith("questionStage")) {
                    Pattern questionPattern = Pattern.compile("questionStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = questionPattern.matcher(line);

                    if (matcher.find()) {
                        Question question = new Question(matcher.group(1),
                                matcher.group(2),
                                matcher.group(3),
                                matcher.group(4),
                                matcher.group(5),
                                matcher.group(6),
                                Boolean.parseBoolean(matcher.group(7))
                        );
                        if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(question);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(question);
                        }
                        else {
                            currentExperiment.addStage(question);
                        }
                    }
                }
                else if(line.startsWith("conditionalStatement")){

                    Pattern conditionalStatementPattern = Pattern.compile("conditionalStatement\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = conditionalStatementPattern.matcher(line);
                    isConditionalStatement = true;
                    if (matcher.find()) {
                        conditionalStatement = new ConditionalStatement(Boolean.parseBoolean(matcher.group(1)),
                                Boolean.parseBoolean(matcher.group(2)),
                                Boolean.parseBoolean(matcher.group(3)),
                                Boolean.parseBoolean(matcher.group(4)),
                                matcher.group(5),
                                matcher.group(6),
                                matcher.group(7),
                                matcher.group(8),
                                matcher.group(9));
                        currentExperiment.addConditionalStatement(conditionalStatement);
                    }
                }
                else if(line.startsWith("If()")){
                    isIf = true;
                }
                else if (line.startsWith("EndIf() ")){
                    isIf = false;
                }

                else if(line.startsWith("Else()")){
                    isElse = true;
                }
                else if(line.startsWith("EndElse()")){
                    isElse = false;
                }
                else if(line.startsWith("EndConditionalStatement()")){
                    conditionalStatement = null;
                    isConditionalStatement = false;
                    isElse = false;
                    isIf = false;
                }

                else if (line.startsWith("ratingsContainer")) {
                    Pattern ratingsContainerPattern = Pattern.compile("ratingsContainer\\(\"(.*?)\",\"(.*?)\"\\)");
                    Matcher matcher = ratingsContainerPattern.matcher(line);

                    isContainer = true;
                    if (matcher.find()) {
                        currentExperiment.addRatingContainerStage(Boolean.parseBoolean(matcher.group(1)),
                                Integer.parseInt(matcher.group(2))
                        );
                        rc = (RatingContainer) currentExperiment.getStages().get(currentExperiment.getStages().size()-1);
                        if(isCourse && course != null && rc != null){
                            course.addChildren(rc);
                        }
                        else if (isIf && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addIf(rc);
                        }
                        else if (isElse && conditionalStatement !=null && isConditionalStatement){
                            conditionalStatement.addElse(rc);
                        }
                    }
                } else if (line.startsWith("endRatingsContainer")) {
                    rc = null;
                    isContainer = false;
                }
                else if (line.startsWith("endExperiment()")){
                    ExperimentList.addExperiment(currentExperiment);
                    currentExperiment.setNumber_of_results(DataAccess.countingResults(currentExperiment));
                    currentExperiment = createNewExperiment();

                }
            }
        }

        catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number from the file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void updateFile() throws Exception {
        ArrayList<Experiment> experiments = ExperimentList.getInstance();
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


