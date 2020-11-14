package views.settings.main;

import app.Files;
import app.Props;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.main.Main;
import views.settings.urlSelector.UrlSelector;

import java.io.IOException;

public class SettingsController {
    public JFXToggleButton setRefreshOnStartToggle;
    public ToggleGroup color;
    public VBox settingsRoot;
    public JFXRadioButton colorPink,
            colorBlack,
            colorBlue,
            colorGreen,
            colorGrayBlue;
    Props props;

    @FXML
    public void initialize() {
        props = new Props();
        refresh();
        // @formatter:off
        switch (props.get(Props.THEME_NAME)){
            case "Blue": colorBlue.selectedProperty().set(true); break;
            case "Green": colorGreen.selectedProperty().set(true); break;
            case "Black": colorBlack.selectedProperty().set(true); break;
            case "GrayBlue": colorGrayBlue.selectedProperty().set(true); break;
            case "Pink": colorPink.selectedProperty().set(true); break;
        }
        // @formatter:on
        setRefreshOnStartToggle.selectedProperty().set(Boolean.parseBoolean(new Props().get(Props.SET_REFRESH_ON_START)));
    }

    @FXML
    private void close() {
        ((Stage) setRefreshOnStartToggle.getScene().getWindow()).close();
    }

    @FXML
    private void openUrlSelector() {
        try {
            Stage stage = new Stage();
            new UrlSelector().start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setRefreshOnStart() {
        props.set(Props.SET_REFRESH_ON_START, Boolean.toString(setRefreshOnStartToggle.selectedProperty().get()));
    }

    @FXML
    private void setTheme() {
        props.set(Props.THEME_NAME, (String) color.getSelectedToggle().getUserData());
        Files.refreshTheme();
        initialize();
        Main.controller.root.getStylesheets().clear();
        Main.controller.root.getStylesheets().add(Files.theme);
    }

    public void refresh() {
        settingsRoot.getStylesheets().clear();
        settingsRoot.getStylesheets().add(Files.theme);
    }
}
