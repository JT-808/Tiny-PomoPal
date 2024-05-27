package src.main.java.de.tiny.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PomoTimerModel {

    public static ObservableList<Integer> zeitListe = FXCollections.observableArrayList(5, 10, 20);
    public static ObservableList<Integer> rundenListe = FXCollections.observableArrayList(1, 2, 3, 4, 5);

    public static ObservableList<Integer> getZeitListe() {
        return zeitListe;
    }

    public static void setZeitListe(ObservableList<Integer> zeitListe) {
        PomoTimerModel.zeitListe = zeitListe;
    }

    public static ObservableList<Integer> getRundenListe() {
        return rundenListe;
    }

    public static void setRundenListe(ObservableList<Integer> rundenListe) {
        PomoTimerModel.rundenListe = rundenListe;
    }

}
