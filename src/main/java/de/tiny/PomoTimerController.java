package src.main.java.de.tiny;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class PomoTimerController {

    // Attribute zur Steuerung der Scene und des Timers
    private Stage stage;
    private Scene scene;
    private Parent root;

    // UI-Elemente
    @FXML
    private ProgressBar FortschrittsBalken; // Fortschrittsbalken für den Timer

    @FXML
    private Text ZeitAnzeige; // Text zur Anzeige der verbleibenden Zeit

    @FXML
    private Button buttonStop; // Button zum Stoppen des Timers und zum zurückkommen zur Main

    @FXML
    private Text workPauseText; // Text zur Anzeige der aktuellen Phase ("work" oder "pause")

    /**
     * Diese Methode wird aufgerufen, wenn der Benutzer zur Hauptseite zurückkehren möchte.
     * Sie lädt die Hauptseite (Main.fxml) und zeigt sie an.
     *
     * @param event Das ActionEvent, das den Wechsel auslöst
     * @throws IOException Wenn ein Fehler beim Laden der FXML-Datei auftritt
     */
    @FXML
    public void wechselZuMain(ActionEvent event) throws IOException {
        // Laden der Main.fxml-Datei
        root = FXMLLoader.load(getClass().getResource("/src/main/resources/de/tiny/Main.fxml"));
        // Abrufen der aktuellen Stage und Setzen der neuen Scene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Startet den Pomodoro-Timer mit den angegebenen Parametern.
     * Beginnt mit einem kurzen Countdown, bevor der Timer gestartet wird.
     *
     * @param work Die Dauer der Arbeitsphase in Minuten
     * @param pause Die Dauer der Pausenphase in Minuten
     * @param runden Die Anzahl der Wiederholungsrunden
     * @param profil Der Name des Profils, das verwendet wird
     * @param profileController Der Controller für die Profile
     */
    void startPomodoro(int work, int pause, int runden, String profil, ProfileController profileController) {
        // Timer für den Countdown vor dem Start des Pomodoro-Timers
        Timer delayTimer = new Timer();
        TimerTask delayTask = new TimerTask() {
            int countdown = 3; // Countdown in Sekunden

            @Override
            public void run() {
                if (countdown > 0) {
                    workPauseText.setText(String.valueOf(countdown));
                    countdown--;
                } else {
                    delayTimer.cancel();
                    // Timer starten, nachdem der Countdown abgelaufen ist
                    startTimer(new Timer(), work, pause, runden, profil, profileController);
                }
            }
        };
        delayTimer.scheduleAtFixedRate(delayTask, 0, 1000); // Countdown alle 1 Sekunde aktualisieren
    }

    /**
     * Startet den eigentlichen Pomodoro-Timer.
     * Der Timer wechselt zwischen Arbeits- und Pausenphasen und aktualisiert die UI entsprechend.
     *
     * @param timer Der Timer, der für die Zeitmessung verwendet wird
     * @param work Die Dauer der Arbeitsphase in Minuten
     * @param pause Die Dauer der Pausenphase in Minuten
     * @param runden Die Anzahl der Wiederholungsrunden
     * @param profil Der Name des Profils, das verwendet wird
     * @param profileController Der Controller für die Profile
     */
    private void startTimer(Timer timer, int work, int pause, int runden, String profil, ProfileController profileController) {
        TimerTask task = new TimerTask() {
            int verbleibendeSekunden = 0; // Verbleibende Sekunden in der aktuellen Phase
            int verbleibendeMinuten = 0; // Verbleibende Minuten in der aktuellen Phase
            int pausenZeit = pause; // Dauer der Pausenphase
            int rundenzaehler = runden; // Anzahl der verbleibenden Runden
            boolean arbeitsPhase = true; // Aktuelle Phase (true für Arbeit, false für Pause)

            @Override
            public void run() {
                if (rundenzaehler > 0) {
                    if (verbleibendeMinuten == 0 && verbleibendeSekunden == 0) {
                        if (arbeitsPhase) {
                            // Wechsel von der Arbeitsphase zur Pausenphase
                            verbleibendeMinuten = pausenZeit - 1;
                            verbleibendeSekunden = 59;
                            arbeitsPhase = false;
                            setWorkPauseText(); // Text für die nächste Phase aktualisieren
                        } else {
                            // Wechsel von der Pausenphase zur Arbeitsphase
                            verbleibendeMinuten = work - 1;
                            verbleibendeSekunden = 59;
                            arbeitsPhase = true;
                            rundenzaehler--;
                            setWorkPauseText(); // Text für die nächste Phase aktualisieren
                        }
                    } else {
                        // Aktualisierung der verbleibenden Zeit
                        if (verbleibendeSekunden == 0) {
                            verbleibendeMinuten--;
                            verbleibendeSekunden = 59;
                        } else {
                            verbleibendeSekunden--;
                        }

                        // Zeit- und Fortschrittsanzeige aktualisieren
                        ZeitAnzeige.setText(String.format("%02d:%02d", verbleibendeMinuten, verbleibendeSekunden));
                        FortschrittsBalken.setProgress(berechneFortschritt(arbeitsPhase, work, pause, verbleibendeMinuten, verbleibendeSekunden));
                    }
                } else {
                    // Timer beendet, alle Anzeigen zurücksetzen und Zeiten speichern
                    timer.cancel();
                    workPauseText.setText("finish");
                    profileController.zeitEinfuegen(profil, work * runden);
                    ZeitAnzeige.setText("total learntime: " + profileController.getLearnTime(profil));
                    ZeitAnzeige.setStyle("-fx-font-size: 16px;"); // Textgröße für die Zeitanzeige ändern
                    profileController.saveProfilesToFile();
                }
            }
        };
        // Timer alle 30 Millisekunden ausführen, um die Zeitaktualisierung zu gewährleisten
        // aktuell wird die Zeit für testzwecke sehr schnell ablaufen
        // Timer auf 1000 stellen um die die reguläre Zeit ablaufen zu lassen
        timer.scheduleAtFixedRate(task, 0, 30);
    }

    /**
     * Berechnet den Fortschritt des Timers basierend auf der verbleibenden Zeit.
     *
     * @param arbeitsPhase Ob sich der Timer in der Arbeitsphase befindet
     * @param work Die Dauer der Arbeitsphase in Minuten
     * @param pause Die Dauer der Pausenphase in Minuten
     * @param verbleibendeMinuten Verbleibende Minuten in der aktuellen Phase
     * @param verbleibendeSekunden Verbleibende Sekunden in der aktuellen Phase
     * @return Der Fortschritt des Timers als Prozentsatz (zwischen 0.0 und 1.0)
     */
    private double berechneFortschritt(boolean arbeitsPhase, int work, int pause, int verbleibendeMinuten, int verbleibendeSekunden) {
        int totalSeconds = (arbeitsPhase ? work : pause) * 60; // Gesamtdauer der Phase in Sekunden
        int remainingSeconds = (verbleibendeMinuten * 60) + verbleibendeSekunden; // Verbleibende Zeit in Sekunden
        return (double) remainingSeconds / totalSeconds; // Fortschritt berechnen
    }

    /**
     * Aktualisiert den Text für die aktuelle Phase und spielt einen Sound ab.
     * Der Text wechselt zwischen "work" und "pause".
     */
    public void setWorkPauseText() {
        try {
            spieleSound(); // Sound abspielen
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace(); // Fehlerbehandlung beim Abspielen des Sounds
        }

        // Text für die Phase aktualisieren
        if (!workPauseText.getText().equals("work")) {
            workPauseText.setText("work");
        } else {
            workPauseText.setText("pause");
        }
    }

    /**
     * Spielt einen Sound ab, um den Phasenwechsel zu signalisieren.
     * Sound wurde selbst aufgenommen
     *
     * @throws LineUnavailableException Wenn die Audio-Leitung nicht verfügbar ist
     * @throws UnsupportedAudioFileException Wenn das Audioformat nicht unterstützt wird
     * @throws IOException Wenn ein Fehler beim Laden der Audio-Datei auftritt
     */
    public static void spieleSound() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File soundFile = new File("src/main/resources/de/tiny/beep.wav"); // Pfad zur Audio-Datei

        final Clip clip = AudioSystem.getClip(); // Clip zum Abspielen des Sounds
        final AudioInputStream in = AudioSystem.getAudioInputStream(soundFile); // AudioInputStream für die Datei
        clip.open(in); // Clip öffnen
        clip.start(); // Clip starten
    }
}
