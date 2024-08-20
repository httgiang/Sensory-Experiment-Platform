package main.sensoryexperimentplatform.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.sensoryexperimentplatform.viewmodel.RunVas_VM;
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
    @FXML
    private ImageView help_image;

    private VasStage_VM viewModel;

    public void setViewModel(VasStage_VM viewModel) {
        this.viewModel = viewModel;
        bindViewModel();
//        if(viewModel.alertProperty().get()) {
//            viewModel.playAlertSound();
//
//        }

        Tooltip tooltip = new Tooltip("Help text here!");
        if (viewModel.helpTextProperty().get() != null) {
            tooltip.setText(viewModel.helpTextProperty().get());
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

        // Add a listener to detect when the image is set to the ImageView
        help_image.imageProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends javafx.scene.image.Image> observable, javafx.scene.image.Image oldImage, javafx.scene.image.Image newImage) {
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
        tooltip.show(imageView, bounds.getMinX() - 250, bounds.getMinY() - tooltip.getHeight());
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
