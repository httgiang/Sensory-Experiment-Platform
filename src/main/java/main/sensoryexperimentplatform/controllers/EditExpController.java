package main.sensoryexperimentplatform.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.models.Timer;
import main.sensoryexperimentplatform.viewmodel.noticeStage_VM;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.viewmodel.*;
import main.sensoryexperimentplatform.models.*;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class EditExpController {
    @FXML
    private Button btn_AddConditionalStatement;

    @FXML
    private Button btn_AddCourse;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btn_AddPeriodicStage;

    @FXML
    private Button btn_AddQuestionStage;
    @FXML
    private Button btn_addAudibleInstruction;

    @FXML
    private Button btn_addFoodAndTaste;

    @FXML
    private Button btn_addInput;

    @FXML
    private Button btn_addRatingContainer;

    @FXML
    private Button btn_addTasteTest;

    @FXML
    private Button btn_addTimer;

    @FXML
    private Button btn_addVasStage;

    @FXML
    private Button btn_assignSound;

    @FXML
    private AnchorPane propertiesPane;
    private Stack<ratingContainer_VM> rating;
    @FXML
    private TreeView<Stages> treeView;


    @FXML
    private Button btn_noticeStage;

    private TreeItem<Stages> startStage;

    private TreeItem<Stages> ratingContainerItems;
    private TreeItem<Stages> ifConditional;
    private TreeItem<Stages> elseConditional;

    private Stack<AddTasteVM> addTasteVMS;
    private Stack<AddCourseVM> addCourseVMS;
    private AudibleSound_VM selectAudibleSound_vm;
    private Experiment originalExperiment;
    private Experiment experiment;



    public void initialize() {
        initializeStack();

        initialDisablingButtons();

        setUpTreeViewListener();

        styleTreeView();
    }


    private void setUpTreeViewListener(){
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue != oldValue) {

                try {
                    addTasteVMS.clear();
                    addCourseVMS.clear();
                    btn_addFoodAndTaste.setDisable(true);
                    showPropertiesPane(newValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void initializeStack(){
        rating = new Stack<>();
        addTasteVMS = new Stack<>();
        addCourseVMS = new Stack<>();
    }
    private void initialDisablingButtons() {
        btn_assignSound.setDisable(true);
        btn_AddPeriodicStage.setDisable(true);
        btn_addFoodAndTaste.setDisable(true);
    }

    private void styleTreeView(){
        //THIS CODE IS TO MAKE THE TREE ITEM WHICH APPEAR DIFFER IN COLORS (ODD VS EVEN)
        treeView.setCellFactory(new Callback<TreeView<Stages>, TreeCell<Stages>>() {

            @Override
            public TreeCell<Stages> call(TreeView<Stages> param) {
                return new TreeCell<Stages>() {
                    @Override
                    protected void updateItem(Stages item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.toString());
                            // Apply the style conditionally
                            if (getTreeItem().getParent() != null && getTreeItem().getParent().getChildren().indexOf(getTreeItem()) % 2 != 0) {
                                setStyle("-fx-background-color: #F1F6FB; -fx-text-fill: black;");
                            } else {
                                setStyle("");
                            }
                        }
                    }
                };
            }
        });
    }

    //THREE RIGHT-SIDE BUTTONS
    @FXML
    void delete(ActionEvent event) {
        TreeItem<Stages> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TreeItem<Stages> parent = selectedItem.getParent();
            if (parent != null) {
                parent.getChildren().remove(selectedItem);
            } else {
                // If it's a root item, remove it from the TreeView directly
                treeView.getRoot().getChildren().remove(selectedItem);
            }
        }
    }
    @FXML
    void down(ActionEvent event) {
        TreeItem<Stages> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TreeItem<Stages> parent = selectedItem.getParent();
            if (parent != null) {
                int currentIndex = parent.getChildren().indexOf(selectedItem);
                System.out.println(currentIndex);
                if (currentIndex < parent.getChildren().size() - 1 && currentIndex >= 0) {
                    TreeItem<Stages> nextItem = parent.getChildren().get(currentIndex + 1);
                    parent.getChildren().set(currentIndex + 1, parent.getChildren().get(currentIndex));
                    parent.getChildren().set(currentIndex, nextItem);

                    Object next = experiment.getStages().get(currentIndex + 1);
                    System.out.println(next);
                    experiment.getStages().set(currentIndex + 1, experiment.getStages().get(currentIndex));
                    experiment.getStages().set(currentIndex, next);
                }
                 treeView.getSelectionModel().select(currentIndex + 1);
            }
        }
    }

    @FXML
    void up(ActionEvent event) {
        TreeItem<Stages> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TreeItem<Stages> parent = selectedItem.getParent();
            if (parent != null) {
                int currentIndex = parent.getChildren().indexOf(selectedItem);
                if (currentIndex < parent.getChildren().size() && currentIndex > 0) {
                    TreeItem<Stages> lastItem = parent.getChildren().get(currentIndex - 1);
                    parent.getChildren().set(currentIndex - 1, parent.getChildren().get(currentIndex));
                    parent.getChildren().set(currentIndex, lastItem);
                    Object last = experiment.getStages().get(currentIndex - 1);
                    System.out.println(last);
                    experiment.getStages().set(currentIndex - 1, experiment.getStages().get(currentIndex));
                    experiment.getStages().set(currentIndex, last);
                }
                treeView.getSelectionModel().select(currentIndex);
            }
        }
    }

