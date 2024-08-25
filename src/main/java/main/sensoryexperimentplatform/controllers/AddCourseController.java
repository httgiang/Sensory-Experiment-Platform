package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.sensoryexperimentplatform.viewmodel.AddCourseVM;

public class AddCourseController {
    @FXML
    private TextField txt_button;

    @FXML
    private TextField txt_endStatement;

    @FXML
    private TextField txt_duration;

    @FXML
    private CheckBox checkbox_alert;

    @FXML
    private TextField txt_content;

    @FXML
    private TextArea txt_help;

    @FXML
    private TextField txt_quantity;

    @FXML
    private TextField txt_refill;

    @FXML
    private TextField txt_title;


    private AddCourseVM view;


    private void bind(){
        txt_content.textProperty().bindBidirectional(view.txt_contentProperty());
        txt_title.textProperty().bindBidirectional(view.txt_titleProperty());
        txt_refill.textProperty().bindBidirectional(view.txt_refillProperty());
        txt_quantity.textProperty().bindBidirectional(view.txt_quantityProperty());
        txt_button.textProperty().bindBidirectional(view.txt_buttonProperty());
        txt_help.textProperty().bindBidirectional(view.txt_helpProperty());
        txt_duration.textProperty().bindBidirectional(view.txt_durationProperty());
        txt_endStatement.textProperty().bindBidirectional(view.txt_endStatementProperty());
        checkbox_alert.selectedProperty().bindBidirectional(view.isAlertProperty());

        //txt_yes.textProperty().bindBidirectional(vasStageVM.txt_yesProperty());
    }
    public void setViewModel(AddCourseVM view){
        this.view = view;
        bind();
    }

}
