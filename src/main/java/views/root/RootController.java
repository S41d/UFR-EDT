package views.root;

import biweekly.component.VEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.StackedFontIcon;
import reader.Day;
import reader.Reader;
import reader.Week;

import java.util.Calendar;
import java.util.Date;

public class RootController {
    public Button nextButton, previousButton;
    public VBox mondayContainer,
            tuesdayContainer,
            wednesdayContainer,
            thursdayContainer,
            fridayContainer,
            saturdayContainer;
    public StackedFontIcon imageView;
    public VBox root;

    Week week;
    int weekNumber = 0;
    Reader reader = new Reader();


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
        beginning.setText(getHoursAndMinutes(event.getDateStart().getValue()));
        filler.setPrefWidth(Region.USE_COMPUTED_SIZE);
        HBox.setHgrow(filler, Priority.ALWAYS);
        ending.setText(getHoursAndMinutes(event.getDateEnd().getValue()));

        // -- details --
        details.setText(event.getSummary().getValue());
        details.setWrapText(true);
        HBox.setHgrow(details, Priority.ALWAYS);

        // -- location --
        location.setText(event.getLocation().getValue());
        location.setWrapText(true);

        // inserting everything in their containers
        timeContainer.getChildren().addAll(beginning, filler, ending);
        detailsContainer.getChildren().add(details);
        mainEventContainer.getChildren().addAll(timeContainer, detailsContainer, location);

        // add the container to the respective day
        // @formatter:off
        switch (day.dayOfWeek()) {
            case 2: mondayContainer.getChildren().add(mainEventContainer); break;
            case 3: tuesdayContainer.getChildren().add(mainEventContainer); break;
            case 4: wednesdayContainer.getChildren().add(mainEventContainer); break;
            case 5: thursdayContainer.getChildren().add(mainEventContainer); break;
            case 6: fridayContainer.getChildren().add(mainEventContainer); break;
            case 7: saturdayContainer.getChildren().add(mainEventContainer); break;
        }
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

    @FXML
    public void initialize() {
        // set previous button disabled for the first week
        previousButton.setDisable(this.weekNumber == 0);
        // set next button disabled for the last week
        nextButton.setDisable(this.weekNumber == reader.getWeeks().size() - 1);
        // empty all containers
        mondayContainer.getChildren().clear();
        tuesdayContainer.getChildren().clear();
        wednesdayContainer.getChildren().clear();
        thursdayContainer.getChildren().clear();
        fridayContainer.getChildren().clear();
        saturdayContainer.getChildren().clear();

        this.week = reader.getWeeks().get(weekNumber);
        for (Day day : week.getDays()) {
            for (VEvent event : day.getEvents()) {
                populateEvents(event, day);
            }
        }
    }

    public void setNextWeek() {
        ++weekNumber;
        fadeOut();
    }

    public void setPreviousWeek() {
        --weekNumber;
        fadeOut();
    }

    private void fadeOut() {
        mondayContainer.getChildren().forEach(Animations::fadeOut);
        tuesdayContainer.getChildren().forEach(Animations::fadeOut);
        wednesdayContainer.getChildren().forEach(Animations::fadeOut);
        thursdayContainer.getChildren().forEach(Animations::fadeOut);
        fridayContainer.getChildren().forEach(Animations::fadeOut);
        saturdayContainer.getChildren().forEach(Animations::fadeOut);
        initialize();
    }
}
