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
    ListView<String> profileListView = new ListView<>(); // ListView zur Anzeige der Profile in der GUI
    public ProfileModel profileModel; // Modell zur Verwaltung der Profile
    private List<String> profileList; // Liste der Profilnamen

    @FXML
    public void initialize() {
        profileModel = new ProfileModel(); // Initialisiere das ProfileModel
        loadProfilesFromFile(); // Lade Profile aus der Datei
    }

    ////////// Profile Hinzufügen & Löschen /////////////////

    // Methode zur Hinzufügung eines neuen Profils in die Liste
    public List<String> addProfileToList(String profileName) {
        if(!profileList.contains(profileName)) // Überprüfe, ob das Profil bereits existiert
            addProfile(profileName); // Füge das Profil hinzu, wenn es nicht existiert
        return profileList; // Gib die aktualisierte Profilliste zurück
    }

    // Interne Methode zur tatsächlichen Hinzufügung des Profils
    private void addProfile(String profileName) {
        if (profileName != null && !profileName.trim().isEmpty()) { // Überprüfe, ob der Profilname gültig ist
            ArrayList<Integer> lernZeit = new ArrayList<>(); // Initialisiere die Lernzeitliste für das neue Profil
            lernZeit.add(0); // Starte mit 0 Minuten Lernzeit
            profileModel.getProfiles().put(profileName, lernZeit); // Füge das neue Profil dem Modell hinzu
            profileList.add(profileName); // Füge den Profilnamen der Liste hinzu
            saveProfilesToFile(); // Speichere die Profile in der Datei
        }
    }

    // Methode zur Entfernung eines Profils aus der Liste
    public List<String> removeProfileFromList(String profileName) {
        removeProfile(profileName); // Entferne das Profil
        return profileList; // Gib die aktualisierte Profilliste zurück
    }

    // Interne Methode zur tatsächlichen Entfernung des Profils
    public void removeProfile(String profileName) {
        if (profileName != null && !profileName.trim().isEmpty()) { // Überprüfe, ob der Profilname gültig ist
            profileModel.getProfiles().remove(profileName); // Entferne das Profil aus dem Modell
            profileList.remove(profileName); // Entferne den Profilnamen aus der Liste
            saveProfilesToFile(); // Speichere die aktualisierten Profile in der Datei
        }
    }

    ///////////////////////// Daten setzen und erhalten /////////////////////

    // Methode zum Abrufen der Profilliste
    public List<String> getProfileList() {
        // Erhalte alle Profilnamen aus der HashMap
        profileList = FXCollections.observableArrayList(profileModel.getProfiles().keySet());
        
        // Aktualisiere die ListView mit den Profilnamen
        profileListView.getItems().setAll(profileList);
        
        return profileList; // Gib die Profilliste zurück
    }

    // Methode zur Berechnung und Rückgabe der Lernzeit für ein Profil
    public String getLearnTime(String profileName) {
        int totalMinutes = 0; // Gesamte Lernzeit in Minuten
    
        // Überprüfen, ob das Profil existiert
        if (profileModel.getProfiles().containsKey(profileName)) {
            List<Integer> lernZeit = profileModel.getProfiles().get(profileName); // Hole die Lernzeiten für das Profil
            
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

    // Methode zum Hinzufügen von Lernzeit zu einem Profil
    public void zeitEinfuegen(String name, int zeit) {
        if (profileList.contains(name)) { // Überprüfen, ob das Profil existiert
            List<Integer> lernZeit = profileModel.getProfiles().get(name); // Hole die Lernzeiten für das Profil
            if (lernZeit == null) { // Falls keine Lernzeit vorhanden ist, initialisiere sie
                lernZeit = new ArrayList<>();
                profileModel.getProfiles().put(name, lernZeit);
            }
            lernZeit.add(zeit); // Füge die neue Lernzeit hinzu

            saveProfilesToFile(); // Speichere die aktualisierten Profile in der Datei
        } else {
            System.out.println("profile not found: " + name); // Ausgabe, falls Profil nicht gefunden wird
        }
    }

    /////////////////  Speichern und Laden der Profile  //////////////////

    // Methode zum Speichern der Profile in einer Datei
    public void saveProfilesToFile() {
        File file = getProfileFile(); // Hole die Datei zum Speichern der Profile
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.writeObject(profileModel.getProfiles()); // Schreibe die Profile in die Datei
        } catch (IOException e) {
            e.printStackTrace(); // Ausgabe bei Fehlern
        }
    }

    // Methode zum Laden der Profile aus einer Datei
    public void loadProfilesFromFile() {
        File file = getProfileFile(); // Hole die Datei zum Laden der Profile
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
                @SuppressWarnings("unchecked")
                HashMap<String, List<Integer>> loadedProfiles = (HashMap<String, List<Integer>>) in.readObject(); // Lese die Profile aus der Datei
                profileModel.getProfiles().clear(); // Bereinige die Liste
                profileModel.getProfiles().putAll(loadedProfiles); // Füge Profile aus der Datei hinzu
                getProfileList(); // Aktualisiere profileList mit den geladenen Profilen
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // Ausgabe bei Fehlern
            }
        }
    }

    // Methode zum Abrufen der Datei zum Speichern/Laden der Profile
    private File getProfileFile() {
        String userHome = System.getProperty("user.home"); // Plattformunabhängiger Speicherort
        File appDirectory = new File(userHome, "tinyPomo"); // Ordner für die Anwendung
        if (!appDirectory.exists()) { // Falls der Ordner nicht existiert, erstelle ihn
            appDirectory.mkdirs();
        }
        return new File(appDirectory, "profile.dat"); // Erstelle im Ordner die Profildatei
    }

}
