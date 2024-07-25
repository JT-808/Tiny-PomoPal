package src.main.java.de.tiny;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import src.main.java.de.tiny.model.ProfileModel;

import java.io.*;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class ProfileController {

    @FXML ListView<String> profileListView = new ListView<>();
    public ProfileModel profileModel;
    private List<String> profileList;
    
    @FXML
    public void initialize() {
        profileModel = new ProfileModel();
        loadProfilesFromFile(); // Lade Profile aus der Datei
    }


    public List<String> addProfileToList(String profileName) {
        addProfile(profileName);
        return profileList;
    }

    public List<String> removeProfileFromList(String profileName) {
        removeProfile(profileName);
        return profileList;
    }

    public List<String> getProfileList() {

        profileListView.getItems().clear();
        profileListView.getItems().addAll(profileList);
        // Implementiere hier die Methode zum Abrufen der Profil-Liste
        return FXCollections.observableArrayList(profileList); // Beispiel: leere Liste
    }

    private void addProfile(String profileName) {
        if (profileName != null && !profileName.trim().isEmpty()) {
            profileModel.getProfiles().add(profileName);
            profileList.add(profileName);
            saveProfilesToFile();
        }
    }
    public void removeProfile(String profileName) {
        if (profileName != null && !profileName.trim().isEmpty()) {
            profileModel.getProfiles().remove(profileName);
            profileList.remove(profileName);
            saveProfilesToFile();
        }
    }

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
            TreeSet<String> loadedProfiles = (TreeSet<String>) in.readObject();
            profileModel.getProfiles().clear(); // Leere die bestehende Map
            profileModel.getProfiles().addAll(loadedProfiles); // Füge geladene Profile hinzu
            profileList = FXCollections.observableArrayList(profileModel.getProfiles());
            profileListView.getItems().setAll(profileList);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}



public File getProfileFile() {
        return new File("/home/woodz/Downloads/profile.dat");
    }

    public ProfileModel getProfileModel() {
        return profileModel;
    }
}
