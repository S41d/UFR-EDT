package views.loading;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import app.Files;
import app.Props;
import views.root.Root;

import java.io.IOException;

public class LoadingController {
    public Label loadingLabel;
    public FontIcon loadingIcon;
    public AnchorPane loadingRoot;

    @FXML
    public void initialize() {
        loadingRoot.getStylesheets().clear();
        loadingRoot.getStylesheets().add(Files.THEME);
        PauseTransition delayTransition = new PauseTransition(Duration.seconds(2));
        delayTransition.setOnFinished(event -> {
            try {
                new Files().check();
                Props properties = new Props();
                if (properties.isRefreshOnStart()) {
                    Files.downloadFile(properties.getUrl(), Files.CALENDAR);
                }
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
