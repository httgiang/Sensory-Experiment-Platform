package main.sensoryexperimentplatform.controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.models.DataAccess;
import main.sensoryexperimentplatform.viewmodel.FoodTasteVM;

import java.io.IOException;
import java.util.*;

public class FoodAndTasteController {
    private List<Cell> xcell;
    private List<Cell> glmscell;
    private List<Cell> vascell;


    @FXML
    private Button btn_play;

    @FXML
    private ListView<String> foodsList;
    @FXML
    private ListView<String> glmsList;
    @FXML
    private ListView<String> vasList;

    private ListProperty<String> selectedFoods = new SimpleListProperty<>();
    private ListProperty<String> selectedGLMS = new SimpleListProperty<>();
    private ListProperty<String>  selectedVAS = new SimpleListProperty<>();


    private int clickOnListView = 1;


    private FoodTasteVM foodTasteVM;
    static class Cell extends ListCell<String> {

        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        CheckBox checkbox = new CheckBox();
        String lastItem;
        private String name;
        private Boolean isSelected;

        private ObservableList<String> selectedItems;

        public Cell(ObservableList<String> selectedItems) {
            super();
            this.selectedItems = selectedItems;

            hbox.getChildren().addAll(label, pane, checkbox);
            HBox.setHgrow(pane, Priority.ALWAYS);

            // Initialize the checkbox based on the list content
            checkbox.setOnAction(event -> {
                isSelected = checkbox.isSelected();
                if (isSelected) {
                    if (!selectedItems.contains(name)) {
                        selectedItems.add(name);
                    }
                } else {
                    selectedItems.remove(name);
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class

            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                name = item;
                label.setText(item != null ? item : "<null>");

                // Check if the item is in the selected list and update the checkbox
                checkbox.setSelected(selectedItems.contains(name));
                setGraphic(hbox);
            }
        }

        public void setSelect(boolean b) {
            isSelected = b;
        }

        public boolean getSelect() {
            return isSelected;
        }

        public String getName() {
            return name;
        }
    }



    private void bindingAllLists(){
        foodsList.itemsProperty().bindBidirectional(foodTasteVM.foodOptionsProperty());
        glmsList.itemsProperty().bindBidirectional(foodTasteVM.gLMSOptionsProperty());
        vasList.itemsProperty().bindBidirectional(foodTasteVM.vasOptionsProperty());

        selectedFoods.bindBidirectional(foodTasteVM.selectedFoodsProperty());
        selectedVAS.bindBidirectional(foodTasteVM.selectedVASProperty());
        selectedGLMS.bindBidirectional(foodTasteVM.selectedGLMSProperty());
    }

    private void initCells(){
        foodsList.setCellFactory(listView -> new Cell(selectedFoods));
        glmsList.setCellFactory(listView -> new Cell(selectedGLMS));
        vasList.setCellFactory(listView -> new Cell(selectedVAS));
    }

    public void loadItems(){
        bindingAllLists();

        foodsList.setOnMouseClicked(event -> {
            clickOnListView = 1; //CLICK ON FOODS
        });
        glmsList.setOnMouseClicked(event -> {
            clickOnListView = 2; //CLICK ON VAS
        });
        vasList.setOnMouseClicked(event -> {
            clickOnListView = 3; //CLICK ON GLMS
        });

        initCells();
    }

    public void setViewModel(FoodTasteVM foodTasteVM) {
        this.foodTasteVM = foodTasteVM;
        xcell = new ArrayList<>();
        vascell = new ArrayList<>();
        glmscell = new ArrayList<>();
        loadItems();

    }

    @FXML
    void Add(ActionEvent event) throws IOException {
        if (clickOnListView == 1){
            FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddFood.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Food");
            addFoodController controller = fxmlLoader.getController();
            controller.setViewModel(foodTasteVM);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
        else if (clickOnListView ==2){
            FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddGLMS.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add VAS");
            addGLMS controller = fxmlLoader.getController();
            controller.setViewModel(foodTasteVM);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
        else if (clickOnListView ==3){
            FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddVas.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add GLMS");
            addVas controller = fxmlLoader.getController();
            controller.setViewModel(foodTasteVM);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }

    }

    @FXML
    void save(ActionEvent event) throws Exception {
        DataAccess.updateFile();

        Stage currentStage = (Stage) btn_play.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void SelectAll(ActionEvent event) {
        if (clickOnListView ==1) {
            for (Cell cell : xcell) {
                cell.checkbox.setSelected(true);
                cell.setSelect(true);
            }
        }
        else if (clickOnListView ==2){
            for (Cell cell : glmscell) {
                cell.checkbox.setSelected(true);
                cell.setSelect(true);
            }
        }
        else if (clickOnListView ==3){
            for (Cell cell : vascell) {
                cell.checkbox.setSelected(true);
                cell.setSelect(true);
            }
        }

    }

    @FXML
    void SelectNone(ActionEvent event) {
        if (clickOnListView ==1) {
            for (Cell cell : xcell) {
                cell.checkbox.setSelected(false);
                cell.setSelect(false);
            }
        }
        else if (clickOnListView ==2){
            for (Cell cell : glmscell) {
                cell.checkbox.setSelected(false);
                cell.setSelect(false);
            }
        }
        else if (clickOnListView ==3){
            for (Cell cell : vascell) {
                cell.checkbox.setSelected(false);
                cell.setSelect(false);
            }
        }
    }



}
