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
import main.sensoryexperimentplatform.viewmodel.*;
import main.sensoryexperimentplatform.models.*;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import static main.sensoryexperimentplatform.utilz.PopUpType.*;

public class EditExpController {

    //THE CURRENT EXPERIMENT LOADED
    private Experiment experiment;


    //PROPERTY PANE TO LOAD PROPERTIES OF EACH STAGE
    @FXML
    private AnchorPane propertiesPane;

    //MENU BUTTONS ON THE LEFT SIDE
    @FXML
    private Button btn_AddConditionalStatement, btn_AddCourse, btn_AddPeriodicStage,
            btn_AddQuestionStage, btn_addAudibleInstruction, btn_addFoodAndTaste,
            btn_addInput, btn_addRatingContainer, btn_addTasteTest, btn_addTimer,
            btn_assignSound, btn_noticeStage ;

    //TREE VIEW TO DISPLAY THE ViewModel OF THE EXP
    @FXML
    private TreeView<ViewModel> treeView;

    //TREE ITEM THAT HAS CHILDREN
    private TreeItem<ViewModel> startStage, ratingContainerItems, ifConditional, elseConditional, courseItem;

    //CANCEL BUTTON
    @FXML
    private Button btnCancel;

    private Stage ownerStage;


    public void initialize() {

        initialDisablingButtons();

        setUpTreeViewListener();

        styleTreeView();
    }

    public void setOwnerStage(Stage ownerStage){
        this.ownerStage = ownerStage;
    }

