package main.sensoryexperimentplatform.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import main.sensoryexperimentplatform.viewmodel.RatingContainer_VM;

public class addRatingContainerController {


    @FXML
    private CheckBox cbx_random;
    private RatingContainer_VM viewModel;

    @FXML
    private TextField txt_minTime;
    public void setViewModel(RatingContainer_VM viewModel) {
        this.viewModel = viewModel;
        bindViewModel();
    }

    public void bindViewModel() {
// Bind the text property of txt_minTime to the minTimeProperty of viewModel
        txt_minTime.textProperty().bindBidirectional(viewModel.minTimeProperty(), new NumberStringConverter());
        cbx_random.selectedProperty().bindBidirectional(viewModel.randomProperty());

        cbx_random.selectedProperty().addListener((observable, oldValue, newValue) -> {
            viewModel.setIsRandomize(newValue);
        });
        txt_minTime.textProperty().addListener((observable, oldValue, newValue) -> {
            // Parse the newValue (String) to an int before setting it
            try {
                int intValue = Integer.parseInt(newValue);
                viewModel.setMinTime(intValue);
            } catch (NumberFormatException e) {
                // Handle the case where newValue cannot be parsed to an int
                // For example, display an error message or set a default value
            }
        });

    }
}


