package src.main.java.de.tiny.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ProfileModel implements Serializable {
    
    // Eine HashMap, die Profilnamen (String) den Lernzeiten (List<Integer>) zuordnet
    private final HashMap<String, List<Integer>> profiles = new HashMap<>();

    // Getter-Methode, um auf die HashMap der Profile zuzugreifen
    public HashMap<String, List<Integer>> getProfiles() {
        return profiles;
    }

}
