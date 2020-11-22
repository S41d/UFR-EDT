package views.main;

import app.Files;
import app.Props;
import app.Reader;
import app.objects.Day;
import app.objects.Week;
import biweekly.component.VEvent;
import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import views.settings.main.Settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainController {
    public JFXButton nextButton, previousButton;
    public VBox mondayContainer,
            tuesdayContainer,
            wednesdayContainer,
            thursdayContainer,
            fridayContainer,
            saturdayContainer;
    public StackedFontIcon imageView;
    public VBox root;
    public JFXButton settingsButton;
    public JFXButton returnButton;
    public Label dateLabel;

    public VBox[] containers;
    public JFXButton refreshButton;
    Stage settingsStage = new Stage();

    Week week;
    int weekIndex = 0;
    Reader reader = new Reader();


    @FXML
    public void initialize() {
        containers = new VBox[]{
                new VBox(),
                mondayContainer,
                tuesdayContainer,
                wednesdayContainer,
                thursdayContainer,
                fridayContainer,
                saturdayContainer
        };
        setFirstWeek();
        initializer();
    }

    public String getHoursAndMinutes(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String hours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minutes = calendar.get(Calendar.MINUTE) == 0
                ? "00"
                : String.valueOf(calendar.get(Calendar.MINUTE));
        return hours + ":" + minutes;
    }

    private void populateEvents(VEvent event, Day day) {
        // all the containers and fields
        VBox mainEventContainer = new VBox();
        HBox timeContainer = new HBox();
        Label beginning = new Label(), ending = new Label();
        Region filler = new Region();
        HBox detailsContainer = new HBox();
        Label details = new Label();
        Label location = new Label();

        // adding values to the fields
        // -- timeContainer --
        beginning.setText(getHoursAndMinutes(event.getDateStart() != null ? event.getDateStart().getValue() : new Date(0)));
        filler.setPrefWidth(Region.USE_COMPUTED_SIZE);
        HBox.setHgrow(filler, Priority.ALWAYS);
        ending.setText(getHoursAndMinutes(event.getDateEnd() != null ? event.getDateEnd().getValue() : new Date(0)));

        // -- details --
        details.setText(event.getSummary() != null ? event.getSummary().getValue() : "");
        details.setWrapText(true);
        HBox.setHgrow(details, Priority.ALWAYS);
        details.setMaxHeight(Double.MAX_VALUE);
        details.setMinHeight(Region.USE_PREF_SIZE);
        // -- location --
        location.setText(event.getLocation() != null ? event.getLocation().getValue() : "");
        location.setWrapText(true);

        // inserting everything in their containers
        timeContainer.getChildren().addAll(beginning, filler, ending);
        detailsContainer.getChildren().add(details);
        detailsContainer.fillHeightProperty();
        mainEventContainer.getChildren().addAll(timeContainer, detailsContainer, location);

        // add the container to the respective day
        // @formatter:off
        containers[day.dayOfWeek() - 1].getChildren().add(mainEventContainer);
        // @formatter:on
        Animations.zoomIn(mainEventContainer);
        // set styling
        mainEventContainer.getStyleClass().add("event-main");
        timeContainer.getStyleClass().add("event-time");
        beginning.getStyleClass().add("event-time-start");
        ending.getStyleClass().add("event-time-end");
        detailsContainer.getStyleClass().add("event-details");
        location.getStyleClass().add("event-location");
    }

    private void setFirstWeek() {
        ArrayList<Week> weeks = reader.getWeeks();
        this.week = reader.getWeeks().get(weekIndex);
        for (int i = 0, weeksSize = weeks.size(); i < weeksSize; i++) {
            Week week = weeks.get(i);
            if (week.getWeekNumber() == Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)) {
                this.weekIndex = i;
            }
        }
    }

    private void initializer() {
        // set previous button disabled for the first week
        previousButton.setDisable(this.weekIndex == 0);
        // set next button disabled for the last week
        nextButton.setDisable(this.weekIndex == reader.getWeeks().size() - 1);

        // empty all containers
        for (VBox container : containers) {
            container.getChildren().clear();
        }

        for (Day day : week.getDays()) {
            for (VEvent event : day.getEvents()) {
                populateEvents(event, day);
            }
        }
        for (VBox container : containers) {
            if (container.getChildren().isEmpty()) {
                Label label = new Label("Pas d'évènement");
                label.setOpacity(0);
                container.getChildren().add(label);
                Animations.fadeIn(label).play();
            }
        }
        // set theme
        root.getStylesheets().clear();
        root.getStylesheets().add(Files.theme);
        dateLabel.setText(this.week.getBeginDate() + " - " + this.week.getEndDate());
    }

    public void returnButtonAction() {
        setFirstWeek();
        fadeOutAllEvents();
    }

    public void setNextWeek() {
        this.week = reader.getWeeks().get(++weekIndex);
        fadeOutAllEvents();
    }

    public void setPreviousWeek() {
        this.week = reader.getWeeks().get(--weekIndex);
        fadeOutAllEvents();
    }

    private void fadeOutAllEvents() {
        PauseTransition pauseTransition = new PauseTransition(Duration.millis(0));
        pauseTransition.setOnFinished(event -> {
            for (VBox container : containers) {
                container.getChildren().forEach(child -> Animations.fadeOut(child).play());
            }
        });
        SequentialTransition sequentialTransition = new SequentialTransition(
                pauseTransition,
                new PauseTransition(Duration.millis(200))
        );
        sequentialTransition.setOnFinished(event -> initializer());
        sequentialTransition.play();
    }

    @FXML
    private void settingsButtonAction() {
        try {
            new Settings().start(settingsStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refresh() {
        Props properties = new Props();
        Files.downloadFile(properties.get(Props.URL), Files.CALENDAR);
        reader = new Reader();
        fadeOutAllEvents();
    }
}