//


    private void showPropertiesPane(TreeItem<Stages> selectedItem) throws IOException
    {//USED TO LOAD THE PROPERTIES PANE CORRESPONDING TO THE SELECTED ITEM

        Stages stages = selectedItem.getValue();
        if (stages == null) return;

        propertiesPane.getChildren().clear();
        propertiesPane.setVisible(true);
        treeView.setMaxHeight(311);

        stages.loadInterface(propertiesPane, addTasteVMS, addCourseVMS);
        stages.handleMenuButtons(propertiesPane, addTasteVMS, addCourseVMS, btn_AddPeriodicStage, btn_AddCourse, btn_assignSound,
                btn_addFoodAndTaste, btn_addAudibleInstruction
                , btn_addInput, btn_noticeStage,
                btn_addTimer, btn_AddQuestionStage,
                btn_addRatingContainer, btn_addTasteTest, btn_AddConditionalStatement, rating
        );


    }

    private void loadItems() throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException {
        ArrayList<Object> stages = experiment.getStages();

        if (experiment.getStages().isEmpty()) {
            Stages startVM = new StartVM(experiment);
            startStage = new TreeItem<>(startVM);
            treeView.setRoot(startStage);
         
        } else {
            Start newStart = experiment.getStart();
            StartVM startVM = new StartVM(newStart);
            startStage = new TreeItem<>(startVM);
            treeView.setRoot(startStage);


            for (Object o : stages) {

                if (o instanceof Vas) {
                    vasStage_VM vasStageVm = new vasStage_VM((Vas) o);
                    startStage.getChildren().add(new TreeItem<>(vasStageVm));

                } else if (o instanceof Notice) {
                    noticeStage_VM noticeStage_vm = new noticeStage_VM((Notice) o);
                    startStage.getChildren().add(new TreeItem<>(noticeStage_vm));

                } else if (o instanceof gLMS) {
                    GLMSStage_VM glmsStageVm = new GLMSStage_VM((gLMS) o);
                    startStage.getChildren().add(new TreeItem<>(glmsStageVm));

                } else if (o instanceof RatingContainer) {
                    ratingContainer_VM ratingContainerVm = new ratingContainer_VM((RatingContainer) o);
                    TreeItem<Stages> itemRating = new TreeItem<>(ratingContainerVm);
                    startStage.getChildren().add(itemRating);

                    for (Object subO : ((RatingContainer) o).container) {
                        if (subO instanceof Vas) {
                            vasStage_VM vasStageVm = new vasStage_VM((Vas) subO);
                            itemRating.getChildren().add(new TreeItem<>(vasStageVm));

                        } else if (subO instanceof gLMS) {
                            GLMSStage_VM glmsStageVm = new GLMSStage_VM((gLMS) subO);
                            itemRating.getChildren().add(new TreeItem<>(glmsStageVm));
                        }
                    }
                } else if (o instanceof Input) {
                    InputStage_VM inputStage_vm = new InputStage_VM(experiment,(Input) o);
                    startStage.getChildren().add(new TreeItem<>(inputStage_vm));
                } else if (o instanceof TasteTest) {
                    AddTasteVM inputStage_vm = new AddTasteVM((TasteTest) o);
                    startStage.getChildren().add(new TreeItem<>(inputStage_vm));
                } else if (o instanceof Course) {
                    AddCourseVM addCourseVM = new AddCourseVM((Course) o);
                    startStage.getChildren().add(new TreeItem<>(addCourseVM));
                } else if (o instanceof AudibleInstruction) {
                    AudibleSound_VM audibleSound_vm = new AudibleSound_VM((AudibleInstruction) o);
                    startStage.getChildren().add(new TreeItem<>(audibleSound_vm));
                } else if (o instanceof Timer) {
                    timerStage_VM timerStageVm = new timerStage_VM((Timer) o);
                    startStage.getChildren().add(new TreeItem<>(timerStageVm));
                }
            }
        }
        startStage.setExpanded(true);
    }



    @FXML
    void addAudibleInstruction(ActionEvent event) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException {

        AudibleSound_VM audibleSound_vm = new AudibleSound_VM(experiment);
        selectAudibleSound_vm = audibleSound_vm;

        addNewTreeItem(audibleSound_vm);
    }

    void addNewTreeItem(Stages stages){
        TreeItem<Stages> parent = treeView.getSelectionModel().getSelectedItem();
        if(ifConditional != null && parent == ifConditional){
            ifConditional.getChildren().add(new TreeItem<>(stages));
        } else if(elseConditional != null && parent == elseConditional){
            elseConditional.getChildren().add(new TreeItem<>(stages));
        } else if (ratingContainerItems!= null && parent == ratingContainerItems){
            ratingContainerItems.getChildren().add(new TreeItem<>(stages));
        } else {
            startStage.getChildren().add(new TreeItem<>(stages));
        }
        if(parent != null){
            parent.setExpanded(true);
        }

    }
    @FXML
    void addConditionalStatement(ActionEvent event) {

        conditionalStatementVM conditionalStatementVM = new conditionalStatementVM(experiment);

        ifConditional = new TreeItem<>(conditionalStatementVM);
        elseConditional = new TreeItem<>(conditionalStatementVM);
        startStage.getChildren().add(ifConditional);
        startStage.getChildren().add(elseConditional);

       }
