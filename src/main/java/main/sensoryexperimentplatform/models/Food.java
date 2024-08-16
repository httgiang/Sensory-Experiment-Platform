package main.sensoryexperimentplatform.models;

public class Food {
    private final String name;
    private RatingContainer vasContainer, glmsContainer;
    private TasteTest tasteTest;

    public Food (String name,TasteTest tasteTest){
        this.name = name;
        vasContainer = new RatingContainer(tasteTest.isRandomizedRatings,tasteTest.getTimeWait());
        glmsContainer = new RatingContainer(tasteTest.isRandomizedRatings,tasteTest.getTimeWait());
    }

    public String getName() {
        return name;
    }

    public void addVasRating(Vas stage){
       vasContainer.addStage(stage);
   }
    public void addGlmsRating(gLMS stage){
      glmsContainer.addStage(stage);
   }
    public String toString(){
        return "food(\"" + name + "\")";
    }
}
