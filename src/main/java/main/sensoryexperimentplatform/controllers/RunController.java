package main.sensoryexperimentplatform.controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage; // Explicit import for JavaFX Stage
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.viewmodel.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;
import java.util.concurrent.*;



public class RunController {
    private String uid;
    double processed = 0.0;

    private ScheduledExecutorService executorService;
    private long startTime, elapsedTime;
    @FXML
    private AnchorPane content;

    @FXML
    private Button btn_next;

    @FXML
    private Button btn_back;

    @FXML
    private Label elapsedTime_label;

    @FXML
    private ProgressBar progress_bar;

    @FXML
    private ListView<RunStages> listView;
    private Experiment experiment;


//    public void setViewModel(RunExperiment_VM viewModel){
//        this.viewModel = viewModel;
//        this.experiment = viewModel.getExperiment();
//        this.uid = viewModel.getUid();
//        System.out.println(experiment.getStages());
//        //viewModel.getFileName()+"_"+DataAccess.getCurrentFormattedTime();
//        startTimer();
//        bindViewModel();
//    }

    public void setExperiment(Experiment experiment, String uId) throws IOException {
        this.experiment = experiment;
        this.uid = uId;
        loadItems();
        startTimer();
        bindViewModel();
    }




    private void bindViewModel() throws IOException {

        //listView.itemsProperty().bind(viewModel.itemsProperty());
        listView.setVisible(false);

        // Add selection listener
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Handle item selection (e.g., show detail view)
                try {
                    showRunningPane(newValue);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        if (!listView.getItems().isEmpty()) {
            listView.getSelectionModel().selectFirst();
            showRunningPane(listView.getItems().get(0));
        }
    }
    @FXML
    void handleBtnBack(MouseEvent event) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        listView.getSelectionModel().select(selectedIndex - 1);
        //updateProgress(listView.getSelectionModel().getSelectedIndex());
    }

    private void showRunningPane(RunStages selectedItem) throws IOException {
        RunStages runStages = selectedItem;


        if (runStages == null) return;

        content.getChildren().clear();
        runStages.loadInterface(content);
        //setResponsive(content);
        listView.setVisible(true);

    }


