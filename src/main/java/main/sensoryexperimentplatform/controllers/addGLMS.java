package main.sensoryexperimentplatform.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.viewmodel.FoodTasteVM;

public class addGLMS {
    @FXML
    private Label FileLabel;

    @FXML
    private Label ImportSoundLabel;

    @FXML
    private AnchorPane ImportSoundPane;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_save;

    @FXML
    private TextField txt_file;
    private FoodTasteVM viewModel;
    private NotiForAddFood notiForAddFood;
    @FXML
    private void btn_save(ActionEvent event) {
        viewModel.addGLMSOptions(txt_file.getText());
        notiForAddFood.notifyObject();
        Stage currentStage = (Stage) btn_cancel.getScene().getWindow();
        currentStage.close();
    }
    @FXML
    private void btn_cancel(ActionEvent event) {
        Stage currentStage = (Stage) btn_cancel.getScene().getWindow();
        currentStage.close();
    }

    public void setViewModel(FoodTasteVM viewModel, FoodAndTasteController foodAndTasteController) {
        this.viewModel = viewModel;
        notiForAddFood = new NotiForAddFood(foodAndTasteController);
    }
}
