module UFR.EDT {
    requires biweekly;
    requires java.desktop;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.jfoenix;

    opens themes;
    opens views.loading;
    opens views.main;
    opens views.settings.main;
    opens views.settings.urlSelector;
}