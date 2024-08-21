package main.sensoryexperimentplatform.models;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {

    private static final String FILE_NAME = "Models/main.sensoryexperimentplatform.Data/ManyExperiments";
    private static final String saveFilePath = "Models/main.sensoryexperimentplatform.Data/user";

    public static void main(String[] args) throws IOException {
        UserManager manager = new UserManager();
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 for Login, 2 for Register:");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = manager.login(username, password);
            if (user != null) {
                System.out.println(user);
                saveData(user);
            } else {
                System.out.println("Login failed");
            }
        } else if (choice == 2) {
            System.out.print("Choose a username: ");
            String username = scanner.nextLine();
            System.out.print("Choose a password: ");
            String password = scanner.nextLine();

            if (register(username, password)) {
                System.out.println("Registration successful. You can now log in.");
            } else {
                System.out.println("Username already exists. Please choose a different username.");
            }
        }

        scanner.close();
    }

    public static boolean register(String user, String pass) {
        StringBuilder existingData = new StringBuilder();
        boolean usernameExists = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(user + ":")) {
                    usernameExists = true; // Username already exists
                }existingData.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (usernameExists) {
            return false; // Username already exists
        }
        User newUser = new User(user, pass);
        String newUserData = newUser+ "\n";

        // Write the new user data followed by the existing data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(newUserData);
            writer.write(existingData.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void saveData(Object o) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFilePath, false))) {
            writer.write(o.toString());
        }
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        String hashPass = new User(null, password).hashedPassword;
        return hashPass.equals(hashedPassword);
    }

    public Experiment getExperimentForUser(String exId) throws IOException {
        Experiment currentExperiment = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            RatingContainer rc = null;
            boolean isContainer = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ExperimentID: " + exId)) {
                    currentExperiment = new Experiment(null, null, null, null, 1,000,null);
                    currentExperiment.setId(Integer.parseInt(exId));

                }
                else if (currentExperiment != null) {
                    if (line.startsWith("ExperimentName")) {
                        currentExperiment.setExperimentName(line.split(": ")[1].trim());

                    } else if (line.startsWith("ExperimenterName")) {
                        currentExperiment.setCreatorName(line.split(": ")[1].trim());

                    } else if (line.startsWith("Version")) {
                        int crt = Integer.parseInt( line.split(": ")[1].trim());
                        currentExperiment.version =crt;

                    } else if (line.startsWith("startExperiment")) {
                        Pattern patternExperiment = Pattern.compile("startExperiment\\(\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                        Matcher matcher = patternExperiment.matcher(line);

                        if (matcher.find()) {
                            currentExperiment.setDescription(matcher.group(1));
                            currentExperiment.setNote(matcher.group(3));
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
                        Pattern questionPattern = Pattern.compile("questionStage\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                        Matcher matcher = questionPattern.matcher(line);

                        if (matcher.find()) {
                            currentExperiment.addQuestionStage(matcher.group(1),
                                    matcher.group(2),
                                    matcher.group(3),
                                    matcher.group(4),
                                    matcher.group(5),
                                    matcher.group(6),
                                    Boolean.parseBoolean(matcher.group(7))
                            );
                        }

                    }
                    else if(line.startsWith("audio")){
                        Pattern audioPattern = Pattern.compile("audio\\(\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\",\"(.*?)\"\\)");
                        Matcher matcher = audioPattern.matcher(line);
                        if (matcher.find()) {
                            currentExperiment.addAudibleInstruction(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4), matcher.group(5), matcher.group(6));
                        }

                    }else if (line.startsWith("ratingsContainer")) {
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
                    } else if (line.startsWith("endExperiment()")) {
                        return currentExperiment;
                    }
                }
            }
        }
        return currentExperiment;
    }

    public User login(String username, String password) {
        User current_user = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(username)) {
                    String[] parts = line.split(":");
                    if (parts.length == 3 && parts[0].equals(username)) {
                        String hashedPassword = parts[1];
                        if (verifyPassword(password, hashedPassword)) {
                            current_user = new User(username, password);
                            String permission = parts[2].replaceAll("^\\[|\\]$", "").trim();
                            System.out.println("check");
                            String[] list = permission.split(", ");
                            current_user.setPermission(list);

                            for (String id : current_user.getPermission()) {
                                BufferedReader expReader = null;
                                try {
                                    expReader = new BufferedReader(new FileReader(FILE_NAME));
                                    String expLine;
                                    while ((expLine = expReader.readLine()) != null) {
                                        if (expLine.startsWith("ExperimentID: " + id)) {
                                            Experiment temp = getExperimentForUser(id);
                                            current_user.addExperiment(temp);
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (expReader != null) {
                                        try {
                                            expReader.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("Login failed");
                            return null;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return current_user;
    }
}
