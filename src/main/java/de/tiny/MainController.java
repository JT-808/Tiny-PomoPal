package src.main.java.de.tiny;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.main.java.de.tiny.model.PomoTimerModel;
import src.main.java.de.tiny.model.ProfileModel;
import javafx.scene.Node;

public class MainController extends PomoTimerModel implements Initializable {

    private Stage stage;
    private Scene scene;

    @FXML
    private ChoiceBox<Integer> PauseBox;

    @FXML
    private ChoiceBox<Integer> RundenBox;

    @FXML
    public ChoiceBox<Integer> Workbox;

    @FXML
    private Button buttonStart;

    @FXML
    private Button addProfile;

    @FXML
    private Button delete;


    @FXML
    private ListView<String> profileList;

    //Controller initialisieren
    private PomoTimerController pomoTimerController;
    private ProfileController pc = new ProfileController();

    @FXML
    //Klick = Abfrage nach Namen -> daraus neues Profil
    private void addNewProfile(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        //Gestalltung der Abfrage:
        dialog.setTitle("add");
        dialog.setHeaderText("Please enter name");
        dialog.setContentText("name:");
        dialog.showAndWait(); //zwingend! sonst keine Anzeige
        String newProfileName = dialog.getEditor().getText(); 
       pc.addProfile(newProfileName);
    }

    @FXML
    private void removeSelectedProfile(ActionEvent event) {
        String selectedProfile = profileList.getSelectionModel().getSelectedItem();
        if (selectedProfile != null) {
            pc.removeProfile(selectedProfile);
        }
    }

    @FXML
    public void wechselZuWork(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/src/main/resources/de/tiny/PomodoroTimerView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        // Lade Controllerobjekt, da sonst Controller= Null und
        // damit die Werte übergeben werden können
        pomoTimerController = loader.getController();
        pomoTimerController.startPomodoro(Workbox.getValue(), PauseBox.getValue(), RundenBox.getValue());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Workbox.setItems(zeitListe);
        Workbox.setValue(zeitListe.getFirst());
        PauseBox.setItems(zeitListe);
        PauseBox.setValue(zeitListe.getFirst());
        RundenBox.setItems(rundenListe);
        RundenBox.setValue(rundenListe.getFirst());

        profileList.setItems(pc.getProfiles());

               // todo: Namen soll aus dem Profil hergeholt werden
               String username = "test";
   //  pomoTimerController.setProfileNameText(username);

    }

}
