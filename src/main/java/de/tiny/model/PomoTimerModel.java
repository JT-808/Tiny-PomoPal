package src.main.java.de.tiny.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PomoTimerModel {

    // ObservableLists für verschiedene Timer-Einstellungen

    // Liste der möglichen Arbeitszeiten (in Minuten)
    ObservableList<Integer> workZeiten = FXCollections.observableArrayList(20, 30, 45, 60, 90);

    // Liste der möglichen Pausenzeiten (in Minuten)
    ObservableList<Integer> pausenZeiten = FXCollections.observableArrayList(5, 10, 15, 30); 

    // Liste der möglichen Rundenzeiten (Anzahl der Runden)
    ObservableList<Integer> rundenZeiten = FXCollections.observableArrayList(1, 2, 3, 4, 5);
    
    // Getter-Methode für die Liste der Arbeitszeiten
    public ObservableList<Integer> getWorkZeiten() {
        return workZeiten;
    }

    // Setter-Methode für die Liste der Arbeitszeiten
    public void setWorkZeiten(ObservableList<Integer> workZeiten) {
        this.workZeiten = workZeiten;
    }

    // Getter-Methode für die Liste der Pausenzeiten
    public ObservableList<Integer> getPausenZeiten() {
        return pausenZeiten;
    }

    // Setter-Methode für die Liste der Pausenzeiten
    public void setPausenZeiten(ObservableList<Integer> pausenZeiten) {
        this.pausenZeiten = pausenZeiten;
    }

    // Getter-Methode für die Liste der Rundenzeiten
    public ObservableList<Integer> getRundenZeiten() {
        return rundenZeiten;
    }

    // Setter-Methode für die Liste der Rundenzeiten
    public void setRundenZeiten(ObservableList<Integer> rundenZeiten) {
        this.rundenZeiten = rundenZeiten;
    }
}
