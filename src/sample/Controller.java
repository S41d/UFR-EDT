package sample;

import biweekly.component.VEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import reader.Day;
import reader.Reader;
import reader.Week;

import java.util.Calendar;
import java.util.Date;

public class Controller {
    public ImageView imageView;
    @FXML
    private VBox mondayContainer,
            tuesdayContainer,
            wednesdayContainer,
            thursdayContainer,
            fridayContainer,
            saturdayContainer,
            sundayContainer;

    Week week;
    int weekNumber = 0;
    Reader reader = new Reader();

    public void setWeek(Week week) {
        this.week = week;
    }

    public int dayOfWeek(Day day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day.getDate());
        return calendar.get(Calendar.DAY_OF_WEEK);
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
        VBox mainContainer = new VBox();
        HBox timeContainer = new HBox();
        Label beginning = new Label(), ending = new Label();
        Region filler = new Region();
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

        // -- location --
        location.setText(event.getLocation().getValue());
        location.setWrapText(true);

        // inserting everything in their containers
        timeContainer.getChildren().addAll(beginning, filler, ending);
        mainContainer.getChildren().addAll(timeContainer, details, location);

        // add the container to the respective day
        switch (dayOfWeek(day)) {
            case 1:
                sundayContainer.getChildren().add(mainContainer);
                break;
            case 2:
                mondayContainer.getChildren().add(mainContainer);
                break;
            case 3:
                tuesdayContainer.getChildren().add(mainContainer);
                break;
            case 4:
                wednesdayContainer.getChildren().add(mainContainer);
                break;
            case 5:
                thursdayContainer.getChildren().add(mainContainer);
                break;
            case 6:
                fridayContainer.getChildren().add(mainContainer);
                break;
            case 7:
                saturdayContainer.getChildren().add(mainContainer);
                break;
        }

        // set styling
        mainContainer.getStyleClass().add("event-main");
        timeContainer.getStyleClass().add("event-time");
        beginning.getStyleClass().add("event-time-start");
        ending.getStyleClass().add("event-time-end");
        details.getStyleClass().add("event-details");
        location.getStyleClass().add("event-location");

        details.setPrefWidth(Double.MAX_VALUE);
    }

    @FXML
    public void initialize() {
//        imageView.setImage(new Image(getClass().getResourceAsStream("../../downloaded/icon.png")));
        setWeek(reader.getWeeks().get(weekNumber));
        for (Day day : week.getDays()) {
            for (VEvent event : day.getEvents()) {
                populateEvents(event, day);
            }
        }
    }

    public String eventStr(VEvent event) {
        return "DateStart: " + event.getDateStart().getValue().toString() + "\n" +
                "DateEnd: " + event.getDateEnd().getValue().toString() + "\n" +
                "Summary: " + event.getSummary().getValue() + "\n" +
                "Location: " + event.getLocation().getValue() + "\n" +
                "Description: " + event.getDescription().getValue() + "\n";
    }

    public void setNextWeek() {
        ++weekNumber;
        initialize();
    }

    public void setPreviousWeek() {
        --weekNumber;
        initialize();
    }
}

