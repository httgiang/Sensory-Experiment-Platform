package main.sensoryexperimentplatform.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.models.Food;
import main.sensoryexperimentplatform.viewmodel.FoodTasteVM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodAndTasteController {
    private List <XCell> xcell;
    private List <XCell> glmscell;
    private List <XCell> vascell;
    @FXML
    private Label SoundSelectTionLabel;

    @FXML
    private Button btn_play;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_remove;

    @FXML
    private Button btn_stop;

    @FXML
    private ListView<String> foodView;
    @FXML
    private ListView<String> glmsTable;

    @FXML
    private ListView<String> vasTable;
    private FoodTasteVM viewModel;
    private ObservableList<String> foods;
    private ObservableList<String> glms;
    private ObservableList<String> vas;
    private int clickOnListView =1;
    static class XCell extends ListCell<String> {

        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        CheckBox button = new CheckBox();
        String lastItem;
        private String name;
        private Boolean isSelect;

        public String getName() {
            return name;
        }

        public void setSelect(Boolean select) {
            isSelect = select;
            button.setSelected(select);
        }

        public Boolean getSelect() {
            return isSelect;
        }

        public XCell(boolean boo) {
            super();
            this.isSelect = boo;
            button.setSelected(boo);
            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    isSelect = button.isSelected();
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
                label.setText(item!=null ? item : "<null>");
                setGraphic(hbox);
            }
        }
    }


    public void loadItems(){
        foods.clear();
        vas.clear();
        glms.clear();
        vasTable.getItems().clear();
        glmsTable.getItems().clear();
        foodView.getItems().clear();
        vas.addAll(viewModel.getListVasShow());
        glms.addAll(viewModel.getListGLMSShow());
        foods.addAll(viewModel.getListFoodsShow());
        foodView.getItems().addAll(foods);
        glmsTable.getItems().addAll(glms);
        vasTable.getItems().addAll(vas);
        foodView.setOnMouseClicked(event -> {
            clickOnListView =1;
            // Add your custom logic here
        });
        glmsTable.setOnMouseClicked(event -> {
            clickOnListView =2;
            // Add your custom logic here
        });
        vasTable.setOnMouseClicked(event -> {
            clickOnListView =3;
            // Add your custom logic here
        });
        foodView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {

                XCell xCell = new XCell(true);
                xcell.add(xCell);
                return xCell;
            }
        });

        glmsTable.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                XCell xCell = new XCell(false);
                glmscell.add(xCell);
                return xCell;
            }
        });
        vasTable.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                XCell xCell = new XCell(false);
                vascell.add(xCell);
                return xCell;
            }
        });


    }

    public void setViewModel(FoodTasteVM viewModel) {
        this.viewModel = viewModel;
        xcell = new ArrayList<>();
        vascell = new ArrayList<>();
        glmscell = new ArrayList<>();
        vas = FXCollections.observableArrayList();
        glms = FXCollections.observableArrayList();
        foods = FXCollections.observableArrayList();
        loadItems();
        System.out.println(viewModel.getListFoods());

    }

    @FXML
    void Add(ActionEvent event) throws IOException {
        if (clickOnListView == 1){
            FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddFood.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Food");
            addFoodController controller = fxmlLoader.getController();
            controller.setViewModel(viewModel, this);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }
        else if (clickOnListView ==2){
            FXMLLoader fxmlLoader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("AddGLMS.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add GLMS");
            addGLMS controller = fxmlLoader.getController();
            controller.setViewModel(viewModel, this);
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
            controller.setViewModel(viewModel, this);
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        }

    }

    @FXML
    void save(ActionEvent event) {
        for (XCell a:xcell){
            if (a.getSelect()){
                System.out.println("selected food " + a.getName());
                viewModel.addListFoods(a.getName());
               // JOptionPane.showMessageDialog(null,a.getName());
            }
        }
        for (XCell a: glmscell){

            if (a.getSelect()){
                System.out.println("selected glms " + a.getName());
                viewModel.addGLMS(a.getName());
            }
        }
        for (XCell a: vascell){

            if (a.getSelect()){
                System.out.println("select vas" + a.getName());
                viewModel.addVas(a.getName());
            }
        }



        Stage currentStage = (Stage) btn_play.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void SelectAll(ActionEvent event) {
        if (clickOnListView ==1) {
            for (XCell cell : xcell) {
                cell.button.setSelected(true);
                cell.setSelect(true);
            }
        }
        else if (clickOnListView ==2){
            for (XCell cell : glmscell) {
                cell.button.setSelected(true);
                cell.setSelect(true);
            }
        }
        else if (clickOnListView ==3){
            for (XCell cell : vascell) {
                cell.button.setSelected(true);
                cell.setSelect(true);
            }
        }

    }

    @FXML
    void SelectNone(ActionEvent event) {
        if (clickOnListView ==1) {
            for (XCell cell : xcell) {
                cell.button.setSelected(false);
                cell.setSelect(false);
            }
        }
        else if (clickOnListView ==2){
            for (XCell cell : glmscell) {
                cell.button.setSelected(false);
                cell.setSelect(false);
            }
        }
        else if (clickOnListView ==3){
            for (XCell cell : vascell) {
                cell.button.setSelected(false);
                cell.setSelect(false);
            }
        }
    }



}
