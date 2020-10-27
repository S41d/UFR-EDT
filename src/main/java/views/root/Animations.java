package views.root;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {
    public static void zoomOut(Node node) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(200), node);
        transition.setFromX(1.1);
        transition.setToX(1);
        transition.setFromY(1.1);
        transition.setToY(1);
        transition.play();
    }

    public static Transition fadeOut(Node child) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), child);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        return fadeTransition;
    }

    public static void zoomIn(Node node) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(200), node);
        SequentialTransition sequence = new SequentialTransition(
                new PauseTransition(Duration.millis(300)),
                transition
        );
        transition.setFromX(0);
        transition.setToX(1.1);
        transition.setFromY(0);
        transition.setToY(1.1);
        sequence.play();
        sequence.setOnFinished(event -> zoomOut(node));
    }
}
