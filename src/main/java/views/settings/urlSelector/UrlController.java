package views.settings.urlSelector;

import app.Files;
import app.Props;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UrlController {
    public TextField urlTextField;
    public JFXButton cancelButton;
    public JFXButton saveButton;
    public VBox root;

    @FXML
    public void initialize() {
        root.getStylesheets().clear();
        root.getStylesheets().add(Files.theme);
        urlTextField.setText(new Props().get(Props.URL));
    }

    public void cancelAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void saveUrl() {
        new Props().set(Props.URL, urlTextField.getText());
        cancelAction();
    }
}
