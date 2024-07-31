package src.main.java.de.tiny;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.stage.Stage;
import src.main.java.de.tiny.model.PomoTimerModel;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    // Attribute für die Steuerung der GUI
    private Stage stage;
    private Scene scene;
    private ProfileController profileController;
    private PomoTimerModel pomoTimerModel;

    // FXML-Elemente in der GUI
    @FXML
    private ChoiceBox<Integer> PauseBox; // Auswahlbox für Pausenzeiten
    @FXML
    private ChoiceBox<Integer> RundenBox; // Auswahlbox für die Anzahl der Runden
    @FXML
    private ChoiceBox<Integer> Workbox; // Auswahlbox für Arbeitszeiten
    @FXML
    private Button buttonStart; // Start-Button für den Pomodoro-Timer
    @FXML
    private Button addProfile; // Button zum Hinzufügen eines neuen Profils
    @FXML
    private Button delete; // Button zum Löschen eines ausgewählten Profils
    @FXML
    private ListView<String> profileListView = new ListView<>(); // ListView zur Anzeige der Profile
    @FXML
    private Text learnTimeText; // Text zur Anzeige der gesamten Lernzeit

    /**
     * Initialisiert den Controller und lädt die notwendigen Daten.
     * @param url Der URL für die Initialisierung
     * @param resourceBundle Das ResourceBundle für die Initialisierung
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialisiere das Profil-Controller und das Pomodoro-Timer-Modell
        profileController = new ProfileController();
        profileController.initialize();
        pomoTimerModel = new PomoTimerModel();

        // Lade die Profil-Liste bei der Initialisierung
        updateProfileList(profileController.getProfileList());

        // Nutze die Werte aus dem Modell für die Auswahlboxen
        Workbox.setItems(pomoTimerModel.getWorkZeiten());
        Workbox.setValue(pomoTimerModel.getWorkZeiten().get(0));
        PauseBox.setItems(pomoTimerModel.getPausenZeiten());
        PauseBox.setValue(pomoTimerModel.getPausenZeiten().get(0));
        RundenBox.setItems(pomoTimerModel.getRundenZeiten());
        RundenBox.setValue(pomoTimerModel.getRundenZeiten().get(0));

        // Wähle das erste Profil in der Liste aus, um NullPointerExceptions zu vermeiden
        profileListView.getSelectionModel().selectFirst();
        showLearnTime(null); // Zeige die Lernzeiten von Anfang an an
    }

    /**
     * Wechselt zur Pomodoro-Work-Szene und startet den Timer.
     * @param event Das ActionEvent, das den Wechsel auslöst
     * @throws IOException Wenn ein Fehler beim Laden der FXML-Datei auftritt
     */
    @FXML
    public void wechselZuWork(ActionEvent event) throws IOException {
        // Laden der PomodoroTimerView.fxml-Datei
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/de/tiny/PomodoroTimerView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen(); // Zentriere das Fenster auf dem Bildschirm
        stage.show();

        // Holen des Controllers der geladenen Szene
        PomoTimerController pomoTimerController = loader.getController();
        String selectedProfile = profileListView.getSelectionModel().getSelectedItem();

        // Starte den Pomodoro-Timer mit den ausgewählten Werten und dem Profil
        pomoTimerController.startPomodoro(Workbox.getValue(), PauseBox.getValue(), RundenBox.getValue(), selectedProfile, profileController);
    }

    /**
     * Fügt ein neues Profil hinzu, nachdem der Benutzer einen Namen eingegeben hat.
     * @param event Das ActionEvent, das das Hinzufügen auslöst
     */
    @FXML
    public void addNewProfile(ActionEvent event) {
        // Dialog zur Eingabe eines neuen Profilnamens
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("insert");
        dialog.setHeaderText("insert name");
        dialog.setContentText("Name:");
        dialog.showAndWait();

        String newProfileName = dialog.getEditor().getText();
        if (newProfileName != null && !newProfileName.trim().isEmpty()) {
            // Füge das neue Profil zur Liste hinzu
            List<String> profileList = profileController.addProfileToList(newProfileName);
            updateProfileList(profileList);
        }
    }

    /**
     * Entfernt das ausgewählte Profil aus der Liste.
     * @param event Das ActionEvent, das das Entfernen auslöst
     */
    @FXML
    public void removeSelectedProfile(ActionEvent event) {
        String selectedProfile = profileListView.getSelectionModel().getSelectedItem();
        if (selectedProfile != null) {
            // Entferne das ausgewählte Profil aus der Liste
            List<String> profileList = profileController.removeProfileFromList(selectedProfile); 
            updateProfileList(profileList);
        }
    }

    /**
     * Zeigt die gesamte Lernzeit für das ausgewählte Profil an.
     * @param event Das MouseEvent, das die Anzeige auslöst
     */
    @FXML
    public void showLearnTime(MouseEvent event) {
        String selectedProfile = profileListView.getSelectionModel().getSelectedItem();
        if (selectedProfile != null) {
            String totalLearnTime = profileController.getLearnTime(selectedProfile);
            learnTimeText.setText("total study time: \n" + totalLearnTime);
        } else {
            learnTimeText.setText("choose profile");
        }
    }

    /**
     * Aktualisiert die Profil-Liste in der ListView.
     * @param profileList Die aktualisierte Liste der Profile
     */
    public void updateProfileList(List<String> profileList) { 
        profileListView.getItems().setAll(profileList);
    }
}
