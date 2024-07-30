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

    private Stage stage;
    private Scene scene;
    private ProfileController profileController;
    private PomoTimerModel pomoTimerModel;

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
    @FXML
    private Text learnTimeText;
    

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
       // Initialisiere das Model und die Liste der Profile in der MainController-GUI
       profileController = new ProfileController();
       profileController.initialize();
       pomoTimerModel = new PomoTimerModel();

       // Laden der Profil-Liste bei der Initialisierung
      updateProfileList(profileController.getProfileList());

        // Nutze die Werte aus dem Model für die ChoiceBoxen
        Workbox.setItems(pomoTimerModel.getWorkZeiten());
        Workbox.setValue(pomoTimerModel.getWorkZeiten().get(0));
        PauseBox.setItems(pomoTimerModel.getPausenZeiten());
        PauseBox.setValue(pomoTimerModel.getPausenZeiten().get(0));
        RundenBox.setItems(pomoTimerModel.getRundenZeiten());
        RundenBox.setValue(pomoTimerModel.getRundenZeiten().get(0));

       profileListView.getSelectionModel().selectFirst(); // dient zur Fehlervermeidung (Null Exception)
       showLearnTime(null); //zeige von Anfang an die Lernzeiten an
   }


      //////////// Methoden in der GUI /////////////////

    @FXML
    public void wechselZuWork(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/main/resources/de/tiny/PomodoroTimerView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        PomoTimerController pomoTimerController = loader.getController();
        String selectedProfile = profileListView.getSelectionModel().getSelectedItem();

        pomoTimerController.startPomodoro(Workbox.getValue(), PauseBox.getValue(), RundenBox.getValue(),selectedProfile, profileController);
     
    }

    @FXML
    public void addNewProfile(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("insert");
        dialog.setHeaderText("insert name");
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


        // aktuallisiere die ListView in der GUI 

   public void updateProfileList(List<String> profileList) { 
    profileListView.getItems().setAll(profileList);
}
    
}