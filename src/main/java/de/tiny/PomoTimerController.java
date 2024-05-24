package src.main.java.de.tiny;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

public class PomoTimerController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ProgressBar FortschrittsBalkenWork;

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

    void startPomodoro(int Work, int Pause, int runden) {
        Timer timer = new Timer();
        ZeitAnzeige.setText((String.valueOf(Work)));

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("test");

            }
        };
        timer.schedule(task, 1000);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ZeitAnzeige.setText("324");
    }

}