//
   @FXML
    void addCourse(ActionEvent event) {
        AddCourseVM addCourseVM = new AddCourseVM(experiment);

        addNewTreeItem(addCourseVM);
     }
//
    @FXML
    void addFoodAndTaste(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("FoodAndTaste.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Food and Taste");
        TasteTest taste = addTasteVMS.get(0).getTastetest();
        FoodAndTasteController controller = fxmlLoader.getController();
        FoodTasteVM foodTasteVM = new FoodTasteVM(taste);
        controller.setViewModel(foodTasteVM);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }
//
    @FXML
    void addGLMSStage(ActionEvent event) {

        GLMSStage_VM glmsStage_VM;
        if (!rating.isEmpty()){
            glmsStage_VM = new  GLMSStage_VM(rating.get(0));
        }
        else {
            glmsStage_VM = new  GLMSStage_VM(experiment);
        }

        addNewTreeItem(glmsStage_VM);
    }

    @FXML
    void addInput(ActionEvent event) {

        InputStage_VM inputStage_vm = new InputStage_VM(experiment);
        addNewTreeItem(inputStage_vm);
    }

    @FXML
    void addNoticeStage(ActionEvent event) {
        noticeStage_VM noticeStage_vm = new noticeStage_VM(experiment);
        addNewTreeItem(noticeStage_vm);
    }


    @FXML
    void addPeriodicStage(ActionEvent event) {
        Course course = addCourseVMS.get(0).getCourse();
        //PeriodicVM periodicVM = new PeriodicVM();
        PeriodicVM periodicVM = new PeriodicVM(course);
     
        addNewTreeItem(periodicVM);
      }

    @FXML
    void addQuestionStage(ActionEvent event) {
        questionStage_VM questionStage_vm = new questionStage_VM(experiment);
        addNewTreeItem(questionStage_vm);
   }

    @FXML
    void addRatingContainer(ActionEvent event) throws IOException {
        ratingContainer_VM ratingContainer_vm = new ratingContainer_VM(experiment);
        ratingContainerItems = new TreeItem<>(ratingContainer_vm);
        startStage.getChildren().add(ratingContainerItems);
    }

    @FXML
    void addTasteTest(ActionEvent event) {
        AddTasteVM addTasteVM = new AddTasteVM(experiment);
        addNewTreeItem(addTasteVM);
    }

   @FXML
   void addTimer(ActionEvent event) {
        timerStage_VM timerStageVm = new timerStage_VM(experiment);
        addNewTreeItem(timerStageVm);

   }

   @FXML
   void addVasStage(ActionEvent event) {
        vasStage_VM vasStageVm ;
        if (!rating.isEmpty()){
            vasStageVm =  new vasStage_VM(rating.get(0));
        }
        else {
            vasStageVm = new vasStage_VM(experiment);
        }
        addNewTreeItem(vasStageVm);

    }

    @FXML
    void assignSound(ActionEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException {

        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AssignSound.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        AssignSoundController controller = fxmlLoader.getController();
        AssignSoundVM viewModel = new AssignSoundVM();

        controller.setViewModel(viewModel,selectAudibleSound_vm);
        stage.setTitle("Add Sound");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setExperiment(Experiment c) throws IOException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException {
        this.experiment = c;
        this.originalExperiment = experiment;
        loadItems();
    }






    @FXML
    void save(ActionEvent event) throws Exception {
        DataAccess.updateFile();
        this.experiment.version++;
    }

    @FXML
    void cancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        this.experiment = originalExperiment;
    }

    @FXML
    void saveAs() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("NewExperiment.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

    }

}