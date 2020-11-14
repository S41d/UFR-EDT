package views.loading;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Loading extends Application {
    @Override
    public void start(Stage loadingStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loading.fxml"));
        loadingStage.setTitle("UFR emploi du temps");
        loadingStage.setResizable(false);
        loadingStage.initStyle(StageStyle.UNDECORATED);
        loadingStage.setScene(new Scene(loader.load()));
        loadingStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
