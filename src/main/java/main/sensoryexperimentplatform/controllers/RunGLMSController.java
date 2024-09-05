package main.sensoryexperimentplatform.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import main.sensoryexperimentplatform.viewmodel.GLMSStage_VM;

public class RunGLMSController {

    @FXML
    private Slider mySlider;
    @FXML
    private Label questionlbl;


    private GLMSStage_VM viewModel;



    public void setViewModel(GLMSStage_VM viewModel) {
        this.viewModel = viewModel;
        bindViewModel();


    }





    private void bindViewModel() {
        // Bind viewModel properties to the labels
        questionlbl.textProperty().bind(viewModel.txt_questionProperty());

        // Two-way binding between slider value and viewModel.sliderValueProperty()
        Bindings.bindBidirectional(mySlider.valueProperty(), viewModel.sliderValueProperty());

    }

}
