package views.loading;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import reader.Files;
import views.root.Root;

import java.io.IOException;

public class LoadingController {
    public Label loadingLabel;
    public FontIcon loadingIcon;
    public AnchorPane loadingRoot;

    @FXML
    public void initialize() {
        PauseTransition delayTransition = new PauseTransition(Duration.seconds(2));
        delayTransition.setOnFinished(event -> {
            try {
                new Files().check();
                ((Stage)loadingRoot.getScene().getWindow()).close();
                Stage stage = new Stage();
                new Root().start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        delayTransition.play();
    }
}
