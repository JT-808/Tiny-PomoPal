package src.main.java.de.tiny;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
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
    public void wechselZuMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/src/main/resources/de/tiny/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //delay  von 3 sekunden -> danach Timer starten
    void startPomodoro(int Work, int pause, int runden) {
        Timer timer = new Timer();
        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {
                startTimer(timer, Work, pause, runden);
            }
        };
        timer.schedule(delayTask, 3000); // 3 Sekunden Verzögerung
    }

    private void startTimer(Timer timer, int Work, int pause, int runden) {
        TimerTask task = new TimerTask() {
            int verbleibendeSekunden = Work / Work * 60;
            int verbleibendeMinuten = Work - 1;
            int pausenZeit = pause;
            int rundenzaehler = runden;
            boolean arbeitsPhase = true;
    
            @Override
            public void run() {
          
                if (rundenzaehler > 0) {
                    if (verbleibendeMinuten >= 0) {
                        ZeitAnzeige.setText(String.format("%02d:%02d", verbleibendeMinuten, verbleibendeSekunden ));
                        FortschrittsBalken.setProgress((double) verbleibendeMinuten / (arbeitsPhase ? Work : pausenZeit));
                        System.out.println(rundenzaehler);
                        verbleibendeSekunden--;
                        if (verbleibendeSekunden == 0) {
                            verbleibendeMinuten--;
                            verbleibendeSekunden = 59;
                        }
                    } else {
                        if (arbeitsPhase) {
                            verbleibendeMinuten = pausenZeit - 1;
                            verbleibendeSekunden = 59;
                            arbeitsPhase = false;
                        } else {
                            verbleibendeMinuten = Work - 1;
                            verbleibendeSekunden = 59;
                            arbeitsPhase = true;
                            rundenzaehler--;
                        }
                    }
                }
                if (rundenzaehler == 0) {
                    timer.cancel();
                    ZeitAnzeige.setText("   \uD83D\uDC4D"); //Daumen hoch
                    FortschrittsBalken.setProgress(0);
                }
            }
        };
        // auf 1000 stellen, damit es im Minutentakt geht
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    

}
