package src.main.java.de.tiny.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PomoTimerModel {

    ObservableList<Integer> workZeiten = FXCollections.observableArrayList(20, 30, 45, 60, 90); // Beispielwerte
    ObservableList<Integer> pausenZeiten = FXCollections.observableArrayList(5, 10, 15, 30); // Beispielwerte
    ObservableList<Integer> rundenZeiten = FXCollections.observableArrayList(1, 2, 3, 4, 5); // Beispielwerte
    
    
    public ObservableList<Integer> getWorkZeiten() {
        return workZeiten;
    }
    public void setWorkZeiten(ObservableList<Integer> workZeiten) {
        this.workZeiten = workZeiten;
    }
    public ObservableList<Integer> getPausenZeiten() {
        return pausenZeiten;
    }
    public void setPausenZeiten(ObservableList<Integer> pausenZeiten) {
        this.pausenZeiten = pausenZeiten;
    }
    public ObservableList<Integer> getRundenZeiten() {
        return rundenZeiten;
    }
    public void setRundenZeiten(ObservableList<Integer> rundenZeiten) {
        this.rundenZeiten = rundenZeiten;
    }



}
