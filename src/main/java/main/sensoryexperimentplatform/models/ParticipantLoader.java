package main.sensoryexperimentplatform.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ParticipantLoader{
    public static ObservableList<Participant> loadSurveyData(String folderPath){
        ObservableList<Participant> participants = FXCollections.observableArrayList();
        try {
            Files.list(Paths.get(folderPath))
                    .filter(Files::isRegularFile) // Ensure it's a file
                    .forEach(filePath -> {
                        try {
                            BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
                            String uid = trimCsvExtension(filePath.getFileName().toString()) ; // Get the filename as uid

                            FileTime fileTime = attr.creationTime(); // Get the creation time
                            // Convert FileTime to LocalDateTime
                            LocalDateTime dateTime = LocalDateTime.ofInstant(fileTime.toInstant(), ZoneId.systemDefault());

                            // Format the date to dd.MM.yyyy
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.hh.mm.ss.SSS");
                            String created = dateTime.format(formatter);
                            System.out.println("UID: " + uid + "Created: " + created);

                            participants.add(new Participant(uid, created));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return participants;
    }
    // Function to trim .csv extension from filenames
    private static String trimCsvExtension(String filename) {
        if (filename.toLowerCase().endsWith(".csv")) {
            return filename.substring(0, filename.length() - 4); // Remove .csv
        }
        return filename; // Return as is if it doesn't end with .csv
    }
}


