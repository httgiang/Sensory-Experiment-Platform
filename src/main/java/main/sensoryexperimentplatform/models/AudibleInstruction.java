package main.sensoryexperimentplatform.models;


public class AudibleInstruction extends Stage implements Model {
    private String title;
    private String content;

    private String buttonText;
    private String helpText;
    private String soundName;


    private String filePath;
    private Sound soundManager;



    public AudibleInstruction(String title, String content, String buttonText,String helpText,String soundName, String FilePath){
      super(title, content);
      this.content=content;
      this.title= title;
      this.buttonText= buttonText;
      this.helpText = helpText;
      this.soundName= soundName;
      this.filePath = FilePath;
      this.soundManager = SoundSingleton.getInstance();



    }

    public AudibleInstruction(AudibleInstruction o) {
        super(o.title, o.content);
        this.title= o.getTitle();
        this.content= o.getContent();
        this.buttonText= o.getButtonText();
        this.helpText = o.getHelpText();
        this.filePath = o.getFilePath();
        this.soundName= o.getSoundName();

        this.soundManager = SoundSingleton.getInstance();
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
        return soundManager;
    }

    public String getSoundName(){
        return soundName;
    }
    public void setSoundName(String soundName){
        this.soundName = soundName;
    }



    public String getFilePath(){
        return filePath;
    }


    public void setFilePath(String filePath) {
     this.filePath = filePath;
    }


    public void playSound(String name) {
       soundManager.playSound(name);
    }

    public void addSoundList(String name){
        soundManager.addNewSound(name);
    }

    public void loadSound(String name,String filePath){
        soundManager.loadSound(name,filePath);
    }




    @Override
    public String toString() {

            return "audio(\"" + title + "\",\"" + content +
                    "\",\"" + buttonText + "\",\"" + helpText + "\",\""  + soundName + "\",\"" + filePath + "\")";
        }
    }





