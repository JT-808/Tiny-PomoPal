package src.main.java.de.tiny.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ProfileModel implements Serializable {
    private final HashMap<String, List<Integer>> profiles = new HashMap<>();

    public HashMap<String, List<Integer>> getProfiles() {
        return profiles;
    }

}
