package src.main.java.de.tiny.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ProfileModel implements Serializable {
    private final TreeSet<String>  profiles = new TreeSet();

    public TreeSet<String> getProfiles() {
        return profiles;
    }
    
 
}