    private void setUpTreeViewListener(){
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue != oldValue) {

                try {
                    showPropertiesPane(newValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void initialDisablingButtons() {
        btn_assignSound.setDisable(true);
        btn_AddPeriodicStage.setDisable(true);
        btn_addFoodAndTaste.setDisable(true);
    }

    private void styleTreeView(){
        //THIS CODE IS TO MAKE THE TREE ITEM WHICH APPEAR DIFFER IN COLORS (ODD VS EVEN)
        treeView.setCellFactory(new Callback<TreeView<ViewModel>, TreeCell<ViewModel>>() {

            @Override
            public TreeCell<ViewModel> call(TreeView<ViewModel> param) {
                return new TreeCell<ViewModel>() {
                    @Override
                    protected void updateItem(ViewModel item, boolean empty) {
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



    private void showPropertiesPane(TreeItem<ViewModel> selectedItem) throws IOException
    {//USED TO LOAD THE PROPERTIES PANE CORRESPONDING TO THE SELECTED ITEM

        ViewModel ViewModel = selectedItem.getValue();
        if (ViewModel == null) return;

        propertiesPane.getChildren().clear();
        propertiesPane.setVisible(true);
        treeView.setMaxHeight(311);

        ViewModel.loadEditInterface(propertiesPane);
        ViewModel.handleEditButtons(btn_AddPeriodicStage, btn_AddCourse, btn_assignSound,
                btn_addFoodAndTaste, btn_addAudibleInstruction
                , btn_addInput, btn_noticeStage,
                btn_addTimer, btn_AddQuestionStage,
                btn_addRatingContainer, btn_addTasteTest, btn_AddConditionalStatement
        );


    }


    private void loadItems(){
        ArrayList<Model> stages = experiment.getStages();
        if (experiment.getStages().isEmpty()) {
            ViewModel startVM = new StartVM(experiment);
            startStage = new TreeItem<>(startVM);
            treeView.setRoot(startStage);

        } else {
            Start newStart = experiment.getStart();
            StartVM startVM = new StartVM(newStart);
            startStage = new TreeItem<>(startVM);
            treeView.setRoot(startStage);

            ModelVMRegistry registry = ModelVMRegistry.getInstance();

            for (Model model : stages) {
                buildTree(startStage, model, registry);
            }

            startStage.setExpanded(true);
        }
    }

    private void buildTree(TreeItem<ViewModel> parent, Model model, ModelVMRegistry registry) {
        if(model instanceof Start){
            return;
        }
        //ADD TREE CON CHO CONDITIONAL STATEMENT
        if(model instanceof ConditionalStatement){
            IfConditionalStatementVM ifVM = new IfConditionalStatementVM((ConditionalStatement) model);
            ElseConditionalStatementVM elseVM = new ElseConditionalStatementVM((ConditionalStatement) model);
            TreeItem<ViewModel> ifVMTreeItem = new TreeItem<>(ifVM);
            TreeItem<ViewModel> elseVMTreeItem = new TreeItem<>(elseVM);
            parent.getChildren().add(ifVMTreeItem);
            parent.getChildren().add(elseVMTreeItem);
            if(((ConditionalStatement) model).getIfConditional()!= null){
                for(Model child : ((ConditionalStatement) model).getIfConditional()){

                    buildTree(ifVMTreeItem, child, registry);
                }
                for(Model child : ((ConditionalStatement) model).getElseConditional()){
                    buildTree(elseVMTreeItem, child, registry);
                }
            }
        }


        ViewModel stage = registry.getViewModel(model);
        if(stage != null){

            TreeItem<ViewModel> item = new TreeItem<>(stage);
            parent.getChildren().add(item);

            //NEU LA CONDITIONAL STATEMENT, COURSE, RATING CONTAINER -> ADD TREE CON
            if(model instanceof ModelContainer && (!(model instanceof TasteTest))) {
                if((!((ModelContainer) model).getChildren().isEmpty())){
                    for(Model child : ((ModelContainer) model).getChildren()) {
                        buildTree(item, child, registry);
                    }
                }
            }

        }
    }

    void addNewTreeItem(ViewModel vm){
        TreeItem<ViewModel> parent = treeView.getSelectionModel().getSelectedItem();
        if(parent != null && parent.getValue() instanceof IfConditionalStatementVM){
            ConditionalStatement conditionalStatement = (ConditionalStatement) parent.getValue().getModel();
            parent.getChildren().add(new TreeItem<>(vm));
            conditionalStatement.addIf(vm.getModel());
            parent.setExpanded(true);
        } else if (parent != null && parent.getValue() instanceof ElseConditionalStatementVM){
            ConditionalStatement conditionalStatement = (ConditionalStatement) parent.getValue().getModel();
            parent.getChildren().add(new TreeItem<>(vm));
            conditionalStatement.addElse(vm.getModel());
            parent.setExpanded(true);
        } else if(parent != null && parent.getValue().getModel() instanceof ModelContainer) {
            Model selected = parent.getValue().getModel();

            if (!(selected instanceof TasteTest)) {
                parent.getChildren().add(new TreeItem<>(vm));
                ((ModelContainer) selected).addChildren(vm.getModel());

            }
            parent.setExpanded(true);
        } else {
            experiment.addStage(vm.getModel());
            startStage.getChildren().add(new TreeItem<>(vm));
            startStage.setExpanded(true);
        }
    }

//    void addNewTreeItem(ViewModel vm){
//        TreeItem<ViewModel> parent = treeView.getSelectionModel().getSelectedItem();
//        if(ifConditional != null && parent == ifConditional){
//            ifConditional.getChildren().add(new TreeItem<>(vm));
//        } else if(elseConditional != null && parent == elseConditional){
//            elseConditional.getChildren().add(new TreeItem<>(vm));
//        } else if (ratingContainerItems!= null && parent == ratingContainerItems) {
//            ratingContainerItems.getChildren().add(new TreeItem<>(vm));
//        } else if (courseItem != null && parent == courseItem){
//            courseItem.getChildren().add(new TreeItem<>(vm));
//        } else {
//            startStage.getChildren().add(new TreeItem<>(vm));
//        }
//        if(parent != null){
//            parent.setExpanded(true);
//        }
//
//    }
    @FXML
    void addAudibleInstruction(ActionEvent event) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException {
        AudibleSound_VM audibleSound_vm = new AudibleSound_VM();
//        AudibleSound_VM audibleSound_vm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            audibleSound_vm =  new AudibleSound_VM((IfConditionalStatementVM) selectedItem.getValue());}
//
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            audibleSound_vm =  new AudibleSound_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            audibleSound_vm = new AudibleSound_VM(experiment);
//        }
        addNewTreeItem(audibleSound_vm);
    }
//    @FXML
//    void addAudibleInstruction(ActionEvent event) throws UnsupportedAudioFileException, LineUnavailableException, IOException, URISyntaxException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException {
//        AudibleSound_VM audibleSound_vm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            audibleSound_vm =  new AudibleSound_VM((IfConditionalStatementVM) selectedItem.getValue());}
//
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            audibleSound_vm =  new AudibleSound_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            audibleSound_vm = new AudibleSound_VM(experiment);
//        }
//        addNewTreeItem(audibleSound_vm);
//    }

    @FXML
    void addConditionalStatement(ActionEvent event) {

        ConditionalStatementVM conditionalStatementVM = new ConditionalStatementVM();
        ConditionalStatement conditionalStatement = conditionalStatementVM.getConditionalStatement();

        IfConditionalStatementVM ifConditionalStatement = new IfConditionalStatementVM(conditionalStatement);
        ElseConditionalStatementVM elseConditionalStatement = new ElseConditionalStatementVM(conditionalStatement);
        addNewTreeItem(ifConditionalStatement);
        addNewTreeItem(elseConditionalStatement);
//        ifConditional = new TreeItem<>(ifConditionalStatement);
//        elseConditional = new TreeItem<>(elseConditionalStatement);
//        startStage.getChildren().add(ifConditional);
//        startStage.getChildren().add(elseConditional);

       }


   @FXML
    void addCourse(ActionEvent event) {
        AddCourseVM addCourseVM = new AddCourseVM();
        addNewTreeItem(addCourseVM);
     }

    @FXML
    void addFoodAndTaste(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("FoodAndTaste.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Food and Taste");

        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();

        AddTasteVM tasteTestVM = (AddTasteVM) selectedItem.getValue();
        TasteTest taste = tasteTestVM.getModel();
        FoodAndTasteController controller = fxmlLoader.getController();
        FoodTasteVM foodTasteVM = new FoodTasteVM(taste);
        controller.setViewModel(foodTasteVM);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

//    @FXML
//    void addGLMSStage(ActionEvent event) {
//        GLMSStage_VM glmsStage_VM;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof RatingContainer_VM){
//            glmsStage_VM = new GLMSStage_VM((RatingContainer_VM) selectedItem.getValue());
//        } else if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            glmsStage_VM = new GLMSStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            glmsStage_VM = new GLMSStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else {
//            glmsStage_VM = new GLMSStage_VM(experiment);
//        }
//        addNewTreeItem(glmsStage_VM);
//    }
//
//    @FXML
//    void addInput(ActionEvent event) {
//        InputStage_VM inputStage_vm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            inputStage_vm = new InputStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            inputStage_vm = new InputStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            inputStage_vm = new InputStage_VM(experiment);
//        }
//        addNewTreeItem(inputStage_vm);
//    }
//
//    @FXML
//    void addNoticeStage(ActionEvent event) {
//        NoticeStage_VM noticeStage_vm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            noticeStage_vm = new NoticeStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            noticeStage_vm = new NoticeStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            noticeStage_vm = new NoticeStage_VM(experiment);
//        }
//        addNewTreeItem(noticeStage_vm);
//    }


    @FXML
    void addGLMSStage(ActionEvent event) {
        GLMSStage_VM glmsStage_VM = new GLMSStage_VM();
//        GLMSStage_VM glmsStage_VM;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof RatingContainer_VM){
//            glmsStage_VM = new GLMSStage_VM((RatingContainer_VM) selectedItem.getValue());
//        } else if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            glmsStage_VM = new GLMSStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            glmsStage_VM = new GLMSStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else {
//            glmsStage_VM = new GLMSStage_VM(experiment);
//        }
        addNewTreeItem(glmsStage_VM);
    }

    @FXML
    void addInput(ActionEvent event) {
        InputStage_VM inputStage_vm = new InputStage_VM();
//        InputStage_VM inputStage_vm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//
//        InputStage_VM inputStage_vm = new InputStage_VM();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            inputStage_vm = new InputStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            inputStage_vm = new InputStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            inputStage_vm = new InputStage_VM(experiment);
//        }
        addNewTreeItem(inputStage_vm);
    }

    @FXML
    void addNoticeStage(ActionEvent event) {
        NoticeStage_VM noticeStage_vm = new NoticeStage_VM();
//        NoticeStage_VM noticeStage_vm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            noticeStage_vm = new NoticeStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            noticeStage_vm = new NoticeStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            noticeStage_vm = new NoticeStage_VM(experiment);
//        }
        addNewTreeItem(noticeStage_vm);
    }


    @FXML
    void addPeriodicStage(ActionEvent event) {
//        Course course = addCourseVMS.get(0).getCourse();
//        //PeriodicVM periodicVM = new PeriodicVM();
//        PeriodicVM periodicVM = new PeriodicVM(course);
//
//        addNewTreeItem(periodicVM);
      }

    @FXML
    void addQuestionStage(ActionEvent event) {
//        QuestionStage_VM questionStage_vm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            questionStage_vm = new QuestionStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            questionStage_vm = new QuestionStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            questionStage_vm = new QuestionStage_VM(experiment);
//
//        }
        QuestionStage_VM questionStage_vm = new QuestionStage_VM();
        addNewTreeItem(questionStage_vm);
   }

    @FXML
    void addRatingContainer(ActionEvent event) throws IOException {
        RatingContainer_VM ratingContainer_vm = new RatingContainer_VM();
        addNewTreeItem(ratingContainer_vm);
//        ratingContainerItems = new TreeItem<>(ratingContainer_vm);
//        startStage.getChildren().add(ratingContainerItems);
    }
//        RatingContainer_VM ratingContainer_vm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            ratingContainer_vm = new RatingContainer_VM((IfConditionalStatementVM) selectedItem.getValue());
//            ratingContainerItems = new TreeItem<>(ratingContainer_vm);
//            ifConditional.getChildren().add(ratingContainerItems);
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            ratingContainer_vm = new RatingContainer_VM((ElseConditionalStatementVM) selectedItem.getValue());
//            ratingContainerItems = new TreeItem<>(ratingContainer_vm);
//            elseConditional.getChildren().add(ratingContainerItems);
//        }
//        else {
//            ratingContainer_vm = new RatingContainer_VM(experiment);
//            ratingContainerItems = new TreeItem<>(ratingContainer_vm);
//            startStage.getChildren().add(ratingContainerItems);
//        }

    @FXML
    void addTasteTest(ActionEvent event) {
  //      AddTasteVM addTasteVM;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            addTasteVM = new AddTasteVM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            addTasteVM = new AddTasteVM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            addTasteVM = new AddTasteVM(experiment);
//        }
        AddTasteVM addTasteVM = new AddTasteVM();
        addNewTreeItem(addTasteVM);
    }

   @FXML
   void addTimer(ActionEvent event) {
       TimerStage_VM timerStageVm = new TimerStage_VM();
//        TimerStage_VM timerStageVm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            timerStageVm = new TimerStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            timerStageVm = new TimerStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else{
//            timerStageVm = new TimerStage_VM(experiment);
//        }

        addNewTreeItem(timerStageVm);

   }

   @FXML
   void addVasStage(ActionEvent event) {
        VasStage_VM vasStageVm = new VasStage_VM();
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof RatingContainer_VM){
//            vasStageVm = new VasStage_VM((RatingContainer_VM) selectedItem.getValue());
//        } else {
//            vasStageVm = new VasStage_VM(experiment);
//        }
//        VasStage_VM vasStageVm;
//        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
//        if(selectedItem.getValue() instanceof RatingContainer_VM){
//            vasStageVm = new VasStage_VM((RatingContainer_VM) selectedItem.getValue());
//        }
//        else if(selectedItem.getValue() instanceof IfConditionalStatementVM){
//            vasStageVm = new VasStage_VM((IfConditionalStatementVM) selectedItem.getValue());
//        }
//        else if (selectedItem.getValue() instanceof ElseConditionalStatementVM){
//            vasStageVm = new VasStage_VM((ElseConditionalStatementVM) selectedItem.getValue());
//        }
//        else {
//            vasStageVm = new VasStage_VM(experiment);
//        }
        addNewTreeItem(vasStageVm);

    }

    @FXML
    void assignSound(ActionEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException {

        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
        AudibleSound_VM audible = null;
        audible = (AudibleSound_VM) selectedItem.getValue();
        AudibleInstruction audibleInstruction = audible.getAudibleIntruction();
        FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AssignSound.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        AssignSoundController controller = fxmlLoader.getController();
        AssignSoundVM viewModel = new AssignSoundVM(audibleInstruction);
        controller.setViewModel(viewModel);
        stage.setTitle("Add Sound");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public void setExperiment(Experiment c) throws IOException, UnsupportedAudioFileException, LineUnavailableException, URISyntaxException {
        this.experiment = c;
        //this.originalExperiment = experiment;
        loadItems();
    }




    //THREE RIGHT-SIDE BUTTONS
    @FXML
    void delete(ActionEvent event) throws Exception {
        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == startStage){
            PopUpVM popUpError = new PopUpVM(ERROR, "You cannot delete Start stage", experiment, ownerStage);
            return;
        }
        if (selectedItem != null){
            TreeItem<ViewModel> parent = selectedItem.getParent();
            if (parent != null) {
                int currentIndex = parent.getChildren().indexOf(selectedItem);

               // PopUpVM popUpConfirm = new PopUpVM(CONFIRM, "Are you sure you want to delete this stage?", experiment);
                Object curr = experiment.getStages().get(currentIndex);
                parent.getChildren().remove(selectedItem);
                experiment.getStages().remove(curr);
            }
        }
    }
    @FXML
    void down(ActionEvent event) throws IOException {
        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TreeItem<ViewModel> parent = selectedItem.getParent();
            if(selectedItem == startStage){
                PopUpVM popUpError = new PopUpVM(ERROR, "You cannot move Start stage", experiment, ownerStage);
                return;
            }

            if (parent != null) {
                int currentIndex = parent.getChildren().indexOf(selectedItem);

                if(currentIndex == parent.getChildren().size() - 1){//last item
                    PopUpVM popUpError = new PopUpVM(ERROR, "You cannot move stage out of experiment", experiment, ownerStage);
                    return;
                }
                if (currentIndex < parent.getChildren().size() - 1 && currentIndex >= 0) {
                    TreeItem<ViewModel> nextItem = parent.getChildren().get(currentIndex + 1);
                    parent.getChildren().set(currentIndex + 1, parent.getChildren().get(currentIndex));
                    parent.getChildren().set(currentIndex, nextItem);

                    Model next = experiment.getStages().get(currentIndex + 1);
                    experiment.getStages().set(currentIndex + 1, experiment.getStages().get(currentIndex));
                    experiment.getStages().set(currentIndex, next);
                }
                treeView.getSelectionModel().select(currentIndex + 1);
            }
        }
    }

    @FXML
    void up(ActionEvent event) throws IOException {
        TreeItem<ViewModel> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TreeItem<ViewModel> parent = selectedItem.getParent();
            if (selectedItem == startStage) {
                PopUpVM popUpError = new PopUpVM(ERROR, "You cannot move stage out of experiment", experiment, ownerStage);
                return;
            }
            if (parent != null) {
                int currentIndex = parent.getChildren().indexOf(selectedItem);
                if (currentIndex == 0) {
                    PopUpVM popUpError = new PopUpVM(ERROR, "You cannot switch stage with Start", experiment, ownerStage);
                    return;
                }
                if (currentIndex < parent.getChildren().size() && currentIndex > 0) {
                    TreeItem<ViewModel> lastItem = parent.getChildren().get(currentIndex - 1);
                    parent.getChildren().set(currentIndex - 1, parent.getChildren().get(currentIndex));
                    parent.getChildren().set(currentIndex, lastItem);
                    Model last = experiment.getStages().get(currentIndex - 1);
                    experiment.getStages().set(currentIndex - 1, experiment.getStages().get(currentIndex));
                    experiment.getStages().set(currentIndex, last);
                }
                treeView.getSelectionModel().select(currentIndex);
            }
        }
    }


    @FXML
    void save(ActionEvent event) throws Exception {
        DataAccess.updateFile();
        this.experiment.version++;

        PopUpVM popUpSuccess = new PopUpVM(SUCCESS, "You successfully saved the experiment!", experiment, ownerStage);
    }

    @FXML
    void cancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        //this.experiment = originalExperiment;
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