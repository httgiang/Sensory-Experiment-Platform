package main.sensoryexperimentplatform.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.sensoryexperimentplatform.viewmodel.RunNotice_VM;


public class RunNoticeController {
    @FXML
    private Label titleTxt, contentTxt;
    private RunNotice_VM viewModel;
    @FXML
    private ImageView help_image;


    public void setViewModel(RunNotice_VM viewModel){
        this.viewModel = viewModel;

        bindViewModel();
        if (viewModel.getAlert().get()) {
           viewModel.playSound();
        }
        Tooltip tooltip = new Tooltip("Help text here!");
        if (viewModel.getHelpText().get() != null) {
            tooltip.setText(viewModel.getHelpText().get());
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

        help_image.setOnMouseEntered(event -> showTooltip(help_image, tooltip));
        help_image.setOnMouseExited(event -> tooltip.hide());

        help_image.imageProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Image> observable, javafx.scene.image.Image oldImage, javafx.scene.image.Image newImage) {
                if (newImage != null) {
                    showTooltip(help_image, tooltip);
                }
            }
        });
    }

    private void showTooltip(javafx.scene.image.ImageView imageView, Tooltip tooltip) {
        // Get the bounds of the ImageView
        javafx.geometry.Bounds bounds = imageView.localToScreen(imageView.getBoundsInLocal());

        // Show the tooltip at the top-left position of the ImageView
        tooltip.show(imageView, bounds.getMinX() - 250, bounds.getMinY() - tooltip.getHeight());
    }


    private void bindViewModel(){
        titleTxt.textProperty().bind(viewModel.getTitle());
        contentTxt.textProperty().bind(viewModel.getContent());

    }
}
