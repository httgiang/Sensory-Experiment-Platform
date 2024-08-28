package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.util.Duration;
import main.sensoryexperimentplatform.viewmodel.InputStage_VM;

public class RunInputController {

    @FXML
    private ImageView help_image;

    @FXML
    private TextField txt_input;

    @FXML
    private Label txt_question;

    private InputStage_VM viewModel;

    public void setViewModel(InputStage_VM viewModel){
        this.viewModel = viewModel;
        txt_input.setAlignment(Pos.CENTER);
        bindViewModel();
        if(viewModel.getPlayAlert().get()){
            viewModel.playAlert();
        }
        Tooltip tooltip = new Tooltip("Help text here!");
        if (viewModel.getHelpText() != null) {
            tooltip.setText(viewModel.getHelpText());
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

        if(viewModel.getHelpText()  == null || viewModel.getHelpText().equals("null")) {
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
    public void setResult(String input){
        viewModel.setResult(input);
    }
    private void showTooltip(ImageView imageView, Tooltip tooltip) {
        // Get the bounds of the ImageView
        javafx.geometry.Bounds bounds = imageView.localToScreen(imageView.getBoundsInLocal());

        // Show the tooltip at the top-left position of the ImageView
        tooltip.show(imageView, bounds.getMinX() - 250, bounds.getMinY() - tooltip.getHeight());
    }

    private void bindViewModel() {
        txt_input.textProperty().bindBidirectional(viewModel.getResult());

        txt_input.textProperty().addListener((observableValue, oldValue, newValue) -> {
            viewModel.setResult(newValue);
        });
        txt_question.textProperty().bindBidirectional(viewModel.questionProperty());

    }
}
