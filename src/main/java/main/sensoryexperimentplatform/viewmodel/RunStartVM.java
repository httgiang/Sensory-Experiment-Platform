package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import main.sensoryexperimentplatform.SensoryExperimentPlatform;
import main.sensoryexperimentplatform.controllers.RunStartController;
import main.sensoryexperimentplatform.models.Notice;
import main.sensoryexperimentplatform.models.Start;

import java.io.IOException;

public class RunStartVM implements RunStages {
    private Start start;
    private StringProperty title, content, button;

    public RunStartVM(Start s){
        this.start = s;
        button = new SimpleStringProperty(start.getButtonText());
        title = new SimpleStringProperty(start.getTitle());
        content = new SimpleStringProperty(start.getContent());
    }
    public StringProperty getTitle(){
        return title;
    }
    public StringProperty getContent(){
        return content;
    }
    public StringProperty buttonProperty() {
        return button;
    }

    @Override
    public void loadInterface(AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(SensoryExperimentPlatform.class.getResource("RunStart.fxml"));
        AnchorPane newContent = loader.load();
        anchorPane.getChildren().setAll(newContent);
        RunStartController controller = loader.getController();
        controller.setViewModel(this);
    }
}
