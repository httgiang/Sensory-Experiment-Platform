package main.sensoryexperimentplatform.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SurveyDataLoader {

    public static ObservableList<SurveyEntry> loadSurveyData(String filePath) {
        ObservableList<SurveyEntry> surveyData = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");

                // Ensure there are at least 4 columns (minimum required to create a SurveyEntry)
                if (columns.length < 4) {
                    System.err.println("Incomplete line: " + line);
                    continue;
                }

                String uid = columns[0].trim();
                String type = columns.length > 1 ? columns[1].trim() : "";
                String time = columns.length > 2 ? columns[2].trim() : null;
                String result = columns.length > 3 && !columns[3].trim().isEmpty() ? columns[3].trim() : "0";
                String question = columns.length > 4 ? columns[4].trim() : "";

                String lowAnchor = columns.length > 5 ? columns[5].trim() : "";
                String highAnchor = columns.length > 6 ? columns[6].trim() : "";
                String lowValue = columns.length > 7 && !columns[7].trim().isEmpty() ? columns[7].trim() : "0";
                String highValue = columns.length > 8 && !columns[8].trim().isEmpty() ? columns[8].trim() : "0";

                SurveyEntry entry = new SurveyEntry(uid, type, time, result, question, lowAnchor, highAnchor, lowValue, highValue);
                surveyData.add(entry);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return surveyData;
    }
}


