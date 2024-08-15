package main.sensoryexperimentplatform.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.viewmodel.FillName_VM;
import main.sensoryexperimentplatform.viewmodel.DashBoard_VM;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.listOfExperiment;
import main.sensoryexperimentplatform.viewmodel.ShowResultVM;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Stack;
import java.util.concurrent.ScheduledExecutorService;

public class DashBoardController {

    private static final int ITEMS_PER_PAGE = 6;
    private DashBoard_VM dashBoard_vm;

    private ScheduledExecutorService executorService;
    private long startTime, elapsedTime;
    @FXML
    private TableView<Experiment> contentTable;

    @FXML
    private TableColumn<Experiment, String> lbl_creator;

    @FXML
    private TableColumn<Experiment, String> lbl_currentVersion;

    @FXML
    private TableColumn<Experiment, String> lbl_experimentName;

    @FXML
    private TableColumn<Experiment, String> lbl_iD;

    @FXML
    private TableColumn<Experiment, String> lbl_result;
    @FXML
    private TableColumn<Experiment, String> lbl_createDate;

    @FXML
    private TableColumn<Experiment, String> lbl_Option;

    @FXML
    private AnchorPane dashBoardPane;
    @FXML
    private Pagination pagination;

    private Stack<Experiment> deletedExp = new Stack<>();

    private Experiment selectedExperiment;

    public void initialize() {
        //this.base = base;
       dashBoard_vm = new DashBoard_VM();
       bindViewModel();
       bindColumnWidths();
       setupPaginationListener();
    }

    private void bindColumnWidths() {
        lbl_iD.prefWidthProperty().bind(contentTable.widthProperty().multiply(0.075)); // 7.5% of table width
        lbl_creator.prefWidthProperty().bind(contentTable.widthProperty().multiply(0.15)); // 15% of table width
        lbl_experimentName.prefWidthProperty().bind(contentTable.widthProperty().multiply(0.20)); // 20% of table width
        lbl_currentVersion.prefWidthProperty().bind(contentTable.widthProperty().multiply(0.10)); // 12.5% of table width
        lbl_result.prefWidthProperty().bind(contentTable.widthProperty().multiply(0.10)); // 10% of table width
        lbl_createDate.prefWidthProperty().bind(contentTable.widthProperty().multiply(0.10)); // 20% of table width
        lbl_Option.prefWidthProperty().bind(contentTable.widthProperty().multiply(0.25)); // 25% of table width
    }


