module App {
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.javafx;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires biweekly;
    requires java.desktop;
    opens themes;
    opens views.root;
    opens views.loading;
    opens views.settings;
}