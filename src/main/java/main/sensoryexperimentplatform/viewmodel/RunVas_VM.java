package main.sensoryexperimentplatform.viewmodel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunVasController;
import main.sensoryexperimentplatform.models.DataAccess;
import main.sensoryexperimentplatform.models.Vas;

public class RunVas_VM implements RunStages{
    private Vas vas;
    private IntegerProperty sliderValue;
    private final StringProperty questionText;
    private StringProperty lowAnchorText, button, conducted, help;
    private StringProperty highAnchorText;
    private BooleanProperty alert;


    public RunVas_VM(Vas vas) {
        this.vas = vas;
        sliderValue = new SimpleIntegerProperty(vas.getResult()); // Khởi tạo giá trị của sliderValue từ kết quả hiện tại
        button = new SimpleStringProperty(vas.getButtonText());
        questionText = new SimpleStringProperty(vas.getTitle());
        lowAnchorText = new SimpleStringProperty(vas.getLowAnchorText());
        highAnchorText = new SimpleStringProperty(vas.getHighAnchorText());
        conducted = new SimpleStringProperty(vas.getConducted());
        help = new SimpleStringProperty(vas.getHelpText());
        alert = new SimpleBooleanProperty(vas.getAlert());

        sliderValue.addListener((observable, oldValue, newValue) -> {
            setResult(newValue.intValue());
            conducted.set(DataAccess.getCurrentFormattedTime());
            setDate();

        });
    }

    public void setDate(){
        vas.setConducted(DataAccess.getCurrentFormattedTime());
    }

    public int getHighAnchorValue() {
        return vas.getHighAnchorValue();
    }
    public int getLowAnchorValue() {
        return vas.getLowAnchorValue();
    }

    public void setResult(int result) {
        vas.setResult(result);
    }
    public IntegerProperty sliderValueProperty() {
        return sliderValue;
    }
    public StringProperty helpTextProperty() {
        return help;
    }

    public StringProperty conductedTextProperty() {
        return conducted;
    }
    public StringProperty questionTextProperty() {
        return questionText;
    }

    public StringProperty lowAnchorTextProperty() {
        return lowAnchorText;
    }

    public StringProperty highAnchorTextProperty() {
        return highAnchorText;
    }

    public StringProperty buttonProperty() {
        return button;
    }

    public BooleanProperty alertProperty() {
        return alert;
    }
    public void playAlertSound(){
        vas.playAlertSound();
    }

    public double getResult() {
        return vas.getResult();
    }

    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunVas.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().addAll(newContent);
        RunVasController controller = loader.getController();
        controller.setViewModel(this);
    }
}