    private void loadItems() {
        //Object selectedObject = viewModel.getObjectByKey(item);
        for(Object selectedObject : experiment.getStages()){
//            updateProgress(listView.getSelectionModel().getSelectedIndex());
//            if (selectedObject != null) {
//                int currentIndex = viewModel.getIndexOfObject(selectedObject);
//                if (currentIndex >= 0) {
//                    if (currentIndex > processed) {
//                        processed = currentIndex;
//                        updateProgress(currentIndex);
//                    }
//                } else {
//                    processed++;
//                    updateProgress(processed);
//                }
//                content.getChildren().clear();

                try {
                    if (selectedObject instanceof Start){
                        RunStartVM vm = new RunStartVM((Start) selectedObject);
                        //showRunningPane(vm);
                        listView.getItems().add(vm);
                        btn_next.textProperty().bind(vm.buttonProperty());
                    }
                    else if (selectedObject instanceof Vas) {
                        RunVas_VM vm = new RunVas_VM((Vas) selectedObject);
                        showRunningPane(vm);
                        listView.getItems().add(vm);
                        btn_next.textProperty().bind(vm.buttonProperty());

                        if (vm.conductedTextProperty().get() == null){
                            btn_next.setDisable(true);
                        }else btn_next.setDisable(false);

                        vm.conductedTextProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue != null) {
                                btn_next.setDisable(false);
                            }
                        });

                    }
                    // glms view display
                    else if (selectedObject instanceof gLMS) {
                        RunGLMS_VM vm = new RunGLMS_VM((gLMS) selectedObject);
                        showRunningPane(vm);
                        listView.getItems().add(vm);
                        btn_next.textProperty().bind(vm.buttonProperty());

                        if (vm.conductedTextProperty().get() == null){
                            btn_next.setDisable(true);
                        }else btn_next.setDisable(false);

                        vm.conductedTextProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue != null) {
                                btn_next.setDisable(false);
                            }
                        });


                    }
                    else if (selectedObject instanceof Notice) {
                        RunNotice_VM vm = new RunNotice_VM((Notice) selectedObject);
                        showRunningPane(vm);
                        listView.getItems().add(vm);
                        btn_next.setDisable(false);
                        btn_next.textProperty().bind(vm.buttonProperty());
                    }
                    else if (selectedObject instanceof Input) {
                        btn_next.setDisable(false);
                        RunInputVM vm = new RunInputVM((Input) selectedObject);
                        showRunningPane(vm);
                        listView.getItems().add(vm);
                        btn_next.textProperty().bind(vm.getButtonText());

                    }
                    else if (selectedObject instanceof Question) {
                        btn_next.setDisable(false);
                        RunQuestion_VM vm = new RunQuestion_VM((Question) selectedObject);
                        showRunningPane(vm);
                        listView.getItems().add(vm);
                    }
                    else if (selectedObject instanceof Timer) {
//                        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunTimer.fxml"));
//                        AnchorPane newContent = loader.load();
//                        setResponsive(newContent);
//                        content.getChildren().setAll(newContent);
//
//
//                        RunTimerController controller = loader.getController();
//                        RunTimer_VM viewModel = new RunTimer_VM((Timer) selectedObject);
//                        controller.setViewModel(viewModel);
//                        btn_back.setVisible(controller.getTimeLineCheck());
//                        btn_next.setVisible(controller.getTimeLineCheck());
//                        btn_next.setDisable(false);
//
//                        controller.timelineFullProperty().addListener(((observableValue, oldValue, newValue) ->{
//                            btn_back.setVisible(newValue);
//                            btn_next.setVisible(newValue);
//                            listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);
//                        } ));

                    }
                    else if (selectedObject instanceof AudibleInstruction) {

                        btn_next.setDisable(false);
                        RunAudible_VM vm = new RunAudible_VM((AudibleInstruction) selectedObject);
                        showRunningPane(vm);
                        listView.getItems().add(vm);
                        btn_next.textProperty().bind(vm.buttonProperty());

                    }
                    else if (selectedObject instanceof Course) {
                        RunCourseVM vm = new RunCourseVM((Course) selectedObject);
                        showRunningPane(vm);
                        listView.getItems().add(vm);
                        btn_next.textProperty().bind(vm.buttonProperty());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    private void setResponsive(AnchorPane newContent){
        AnchorPane.setTopAnchor(newContent, 0.0);
        AnchorPane.setBottomAnchor(newContent, 0.0);
        AnchorPane.setLeftAnchor(newContent, 0.0);
        AnchorPane.setRightAnchor(newContent, 0.0);
    }
    private void handleFinalNext() throws IOException {
        stopTimer();
        autoClose();
    }

    private void autoClose() {
        Stage stage = (Stage) content.getScene().getWindow();
        stopTimer();
        stage.close();
    }


    //timer tracks the experiment
    private void startTimer() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        startTime = System.currentTimeMillis();
        executorService.scheduleAtFixedRate(() ->{
            long currentTime = System.currentTimeMillis();
            elapsedTime = (currentTime - startTime) / 1000;
            experiment.elapsedTime = Math.toIntExact(elapsedTime);

            long minutes = experiment.elapsedTime / 60;
            long seconds = experiment.elapsedTime % 60;
            String formattedTime = String.format("%d:%02d", minutes, seconds);
            Platform.runLater(() -> elapsedTime_label.setText(formattedTime));
        }, 0, 1, TimeUnit.SECONDS);
    }
    //stop tracking time
    public void stopTimer() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
//    private void updateProgress(double processed){
//
//        progress_bar.setProgress(processed/(viewModel.count - 1));
//    }
    @FXML
    void handleBtnNext(MouseEvent event) throws IOException {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            listView.getSelectionModel().select(0);
        }
        if (selectedIndex >= 0 && selectedIndex < listView.getItems().size() - 1) {
            listView.getSelectionModel().select(selectedIndex + 1);
        }
        if(selectedIndex == listView.getItems().size() - 1){
            handleFinalNext();
        }
        DataAccess.quickSave(experiment, uid);
       // updateProgress(listView.getSelectionModel().getSelectedIndex());
    }

}