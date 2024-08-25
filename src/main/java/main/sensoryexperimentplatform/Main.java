package main.sensoryexperimentplatform;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.sensoryexperimentplatform.models.DataAccess;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/main/sensoryexperimentplatform/Base.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        DataAccess.loadExperiments();

        primaryStage.setTitle("Sense XP");

        primaryStage.setScene(scene);
        primaryStage.setHeight(850);
        primaryStage.setWidth(1450);


        primaryStage.show();

    }
}