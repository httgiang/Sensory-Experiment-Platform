package main.sensoryexperimentplatform.controllers;


import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage; // Explicit import for JavaFX Stage
import javafx.util.Duration;
import main.sensoryexperimentplatform.viewmodel.*;
import main.sensoryexperimentplatform.models.*;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.concurrent.*;


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
    private ImageView help_image;

    private Tooltip tooltip;


//    @FXML
//    private Label elapsedTime_label;

    ModelVMRegistry registry;


    public void initRunExperiment(Experiment experiment, String uId) throws IOException {
        this.experiment = experiment;
        this.uid = uId;
        loadItems();
//        startTimer();
        setListViewListener();
        initButtons();
        setupToolTip();


    }


    private void buildList(ListView<ViewModel> listView, Model model, ModelVMRegistry registry){
        ViewModel stages = registry.getViewModel(model);

        //RATING, TASTE TEST KH HIEN THI MAN HINH RUN NEN KHONG DD VO LISTVIEW, CHI ADD CON CUA TUI NO TH
        if(model instanceof Course){
            AddCourseVM courseVM = (AddCourseVM) registry.getViewModel(model);
            courseVM.initRunSetup(listView);
            return;
        }
        if(model instanceof ConditionalStatement){
            ConditionalStatementVM conditionalVM = (ConditionalStatementVM) registry.getViewModel(model);
            conditionalVM.initRunSetup(listView);
        }

        if(model instanceof ModelContainer){
            if(((ModelContainer) model).getChildren() != null){
                for(Model children : ((ModelContainer) model).getChildren()){
                    buildList(listView, children, registry);
                }
            }
            // RUN FOR IF CONDITIONAL STATEMENT ( CONDITION SE ADD SAU )
//            else if(((ConditionalStatement) model).getIfConditional() != null){
//                for(Model children : ((ConditionalStatement) model).getIfConditional()){
//                    buildList(listView, children, registry);
//                }
//
//            }
//            else if(((ConditionalStatement) model).getElseConditional() != null){
//                for(Model children : ((ConditionalStatement) model).getElseConditional()){
//                    buildList(listView, children, registry);
//                }
//            }

        }
        else {
            if(stages != null){
                if(model instanceof TimerStage_VM){
                    setupTimerListener(((TimerStage_VM) model).getRunController());
                }
                listView.getItems().add(stages);
            }

        }
    }
    private void loadItems() {
        for(Model selectedObject : experiment.getStages()) {
            registry = ModelVMRegistry.getInstance();
            buildList(listView, selectedObject, registry);
        }
    }


    private void showRunningPane(ViewModel selectedItem) throws IOException {
        ViewModel runStages = selectedItem;
        if (runStages == null) return;

        content.getChildren().clear();
        runStages.loadRunInterface(content);
        runStages.handleRunButtons(btn_next, btn_back,tooltip,help_image);


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
        if (!listView.getItems().isEmpty()) {;
            showRunningPane(listView.getItems().get(0));
        }
    }

    private void initButtons(){
        btn_next.setWrapText(true);
        btn_next.setPrefWidth(Region.USE_COMPUTED_SIZE);
        btn_next.setPrefHeight(Region.USE_COMPUTED_SIZE);
    }
    private void setupToolTip(){
    tooltip = new Tooltip("Help text here!");


        tooltip.setStyle(
                "-fx-background-color: #e3e2e2;\n" +
                        "    -fx-text-fill: #397E82;\n" +
                        "    -fx-font-size: 20px;\n" +
                        "    -fx-padding: 5px;\n" +
                        "    -fx-border-color: White;\n" +
                        "    -fx-border-width: 1px;\n" +
                        "    -fx-border-radius: 3px;"
        );
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setAutoHide(true);
        tooltip.setWrapText(true);
        tooltip.setMaxWidth(250);

        // Set listeners to show and hide tooltip on mouse enter and exit
        help_image.setOnMouseEntered(event -> showTooltip(help_image, tooltip));
        help_image.setOnMouseExited(event -> tooltip.hide());

        help_image.imageProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Image> observable, javafx.scene.image.Image oldImage, javafx.scene.image.Image newImage) {
                if (newImage != null) {
                    showTooltip(help_image, tooltip);
                }
            }
        });
    }

    private void showTooltip(ImageView imageView, Tooltip tooltip) {
        // Get the bounds of the ImageView
        javafx.geometry.Bounds bounds = imageView.localToScreen(imageView.getBoundsInLocal());

        // Show the tooltip at the top-left position of the ImageView
        tooltip.show(imageView, bounds.getMinX() - 250, bounds.getMinY() - tooltip.getHeight());
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


    private void setupTimerListener(RunTimerController runTimerController) {
        runTimerController.timelineFullProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1);
            }
        });
    }

    //timer tracks the experiment
//    private void startTimer() {
//        executorService = Executors.newSingleThreadScheduledExecutor();
//        startTime = System.currentTimeMillis();
//        executorService.scheduleAtFixedRate(() ->{
//            long currentTime = System.currentTimeMillis();
//            elapsedTime = (currentTime - startTime) / 1000;
//            experiment.elapsedTime = Math.toIntExact(elapsedTime);
//
//            long minutes = experiment.elapsedTime / 60;
//            long seconds = experiment.elapsedTime % 60;
//            String formattedTime = String.format("%d:%02d", minutes, seconds);
//            Platform.runLater(() -> elapsedTime_label.setText(formattedTime));
//        }, 0, 1, TimeUnit.SECONDS);
//    }
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