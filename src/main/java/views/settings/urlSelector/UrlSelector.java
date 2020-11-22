package views.settings.urlSelector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UrlSelector extends Application {
    public static UrlController controller;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/urlSelector.fxml"));
        Parent parent = loader.load();
        controller = loader.getController();
        primaryStage.setTitle("UFR emploi du temps - Paramètres");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
