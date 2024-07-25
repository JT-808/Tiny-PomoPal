package src.main.java.de.tiny;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private ChoiceBox<Integer> PauseBox;

    @FXML
    private ChoiceBox<Integer> RundenBox;

    @FXML
    private ChoiceBox<Integer> Workbox;

    @FXML
    private Button buttonStart;

    @FXML
    private Button addProfile;

    @FXML
    private Button delete;

    @FXML
    private ListView<String> profileListView = new ListView<>();
    

   private ProfileController profileController ;

    @FXML
    public void wechselZuWork(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/de/tiny/PomodoroTimerView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        PomoTimerController pomoTimerController = loader.getController();
        pomoTimerController.startPomodoro(Workbox.getValue(), PauseBox.getValue(), RundenBox.getValue());
     
    }

        // aktuallisiere die ListView in der GUI
    public void updateProfileList(List<String> profileList) { 
        profileListView.getItems().setAll(profileList);
      }

    @FXML
    public void addNewProfile(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("hinzufügen");
        dialog.setHeaderText("Bitte Namen eingeben");
        dialog.setContentText("Name:");
        dialog.showAndWait();

        String newProfileName = dialog.getEditor().getText();
        if (newProfileName != null && !newProfileName.trim().isEmpty()) {
            // Füge Profil zur Liste hinzu
            List<String> profileList = profileController.addProfileToList(newProfileName);
            updateProfileList(profileList);
        }
    }

    @FXML
    public void removeSelectedProfile(ActionEvent event) {
        String selectedProfile = profileListView.getSelectionModel().getSelectedItem();
        if (selectedProfile != null) {
            // Entferne Profil aus der Liste
            List<String> profileList = profileController.removeProfileFromList(selectedProfile); 
            updateProfileList(profileList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisiere die Liste der Profile in der MainController-GUI
        profileController = new ProfileController();
        profileController.initialize();

        // Laden der Profil-Liste bei der Initialisierung
       updateProfileList(profileController.getProfileList());

        
        // Beispielwerte für ChoiceBoxen
        ObservableList<Integer> zeitListe = FXCollections.observableArrayList(5, 10, 15, 25, 30); // Beispielwerte
        ObservableList<Integer> rundenListe = FXCollections.observableArrayList(1, 2, 3, 4, 5); // Beispielwerte

        Workbox.setItems(zeitListe);
        Workbox.setValue(zeitListe.get(0));
        PauseBox.setItems(zeitListe);
        PauseBox.setValue(zeitListe.get(0));
        RundenBox.setItems(rundenListe);
        RundenBox.setValue(rundenListe.get(0));     
        
        

    }
}
