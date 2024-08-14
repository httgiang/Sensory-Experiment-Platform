package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.sensoryexperimentplatform.models.Notice;

public class RunNotice_VM {
    private Notice notice;
    private StringProperty title, content, button;
    private StringProperty helpText;
    private BooleanProperty alert;

    public RunNotice_VM(Notice notice){
        this.notice = notice;
        button = new SimpleStringProperty(notice.getButtonText());
        title = new SimpleStringProperty(notice.getTitle());
        content = new SimpleStringProperty(notice.getContent());
        helpText = new SimpleStringProperty(notice.getHelpText());
        alert = new SimpleBooleanProperty(notice.isAlert());
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
    public StringProperty getHelpText(){
        return helpText;
    }
    public BooleanProperty getAlert(){
        return alert;
    }
    public void playSound(){
        notice.playSound();
    }
}