    public void bindViewModel() {

        lbl_iD.setCellValueFactory(new PropertyValueFactory<>("id"));

        lbl_creator.setCellValueFactory(new PropertyValueFactory<>("creatorName"));

        lbl_experimentName.setCellValueFactory(new PropertyValueFactory<>("experimentName"));

        lbl_currentVersion.setCellValueFactory(new PropertyValueFactory<>("version"));

        lbl_result.setCellValueFactory(new PropertyValueFactory<>("number_of_results"));

        lbl_createDate.setCellValueFactory(new PropertyValueFactory<>("created_date"));

        contentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, selectedExperiment) -> {
            this.selectedExperiment = selectedExperiment;
        });
        Callback<TableColumn<Experiment, String>, TableCell<Experiment, String>> cellFactory = new Callback<TableColumn<Experiment, String>, TableCell<Experiment, String>>() {
            @Override
            public TableCell<Experiment, String> call(final TableColumn<Experiment, String> param) {
                 TableCell<Experiment, String> cell = new TableCell<Experiment, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); //empty rows do not get buttons
                            setText(null);
                        } else {
                            FXMLLoader runloader = new FXMLLoader(getClass().getResource("/main/sensoryexperimentplatform/runButton.fxml"));
                            FXMLLoader editloader = new FXMLLoader(getClass().getResource("/main/sensoryexperimentplatform/editButton.fxml"));
                            FXMLLoader deleteloader = new FXMLLoader(getClass().getResource("/main/sensoryexperimentplatform/deleteButton.fxml"));
                            FXMLLoader resultLoader = new FXMLLoader(getClass().getResource("/main/sensoryexperimentplatform/resultButton.fxml"));
                            final Button delete;
                            final Button edit;
                            final Button run;
                            final Button result;
                            try {
                                run = runloader.load();
                                edit = editloader.load();
                                delete = deleteloader.load();
                                result = resultLoader.load();
//
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            run.setStyle(
                                    " -fx-cursor: hand ;"
                                            + "-glyph-size:28px;"
                                            +"-fx-background-color: transparent"
                            );
                            edit.setStyle(
                                    " -fx-cursor: hand ;"
                                            + "-glyph-size:28px;"
                                            + "-fx-background-color: transparent"
                            );
                            delete.setStyle(
                                    " -fx-cursor: hand ;"
                                            + "-glyph-size:28px;"
                                            + "-fx-background-color: transparent"
                            );
                            result.setStyle(
                                    " -fx-cursor: hand ;"
                                    + "-glyph-size:28px;"
                                    + "-fx-background-color: transparent"
                            );
                            result.setOnAction((ActionEvent event) -> {
                                selectedExperiment = getTableView().getItems().get(getIndex());
                                try{
                                    showResultEx(selectedExperiment);
                                } catch (Exception e){

                                }
                            });

                            delete.setOnAction((ActionEvent event) -> {
                                selectedExperiment = getTableView().getItems().get(getIndex());
                                try {
                                    deleteEx(selectedExperiment);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            edit.setOnAction((ActionEvent event) -> {
                                selectedExperiment = getTableView().getItems().get(getIndex());
                                try {
                                    editExperiment(selectedExperiment);
                                } catch (UnsupportedAudioFileException e) {
                                    throw new RuntimeException(e);
                                } catch (LineUnavailableException e) {
                                    throw new RuntimeException(e);
                                } catch (URISyntaxException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            run.setOnAction((ActionEvent) ->{
                                selectedExperiment = getTableView().getItems().get(getIndex());
                                try {
                                    FXMLLoader fillNameLoader = new FXMLLoader(getClass().getResource("/main/sensoryexperimentplatform/fill_name.fxml"));
                                    Parent root = fillNameLoader.load();

                                    FillNameController controller = fillNameLoader.getController();
                                    //use cloned object
                                    Experiment newE = new Experiment(selectedExperiment);
                                    FillName_VM viewModel = new FillName_VM(newE);
                                    controller.setViewModel(viewModel);

                                    Stage dialog = new Stage();
                                    dialog.initStyle(StageStyle.TRANSPARENT);
                                    Scene dialogScene = new Scene(root);
                                    dialogScene.setFill(Color.TRANSPARENT);
                                    dialog.setScene(dialogScene);

                                    dialog.showAndWait();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            HBox managebtn = new HBox(run, edit,delete, result);
                            managebtn.setStyle("-fx-alignment:center");
                            HBox.setMargin(run, new Insets(2, 2, 0, 3));
                            HBox.setMargin(edit, new Insets(2, 3, 0, 2));
                            HBox.setMargin(delete, new Insets(2, 4, 0, 2));
                            HBox.setMargin(result, new Insets(2, 5, 0, 2));
//                            HBox.setMargin(edit, new Insets(2, 5, 0, 3));
                            setGraphic(managebtn);
                        }
                    }
                };
                return cell;
            }
        };
        lbl_Option.setCellFactory(cellFactory);


        // Bind the TableView items to the ViewModel items
       // System.out.println(dashBoard_vm.getExperiments().size());
      //contentTable.setItems(dashBoard_vm.getExperiments());
     //   setupPagination();

    }
    private void setupPaginationListener() {
        dashBoard_vm.experimentsProperty().addListener((ListChangeListener<Experiment>) change -> {
            setupPagination();
        });
    }

    private void setupPagination() {
        int totalPages = (int) Math.ceil((double) dashBoard_vm.getExperiments().size() / ITEMS_PER_PAGE);

        pagination.setPageCount(totalPages);
        pagination.setLayoutX(dashBoardPane.getWidth() / 2.0 - pagination.getWidth() / 2.0);

        pagination.setPageFactory(this::createPage);
    }

    private TableView<Experiment> createPage(int pageIndex) {
        int start = pageIndex * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, dashBoard_vm.getExperiments().size());

        if(start > end) {
            contentTable.setItems(dashBoard_vm.getExperiments());
        } else {
            ObservableList<Experiment> itemsForPage = FXCollections.observableArrayList(
                    dashBoard_vm.getExperiments().subList(start, end)
            );
            contentTable.setItems(itemsForPage);
        }

        return contentTable;
    }

    private void deleteEx(Experiment e) throws Exception {
        deletedExp.push(e);
        listOfExperiment.deleteExperiment(e);
    }

    public void redo() throws Exception {
        if(!deletedExp.isEmpty()){
            listOfExperiment.addExperiment(deletedExp.pop());
        }
    }

    private void showResultEx(Experiment e) throws IOException {
        try {
            FXMLLoader showResult = new FXMLLoader(getClass().getResource("/main/sensoryexperimentplatform/ResultTableEx.fxml"));
            Parent root = showResult.load();

            ShowResultVM viewModel = new ShowResultVM(e);
            ShowResultController controller = showResult.getController();
            controller.setViewModel(viewModel);

            Stage stage = new Stage();
            stage.setTitle("Data");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Error loading FXML: " + ex.getMessage());
        }

    }

    private void editExperiment(Experiment c) throws UnsupportedAudioFileException, LineUnavailableException, URISyntaxException {
//        c.updateVersion();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main/sensoryexperimentplatform/EditExperiment.fxml"));
        Parent root = null;
        try{
            EditExpController controller = new EditExpController();
            root = loader.load();
            controller = loader.getController();
            controller.setExperiment(c);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Edit experiment");

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }


    @FXML
    void btn_addEx(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("NewExperiment.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

    }




}