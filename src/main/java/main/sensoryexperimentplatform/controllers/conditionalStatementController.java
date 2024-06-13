package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import main.sensoryexperimentplatform.viewmodel.conditionalStatementVM;

public class conditionalStatementController {
    conditionalStatementVM viewModel;

    @FXML
    private RadioButton cbx_value2;

    @FXML
    private RadioButton cbx_variable1;

    @FXML
    private RadioButton cbx_variable2;


    @FXML
    private ComboBox<String> chx_variable1Choice;

    @FXML
    private ComboBox<String> chx_variable2Choice;
    @FXML
    private ComboBox<String> chx_compare;

    @FXML
    private TextField txt_value1Text;

    @FXML
    private TextField txt_value2Text;

    private void bind(){
        txt_value1Text.textProperty().bindBidirectional(viewModel.Value1TextProperty());
        txt_value2Text.textProperty().bindBidirectional(viewModel.Value2TextProperty());
        chx_variable1Choice.valueProperty().bindBidirectional(viewModel.Variable1ChoiceProperty());
        chx_variable2Choice.valueProperty().bindBidirectional(viewModel.Variable2ChoiceProperty());
        chx_compare.valueProperty().bindBidirectional(viewModel.compareProperty());

    }

    public void setViewModel(conditionalStatementVM viewModel) {
        this.viewModel = viewModel;
        chx_variable1Choice.getItems().addAll("Scale Reading", "Bout Number","Total Eaten Over Course","Total Eaten Over Experiment");
        chx_variable2Choice.getItems().addAll("Scale Reading", "Bout Number","Total Eaten Over Course","Total Eaten Over Experiment");
        chx_compare.getItems().addAll("Not equal to (!=)", "Greater than (>)","Less Than (<)","equals (=)","Less than or equals to(<=)","Less than or equals to(=>)");
        bind();

    }
}
