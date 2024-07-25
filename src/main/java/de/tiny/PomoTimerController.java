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

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ProgressBar FortschrittsBalken;

    @FXML
    private Text ZeitAnzeige;

    @FXML
    private Button buttonStop;

    @FXML
    private Text workPauseText;

    // Phasen als Text anzeigen lassen mit umschaltung
    public void setWorkPauseText() {

        try {
            spieleSound();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!workPauseText.getText().equals("work")) {
            workPauseText.setText("work");
        } else {
            workPauseText.setText("pause");
        }
    }

    // mit der Stop Taste wieder zurueck zur Hauptseite
    @FXML
    public void wechselZuMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/src/main/resources/de/tiny/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

     ///////////////// Start des Pomodoro-Timers ////////////////

     void startPomodoro(int work, int pause, int runden) {

    //Am anfang ein kurzer Delay damit es nicht sofort los geht
    
        Timer delayTimer = new Timer();
        TimerTask delayTask = new TimerTask() {
            int countdown = 3;

            @Override
            public void run() {
                if (countdown > 0) {
                    workPauseText.setText(String.valueOf(countdown));
                    countdown--;
                } else {
                    delayTimer.cancel();
                    startTimer(new Timer(), work, pause, runden);
                }
            }
        };
        delayTimer.scheduleAtFixedRate(delayTask, 0, 1000);
    }


    // der eigentliche Timer
    private void startTimer(Timer timer, int work, int pause, int runden) {
        TimerTask task = new TimerTask() {
            int verbleibendeSekunden = 0;
            int verbleibendeMinuten = 0;
            int pausenZeit = pause;
            int rundenzaehler = runden;
            boolean arbeitsPhase = true;

            @Override
            public void run() {
                
                if (rundenzaehler > 0) {
                    if (verbleibendeMinuten == 0 && verbleibendeSekunden == 0) {
                        if (arbeitsPhase) {


                            verbleibendeMinuten = pausenZeit - 1;
                            verbleibendeSekunden = 59;
                            arbeitsPhase = false;
                            setWorkPauseText();


                        } else {
                            verbleibendeMinuten = work - 1;
                            verbleibendeSekunden = 59;
                            arbeitsPhase = true;
                            rundenzaehler--;
                            setWorkPauseText(); // Phase wechseln

                        }
                    } else {



                        if (verbleibendeSekunden == 0) {
                            verbleibendeMinuten--;
                            verbleibendeSekunden = 59;
                        } else {
                            verbleibendeSekunden--;
                        }

                            // Zeit dauerhaft aktuallisieren und anzeigen
                            ZeitAnzeige.setText(String.format("%02d:%02d", verbleibendeMinuten, verbleibendeSekunden));

                           // Fortschrittsbalken dauerhaft aktualisieren & anzeigen
                            FortschrittsBalken.setProgress(berechneFortschritt(arbeitsPhase, work, pause, verbleibendeMinuten, verbleibendeSekunden));

                            }
                } else {
                    
                    timer.cancel();
                    workPauseText.setText("finish");
                    ZeitAnzeige.setText("   \uD83D\uDC4D"); // Daumen hoch
                    FortschrittsBalken.setProgress(0);
                }
            }
        };
        // Timer alle 1000 ms (1 Sekunde) ausführen
        timer.scheduleAtFixedRate(task, 0, 30);
    }

   
    private double berechneFortschritt(boolean arbeitsPhase, int work, int pause, int verbleibendeMinuten, int verbleibendeSekunden) {
        int totalSeconds = (arbeitsPhase ? work : pause) * 60;
        int remainingSeconds = (verbleibendeMinuten * 60) + verbleibendeSekunden;
        return (double) remainingSeconds / totalSeconds;
    }




    // Sound abspielen -> eventuell auslagern



    public static void spieleSound() throws LineUnavailableException, UnsupportedAudioFileException, IOException{
         File soundFile = new File("src/main/resources/de/tiny/beep.wav");

        final Clip clip = AudioSystem.getClip();
        final AudioInputStream in = AudioSystem.getAudioInputStream(soundFile);
        //clip.setFramePosition(5);
       
        clip.open(in);
        clip.start();
       // clip.close();

    }

}






