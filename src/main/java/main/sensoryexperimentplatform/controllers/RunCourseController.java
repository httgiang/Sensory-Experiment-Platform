package main.sensoryexperimentplatform.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import main.sensoryexperimentplatform.models.Course;
import main.sensoryexperimentplatform.viewmodel.AddCourseVM;

public class RunCourseController {

    @FXML
    private Button btn;

    @FXML
    private Label contentTxt;

    @FXML
    private ImageView help_image;

    @FXML
    private Label titleTxt;

    private DoubleProperty consumedWeight;


    private Course course;
    private AddCourseVM viewModel;
    public void setViewModel(AddCourseVM viewModel){
        this.course = viewModel.getCourse();
        this.viewModel = viewModel;
        bindViewModel();

    }
    private void showTooltip(ImageView imageView, Tooltip tooltip) {
        // Get the bounds of the ImageView
        javafx.geometry.Bounds bounds = imageView.localToScreen(imageView.getBoundsInLocal());

        // Show the tooltip at the top-left position of the ImageView
        tooltip.show(imageView, bounds.getMinX() - 250, bounds.getMinY() - tooltip.getHeight());
    }
    private void bindViewModel() {
        titleTxt.textProperty().bind(viewModel.txt_titleProperty());
        contentTxt.textProperty().bind(viewModel.txt_contentProperty());
        consumedWeight.bindBidirectional(viewModel.consumedWeightProperty());
    }


}
