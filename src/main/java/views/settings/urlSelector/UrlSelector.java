package views.settings.urlSelector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UrlSelector extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/urlSelector.fxml"));
        primaryStage.setTitle("UFR emploi du temps - Paramètres");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }
}
