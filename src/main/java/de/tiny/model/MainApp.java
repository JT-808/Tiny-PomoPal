package src.main.java.de.tiny.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/src/main/resources/de/tiny/Main.fxml"));
            Scene scene = new Scene(root);
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.setTitle("PomoPal");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }

}
