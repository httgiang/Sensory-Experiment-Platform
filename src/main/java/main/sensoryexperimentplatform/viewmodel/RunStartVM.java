package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.sensoryexperimentplatform.models.Notice;
import main.sensoryexperimentplatform.models.Start;

public class RunStartVM {
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
}
