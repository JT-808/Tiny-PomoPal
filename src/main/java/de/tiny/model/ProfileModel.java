package src.main.java.de.tiny.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProfileModel {
    private ObservableList<String> profiles = FXCollections.observableArrayList();

    public ProfileModel() {
        // Beispielprofile hinzufügen
        profiles.addAll("Julia", "Sue", "Hannah", "Stephan", "Denise");
    }

    public ObservableList<String> getProfiles() {
        return profiles;
    }

    public void addProfile(String profile) {
        profiles.add(profile);
    }

    public void removeProfile(String profile) {
        profiles.remove(profile);
    }
}
