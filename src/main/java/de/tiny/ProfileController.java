package src.main.java.de.tiny;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import src.main.java.de.tiny.model.ProfileModel;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ProfileController {

    @FXML ListView<String> profileListView = new ListView<>();
    public ProfileModel profileModel;
    private List<String> profileList;
    
    @FXML
    public void initialize() {
        profileModel = new ProfileModel();
        loadProfilesFromFile(); // Lade Profile aus der Datei
        
    }

///////// Profile Hinzufügen & Löschen /////////////////

    public List<String> addProfileToList(String profileName) {
        if(!profileList.contains(profileName))
        addProfile(profileName);
        return profileList;
    }
    private void addProfile(String profileName) {
        if (profileName != null && !profileName.trim().isEmpty()) {
            profileModel.getProfiles().put(profileName, null);
            profileList.add(profileName);
            saveProfilesToFile();
        }
    }

    public List<String> removeProfileFromList(String profileName) {
        removeProfile(profileName);
        return profileList;
    }

    public void removeProfile(String profileName) {
        if (profileName != null && !profileName.trim().isEmpty()) {
            profileModel.getProfiles().remove(profileName);
            profileList.remove(profileName);
            saveProfilesToFile();
        }
    }

    public List<String> getProfileList() {
        // Erhalte alle Profile-Namen aus der HashMap
        profileList = FXCollections.observableArrayList(profileModel.getProfiles().keySet());
        
        // Aktualisiere die ListView mit den Profilnamen
        profileListView.getItems().setAll(profileList);
        
        return profileList;
    }


/////////////////  Speichern und Laden der Profile  //////////////////

    public void saveProfilesToFile() {
        File file = getProfileFile();
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.writeObject(profileModel.getProfiles());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public void loadProfilesFromFile() {
    File file = getProfileFile();
    if (file.exists()) {
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            @SuppressWarnings("unchecked")
            HashMap<String, Set<Integer>> loadedProfiles = (HashMap<String, Set<Integer>>) in.readObject();
            profileModel.getProfiles().clear(); // bereinige die Liste
            profileModel.getProfiles().putAll(loadedProfiles); // füge Profile aus der Datei hinzu
            getProfileList(); // aktualisiere profileList mit den geladenen Profilen
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

private File getProfileFile() {
    String userHome = System.getProperty("user.home"); //Platforunabhängiger Speicherort
    File appDirectory = new File(userHome, "tinyPomo");  //.tinyPomo => versteckter Ordner
    if (!appDirectory.exists()) {// wenn nicht vorhanden, erstelle Ordner
        appDirectory.mkdirs();
    }
    return new File(appDirectory, "profile.dat"); //erstelle im Ordner die Profildatei
}

}

