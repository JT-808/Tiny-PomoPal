package src.main.java.de.tiny;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import src.main.java.de.tiny.model.ProfileModel;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfileController {

    @FXML
    ListView<String> profileListView = new ListView<>();
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
            ArrayList<Integer> lernZeit = new ArrayList<>();
            lernZeit.add(0);
            profileModel.getProfiles().put(profileName,lernZeit);
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


///////////////////////// Daten setzen und erhalten /////////////////////

    public List<String> getProfileList() {
        // Erhalte alle Profile-Namen aus der HashMap
        profileList = FXCollections.observableArrayList(profileModel.getProfiles().keySet());
        
        // Aktualisiere die ListView mit den Profilnamen
        profileListView.getItems().setAll(profileList);
        
        return profileList;
    }

    
    public String getLearnTime(String profileName) {
        int totalMinutes = 0;
    
        // Überprüfen, ob das Profil existiert
        if (profileModel.getProfiles().containsKey(profileName)) {
            List<Integer> lernZeit = profileModel.getProfiles().get(profileName);
            
            // Berechnung der Gesamtzeit in Minuten
            for (int zeit : lernZeit) {
                totalMinutes += zeit;
            }
            
            // Umwandlung in Stunden und Minuten
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
    
            // Rückgabe der Zeit als formatierten String
            return String.format("%d h %d m", hours, minutes);
        } else {
            // Falls Profil nicht gefunden wird
            return "profile not found";
        }
    }

    public void zeitEinfuegen(String name, int zeit) {
        if (profileList.contains(name)) {
            List<Integer> lernZeit = profileModel.getProfiles().get(name);
            if (lernZeit == null) {
                lernZeit = new ArrayList<>();
                profileModel.getProfiles().put(name, lernZeit);
            }
            lernZeit.add(zeit);

            saveProfilesToFile();
        } else {
            System.out.println("profile not found: " + name);
        }
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
                HashMap<String, List<Integer>> loadedProfiles = (HashMap<String, List<Integer>>) in.readObject();
                profileModel.getProfiles().clear(); // Bereinige die Liste
                profileModel.getProfiles().putAll(loadedProfiles); // Füge Profile aus der Datei hinzu
                getProfileList(); // Aktualisiere profileList mit den geladenen Profilen
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

