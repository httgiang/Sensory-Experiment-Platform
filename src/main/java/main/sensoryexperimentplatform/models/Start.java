package main.sensoryexperimentplatform.models;
import javafx.scene.paint.Color;
public class Start extends Stage implements Model{
    private String buttonText;
    private Boolean requireBalance;
    private Color backGroundColor;
    private Color TextColor;
    private long StartOfStageDelay;
    private long EndOfStageDelay;
    private Color disableButtonColor;
    private String title, content;

    public Start(String title, String content, String buttonText, Boolean requireBalance, Color backGroundColor, Color TextColor
    , long StartOfStageDelay, long EndOfStageDelay,Color disableButtonColor ) {
        super(title, content);
        this.title = title;
        this.content = content;
        this.backGroundColor = backGroundColor;
        this.buttonText = buttonText;
        this.StartOfStageDelay = StartOfStageDelay;
        this.EndOfStageDelay = EndOfStageDelay;
        this.requireBalance = requireBalance;
        this.disableButtonColor = disableButtonColor;
    }
    public Start(Start start){
        super(start.title, start.content);
        this.title = start.getTitle();
        this.content = start.getContent();
        this.backGroundColor = start.backGroundColor;
        this.buttonText = start.buttonText;
        this.StartOfStageDelay = start.StartOfStageDelay;
        this.EndOfStageDelay = start.EndOfStageDelay;
        this.requireBalance = start.requireBalance;
        this.disableButtonColor = start.disableButtonColor;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public Boolean getRequireBalance() {
        return requireBalance;
    }

    public void setRequireBalance(Boolean requireBalance) {
        this.requireBalance = requireBalance;
    }

    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public Color getTextColor() {
        return TextColor;
    }

    public void setTextColor(Color textColor) {
        TextColor = textColor;
    }

    public long getStartOfStageDelay() {
        return StartOfStageDelay;
    }

    public void setStartOfStageDelay(long startOfStageDelay) {
        StartOfStageDelay = startOfStageDelay;
    }

    public long getEndOfStageDelay() {
        return EndOfStageDelay;
    }

    public void setEndOfStageDelay(long endOfStageDelay) {
        EndOfStageDelay = endOfStageDelay;
    }

    public Color getDisableButtonColor() {
        return disableButtonColor;
    }

    public void setDisableButtonColor(Color disableButtonColor) {
        this.disableButtonColor = disableButtonColor;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String toString(){
        System.out.println(getTitle()+"\t"+getContent());
        return "startExperiment(\"" + getTitle() + "\",\"" + getContent() + "\",\"" +
                buttonText + "\")";
    }
}
