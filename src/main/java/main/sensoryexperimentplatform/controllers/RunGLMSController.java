package main.sensoryexperimentplatform.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import main.sensoryexperimentplatform.viewmodel.GLMSStage_VM;

public class RunGLMSController {

    @FXML
    private Label barelyDetectableLabel;
    @FXML
    private Label moderateLabel;
    @FXML
    private Label noSensationLabel;
    @FXML
    private Label strongLabel;
    @FXML
    private Label strongestImaginableLabel;
    @FXML
    private Label veryStrongLabel;
    @FXML
    private Label weakLabel;
    @FXML
    private Slider mySlider;
    @FXML
    private Label questionlbl;

    private GLMSStage_VM viewModel;

    @FXML
    private void initialize() {
        // Add listener to update label positions when slider size changes
        mySlider.heightProperty().addListener((obs, oldHeight, newHeight) -> updateLabelPositions());
        mySlider.widthProperty().addListener((obs, oldWidth, newWidth) -> updateLabelPositions());
    }


    private void updateLabelPositions() {
        double sliderHeight = mySlider.getHeight();

        // Example: Keep the labels at a fixed X position or near the slider
        // Get the X position of the slider
        double sliderX = mySlider.getLayoutX();
        // Or use the slider's X position dynamically
        // double labelX = mySlider.getLayoutX() - 50;  // Adjust based on slider's position

        // Position each label based on a percentage of the slider height for vertical placement
        noSensationLabel.setLayoutY(sliderHeight * ((100-0)/100)); // Near the bottom
        barelyDetectableLabel.setLayoutY(sliderHeight * ((100-1.380)/100));
        weakLabel.setLayoutY(sliderHeight * ((100-5.754)/100));
        moderateLabel.setLayoutY(sliderHeight * ((100-16.218)/100));
        strongLabel.setLayoutY(sliderHeight * ((100-33.113)/100));
        veryStrongLabel.setLayoutY(sliderHeight * ((100-50.119)/100));
        strongestImaginableLabel.setLayoutY(sliderHeight * ((100-95.499)/100)); // Near the top

    }


    /**
     * Binds the view model to the UI components.
     * @param viewModel The view model to bind to.
     */
    public void setViewModel(GLMSStage_VM viewModel) {
        this.viewModel = viewModel;
        bindViewModel();
    }

    /**
     * Binds the view model properties to the UI components.
     */
    private void bindViewModel() {
        if (viewModel != null) {
            // Bind the question label to the viewModel's txt_questionProperty
            questionlbl.textProperty().bind(viewModel.txt_questionProperty());

            // Two-way binding between the slider value and the viewModel's sliderValueProperty
            Bindings.bindBidirectional(mySlider.valueProperty(), viewModel.sliderValueProperty());
        }
    }
}
