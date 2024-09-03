package main.sensoryexperimentplatform.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import main.sensoryexperimentplatform.viewmodel.VasStage_VM;

public class RunVasController {
    @FXML
    private Label highAnchor_label;
    @FXML
    private Label lowAnchor_label;
    @FXML
    private Label questionlbl;
    @FXML
    private Slider mySlider;
//    @FXML
//    private ImageView help_image;

    private VasStage_VM viewModel;

    public void setViewModel(VasStage_VM viewModel) {
        this.viewModel = viewModel;
        bindViewModel();

    }
    private void bindViewModel() {
        highAnchor_label.textProperty().bind(viewModel.highAnchorTextProperty());
        lowAnchor_label.textProperty().bind(viewModel.lowAnchorTextProperty());
        questionlbl.textProperty().bind(viewModel.questionTextProperty());
        mySlider.setMax(Integer.parseInt(viewModel.getHighAnchorValue()));
        mySlider.setMin(Integer.parseInt(viewModel.getLowAnchorValue()));

        // Binding hai chiều giữa mySlider.valueProperty() và viewModel.sliderValueProperty()
        Bindings.bindBidirectional(mySlider.valueProperty(), viewModel.sliderValueProperty());
    }

}
