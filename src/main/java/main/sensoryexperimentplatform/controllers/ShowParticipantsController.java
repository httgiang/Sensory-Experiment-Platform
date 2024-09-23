package main.sensoryexperimentplatform.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.models.Participant;
import main.sensoryexperimentplatform.models.ParticipantLoader;
import main.sensoryexperimentplatform.viewmodel.ShowParticipantVM;

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
//        bindColumnWidths();
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
