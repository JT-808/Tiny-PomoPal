package src.main.java.de.tiny;

import src.main.java.de.tiny.model.ProfileModel;
import javafx.collections.ObservableList;

public class ProfileController {
    private ProfileModel profileModel = new ProfileModel();

    public ObservableList<String> getProfiles() {
        return profileModel.getProfiles();
    }

    public void addProfile(String profile) {
        profileModel.addProfile(profile);
    }

    public void removeProfile(String profile) {
        profileModel.removeProfile(profile);
    }
}


/*
 * todo
 * - profile in eine datei speichern
 * --> Profil soll im Promodorotimerview angezeigt werden
 */

