package main.sensoryexperimentplatform.viewmodel;

import javafx.beans.property.StringProperty;
import main.sensoryexperimentplatform.models.Question;

public class RunQuestion_VM {
    private Question question;
    private StringProperty questionText;
    public RunQuestion_VM(Question question) {
        this.question = question;

    }
}
