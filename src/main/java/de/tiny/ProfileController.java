package src.main.java.de.tiny;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import src.main.java.de.tiny.model.ProfileModel;

import java.io.*;

import java.util.TreeMap;

public class ProfileController {

    @FXML
    private ListView<String> profileListView = new ListView<>();
    public ProfileModel profileModel;
    private ObservableList<String> profileList;

    @FXML
    public void initialize() {
        profileModel = new ProfileModel();
       // loadProfilesFromFile(); // Lade Profile aus der Datei
        profileList = FXCollections.observableArrayList(profileModel.getProfiles());
        //profileListView.setItems(profileList);
    

    }

    public ObservableList<String> addProfileToList(String profileName) {
        addProfile(profileName);
        return profileList;
    }

    public void removeProfileFromList(String profileName) {
        // Implementiere hier die Methode zum Entfernen eines Profils aus der Liste
    }

    public ObservableList<String> getProfileList() {
        // Implementiere hier die Methode zum Abrufen der Profil-Liste
        return FXCollections.observableArrayList(); // Beispiel: leere Liste
    }

    private void addProfile(String profileName) {
        if (profileName != null && !profileName.trim().isEmpty()) {
            profileModel.getProfiles().add(profileName);
            profileList.add(profileName);
           // saveProfilesToFile();
        }
    }

    public void removeProfile(String profileName) {
        if (profileName != null && !profileName.trim().isEmpty()) {
            profileModel.getProfiles().remove(profileName);
         //   saveProfilesToFile();
        }
    }

   

    // public void saveProfilesToFile() {
    //     File file = getProfileFile();
    //     try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
    //         out.writeObject(profileModel.getProfiles());
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

//     public void loadProfilesFromFile() {
//     File file = getProfileFile();
//     if (file.exists()) {
//         try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
//             @SuppressWarnings("unchecked")
//             TreeMap<String, ObservableSet<Integer>> loadedProfiles = (TreeMap<String, ObservableSet<Integer>>) in.readObject();
            
//             profileModel.getProfiles().clear(); // Leere die bestehende Map
//             profileModel.getProfiles().putAll(loadedProfiles); // Füge geladene Profile hinzu
//             profileList = FXCollections.observableArrayList(profileModel.getProfiles().keySet());
//             profileListView.setItems(profileList);
//             updateProfileList();
//         } catch (IOException | ClassNotFoundException e) {
//             e.printStackTrace();
//         }
//     }
// }

// public File getProfileFile() {
//         return new File("/home/woodz/Downloads/profile.dat");
//     }

    public ProfileModel getProfileModel() {
        return profileModel;
    }
}
