package main.sensoryexperimentplatform.controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage; // Explicit import for JavaFX Stage
import main.sensoryexperimentplatform.viewmodel.*;
import main.sensoryexperimentplatform.models.*;

import java.io.IOException;
import java.util.concurrent.*;

import static main.sensoryexperimentplatform.utilz.FeatureType.RUN;


public class RunController {
    @FXML
    private ListView<ViewModel> listView;
    private Experiment experiment; private String uid;
    int processed = 0; //index to keep track of how many stages are processed

    private ScheduledExecutorService executorService;
    private long startTime, elapsedTime;
    @FXML
    private AnchorPane content;

    @FXML
    private Button btn_next, btn_back;

    @FXML
    private Label elapsedTime_label;

    ModelVMRegistry registry;


    public void initRunExperiment(Experiment experiment, String uId) throws IOException {
        this.experiment = experiment;
        this.uid = uId;
        loadItems();
        startTimer();
        setListViewListener();
        initButtons();
    }


    private void buildList(ListView<ViewModel> listView, Model model, ModelVMRegistry registry){
        ViewModel stages = registry.getViewModel(model);
        if(stages != null){
            System.out.println(model);
            listView.getItems().add(stages);
        }

        if(model instanceof ModelContainer){
            for(Model children : ((ModelContainer) model).getChildren()){
                buildList(listView, children, registry);
            }

        }
    }
    private void loadItems() {
        for(Model selectedObject : experiment.getStages()){

            registry = ModelVMRegistry.getInstance();
            buildList(listView, selectedObject, registry);
//            if (selectedObject instanceof Start){
//                RunStartVM vm = new RunStartVM((Start) selectedObject);
//                listView.getItems().add(vm);;
//            }
//            else if (selectedObject instanceof Vas) {
//                RunVas_VM vm = new RunVas_VM((Vas) selectedObject);
//                listView.getItems().add(vm);
//            }
//            // glms view display
//            else if (selectedObject instanceof gLMS) {
//                RunGLMS_VM vm = new RunGLMS_VM((gLMS) selectedObject);
//                listView.getItems().add(vm);
//            }
//            else if (selectedObject instanceof Notice) {
//                registry = ModelVMRegistry.getInstance();
//                ViewModel stages = registry.getViewModel(selectedObject);
//                listView.getItems().add(stages);
//              //  RunNotice_VM vm = new RunNotice_VM((Notice) selectedObject);
//              //  listView.getItems().add(vm);
//            }
//            else if (selectedObject instanceof Input) {
//                RunInputVM vm = new RunInputVM((Input) selectedObject);
//                listView.getItems().add(vm);
//            }
//            else if (selectedObject instanceof Question) {
//                RunQuestion_VM vm = new RunQuestion_VM((Question) selectedObject);
//                listView.getItems().add(vm);
//            }
//            else if (selectedObject instanceof Timer) {
//                RunTimer_VM vm = new RunTimer_VM((Timer) selectedObject);
//                listView.getItems().add(vm);
//            }
//            else if (selectedObject instanceof AudibleInstruction) {
//                RunAudible_VM vm = new RunAudible_VM((AudibleInstruction) selectedObject);
//                listView.getItems().add(vm);
//            }
//            else if (selectedObject instanceof Course) {
//                RunCourseVM vm = new RunCourseVM((Course) selectedObject);
//                listView.getItems().add(vm);
//
//            }
        }
    }


    private void showRunningPane(ViewModel selectedItem) throws IOException {
        ViewModel runStages = selectedItem;
        if (runStages == null) return;

        content.getChildren().clear();
        runStages.loadRunInterface(content);
        runStages.handleRunButtons(btn_next, btn_back);

    }
    private void setListViewListener() throws IOException {
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
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

    private void initButtons(){
        btn_next.setWrapText(true);
        btn_next.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btn_next.setPrefHeight(Region.USE_COMPUTED_SIZE);
    }


    private void handleFinalNext() throws IOException {
        processed = 0;
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
    }
    @FXML
    void handleBtnBack(MouseEvent event) {
        int selectedIndex = listView.getSelectionModel().getSelectedIndex();
        listView.getSelectionModel().select(selectedIndex - 1);
    }

}