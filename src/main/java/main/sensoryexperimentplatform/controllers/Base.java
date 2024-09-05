package main.sensoryexperimentplatform.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.models.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static main.sensoryexperimentplatform.utilz.Constants.DEFAULT_DIRECTORY;


public class Base  {
    private boolean isSidebarVisible = true;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private VBox sideMenu;
    private Experiment selectedExp;
    @FXML
    private BorderPane borderPane;
    private Stage ownerStage;


    public void initialize(Stage ownerStage) {
        this.ownerStage = ownerStage;
        showDashBoard(ownerStage);
    }

    @FXML
    void openDashBoard(ActionEvent event) {
        showDashBoard(ownerStage);
    }

    @FXML
    void toggleDashboard(ActionEvent event) {
        if (isSidebarVisible) {
            borderPane.setLeft(null);

        } else {
            borderPane.setLeft(sideMenu);
        }
        isSidebarVisible = !isSidebarVisible;
    }
    private void showDashBoard(Stage ownerStage){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/sensoryexperimentplatform/DashBoard.fxml"));
        AnchorPane newContent = null;
        try{
            newContent = loader.load();
            mainContent.getChildren().setAll(newContent);
            setResponsive(newContent);
            DashBoardController controller = loader.getController();
            controller.initialize(ownerStage);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    void importExperiment() throws Exception {
        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File(DEFAULT_DIRECTORY));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SIPM Files", "*.sipm")); // Accept all file types
        List<File> files = fileChooser.showOpenMultipleDialog(null);

        if (files != null) {
            for(File file : files){
                String filePath = file.getAbsolutePath();
                DataAccess.importExperiment(filePath);
            }
        }

    }

    @FXML
    void exportExperiment(){
        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File(DEFAULT_DIRECTORY));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SIPM Files", "*.sipm"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            saveToFile(file);
        }
    }

    private void saveToFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(selectedExp.toString());
            writer.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setResponsive(AnchorPane newContent){
        AnchorPane.setTopAnchor(newContent, 0.0);
        AnchorPane.setBottomAnchor(newContent, 0.0);
        AnchorPane.setLeftAnchor(newContent, 0.0);
        AnchorPane.setRightAnchor(newContent, 0.0);
    }
}