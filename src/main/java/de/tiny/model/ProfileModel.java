package src.main.java.de.tiny.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class ProfileModel implements Serializable {
    private final HashMap<String, Set<String>> profiles = new HashMap<>();

    public HashMap<String, Set<String>> getProfiles() {
        return profiles;
    }
}
