package main.sensoryexperimentplatform.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.sensoryexperimentplatform.viewmodel.GLMSStage_VM;
import main.sensoryexperimentplatform.viewmodel.RunGLMS_VM;

public class RunGLMSController {

    @FXML
    private Slider mySlider;
    @FXML
    private Label questionlbl;
    @FXML
    private ImageView help_image;

    private GLMSStage_VM viewModel;



    public void setViewModel(GLMSStage_VM viewModel) {
        this.viewModel = viewModel;

        Tooltip helpTooltip = new Tooltip(viewModel.txt_helpProperty().get());

        bindViewModel();

        Tooltip tooltip = new Tooltip("Help text here!");
        if(viewModel.isCheckB_sound()){
            viewModel.playAlertSound();
        }
        if (viewModel.txt_helpProperty().get() != null) {
            tooltip.setText(viewModel.txt_helpProperty().get());
        }

        tooltip.setStyle(
                "-fx-background-color: #e3e2e2;\n" +
                        "    -fx-text-fill: #397E82;\n" +
                        "    -fx-font-size: 20px;\n" +
                        "    -fx-padding: 5px;\n" +
                        "    -fx-border-color: White;\n" +
                        "    -fx-border-width: 1px;\n" +
                        "    -fx-border-radius: 3px;"
        );
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setAutoHide(true);
        tooltip.setWrapText(true);
        tooltip.setMaxWidth(250);

        // Set listeners to show and hide tooltip on mouse enter and exit
        help_image.setOnMouseEntered(event -> showTooltip(help_image, tooltip));
        help_image.setOnMouseExited(event -> tooltip.hide());

        if(viewModel.txt_helpProperty()  == null || viewModel.txt_helpProperty().equals("null")) {
            tooltip.setOpacity(0.0);
            help_image.setVisible(false);
            help_image.setManaged(false);
        }

        // Add a listener to detect when the image is set to the ImageView
        help_image.imageProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Image> observable, javafx.scene.image.Image oldImage, javafx.scene.image.Image newImage) {
                if (newImage != null) {
                    showTooltip(help_image, tooltip);
                }
            }
        });

    }
    private void showTooltip(ImageView imageView, Tooltip tooltip) {
        // Get the bounds of the ImageView
        javafx.geometry.Bounds bounds = imageView.localToScreen(imageView.getBoundsInLocal());

        // Show the tooltip at the top-left position of the ImageView
        tooltip.show(imageView, bounds.getMinX() -250, bounds.getMinY() - tooltip.getHeight());
    }




    private void bindViewModel() {
        // Bind viewModel properties to the labels
        questionlbl.textProperty().bind(viewModel.txt_questionProperty());

        // Two-way binding between slider value and viewModel.sliderValueProperty()
        Bindings.bindBidirectional(mySlider.valueProperty(), viewModel.sliderValueProperty());

    }

}
