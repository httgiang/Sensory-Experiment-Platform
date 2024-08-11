package main.sensoryexperimentplatform.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.viewmodel.newEx_VM;

public class newExController {
    newEx_VM viewModel;

    @FXML
    private AnchorPane newExPane;


    @FXML
    private TextField txt_ExperimentName;


    @FXML
    private TextArea txt_description;

    @FXML
    private TextArea txt_additionNote;

    @FXML
    private TextField txt_creatorName;

    public void initialize(){
        viewModel = new newEx_VM();
        bindViewModel();

        AnchorPane.setTopAnchor(newExPane, 0.0);
        AnchorPane.setBottomAnchor(newExPane, 0.0);
        AnchorPane.setLeftAnchor(newExPane, 0.0);
        AnchorPane.setRightAnchor(newExPane, 0.0);
    }
    @FXML
    void btn_cancel(ActionEvent event) {

    }

    @FXML
    void btn_save(ActionEvent event) throws Exception {
        viewModel.saveItems();
        Stage currentStage = (Stage) newExPane.getScene().getWindow();
        currentStage.close();
    }

    public void bindViewModel(){
        txt_ExperimentName.textProperty().bindBidirectional(viewModel.ExperimentNameProperty());
        txt_creatorName.textProperty().bindBidirectional(viewModel.CreatorNameProperty());
        txt_additionNote.textProperty().bindBidirectional(viewModel.noteProperty());
        txt_description.textProperty().bindBidirectional(viewModel.DescriptionProperty());

    }
    public void setViewModel(newEx_VM viewModel){
        this.viewModel = viewModel;
    }

}
