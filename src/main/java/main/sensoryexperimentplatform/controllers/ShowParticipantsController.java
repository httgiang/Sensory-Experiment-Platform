package main.sensoryexperimentplatform.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.models.Participant;
import main.sensoryexperimentplatform.models.ParticipantLoader;
import main.sensoryexperimentplatform.viewmodel.ShowParticipantVM;
import main.sensoryexperimentplatform.viewmodel.ShowResultVM;

import java.io.IOException;

public class ShowParticipantsController {

    @FXML
    private AnchorPane coverPane;

    @FXML
    private TableColumn<Participant, String> createdOnColumn;

    @FXML
    private TableColumn<Participant, String> fileNameColumn;

    @FXML
    private TableView<Participant> participantTable;

    ShowParticipantVM viewModel;

    @FXML
    private void initialize(){
        setupTableColumns();
        bindColumnWidths();

        // Set up click listener for table rows (no need to check if items are empty)
        participantTable.setOnMouseClicked(event -> {
            Participant selectedParticipant = participantTable.getSelectionModel().getSelectedItem();
            if (selectedParticipant != null) {
                try {
                    System.out.println("Clicked: " + selectedParticipant.getUid());
                    showParticipantResults(selectedParticipant);
                } catch (IOException e) {
                    e.printStackTrace(); // Consider more detailed error handling/logging
                }
            }
        });
    }

    private void showParticipantResults(Participant participant) throws IOException {
        System.out.println("Showing results for " + participant.getUid());

        // Load the FXML file for the participant results view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/sensoryexperimentplatform/ResultTableEx.fxml"));
        Parent root = loader.load();

        // Get the controller of the new window
        ShowResultController controller = loader.getController();
        ShowResultVM resultVM = new ShowResultVM(viewModel.getExperiment(), participant);

        // Pass the selected participant to the controller
        controller.setViewModel(resultVM);

        // Create a new stage (window)
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setViewModel(ShowParticipantVM viewModel) {
        this.viewModel = viewModel;

        if (participantTable == null) {
            System.err.println("TableView is not initialized!");
            return;
        }

        if (this.viewModel != null) {
            try {
                ObservableList<Participant> data = ParticipantLoader.loadSurveyData(viewModel.getFolderPath());
                if (data != null) {
                    participantTable.setItems(data);
                } else {
                    System.err.println("The system cannot find the file specified");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setupTableColumns() {
        // Set up the cell value factories for each column
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("uid"));
        createdOnColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
    }

    private void bindColumnWidths(){
        fileNameColumn.prefWidthProperty().bind(participantTable.widthProperty().multiply(0.5));
        createdOnColumn.prefWidthProperty().bind(participantTable.widthProperty().multiply(0.5));
    }
}
