package main.sensoryexperimentplatform.models;



public class AudibleInstruction extends Stage {
    private String title;
    private String content;

    private String buttonText;
    private String helpText;
    private String soundName;
    private Sound SoundManager;



    public AudibleInstruction(String title, String content, String buttonText,String helpText,String soundName){
      super(title, content);
      this.content=content;
      this.title= title;
        this.buttonText= buttonText;
        this.helpText = helpText;
        this.soundName= soundName;
        this.SoundManager = new Sound();



    }

    public AudibleInstruction(AudibleInstruction o) {
        super(o.title, o.content);
        this.title= o.getTitle();
        this.content= o.getContent();
        this.buttonText= o.getButtonText();
        this.helpText = o.getHelpText();
        this.soundName= o.getSoundName();
        this.SoundManager = o.getSound();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public Sound getSound() {
        return SoundManager;
    }

    public String getSoundName(){
        return soundName;
    }
    public void setSoundName(String soundName){
        this.soundName = soundName;
    }


    public void playSound(String name) {
       SoundManager.playSound(name);
    }


    @Override
    public String toString() {

            return "audio(\"" + title + "\",\"" + content +
                    "\",\"" + buttonText + "\",\"" + helpText + "\",\""+ soundName + "\")";
        }
    }





