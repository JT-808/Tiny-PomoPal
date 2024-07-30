package src.main.java.de.tiny;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class MainApp extends Application {

      /**
     * Überprüfe, ob die FXML-Datei erfolgreich gefunden wurde
    * lade FXML
    * Starte die JavaFX-Anwendung und zeige das Hauptfenster an.
     *
     * @param primaryStage Die Hauptbühne der JavaFX-Anwendung
     * @throws Exception Wenn ein Fehler beim Laden der FXML-Datei auftritt
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL fxmlLocation = getClass().getResource("/src/main/resources/de/tiny/Main.fxml");
        if (fxmlLocation == null) {
            throw new IllegalStateException("FXML file not found. Check the path and ensure the file exists.");
        }
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        primaryStage.setTitle("Tiny PomoPal");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
       

    }
}
