package app.objects;

import biweekly.component.VEvent;
import biweekly.util.ICalDate;

import java.util.ArrayList;
import java.util.Calendar;

public class Day {
    ICalDate date;
    ArrayList<VEvent> events;
    Calendar calendar = Calendar.getInstance();

    public ICalDate getDate() {
        return date;
    }

    public ArrayList<VEvent> getEvents() {
        return events;
    }

    public String getEventsStr() {
        StringBuilder returnString = new StringBuilder();
        int eventCounter = 1;
        for (VEvent event : events) {
            returnString.append("--- Event ").append(eventCounter).append(" --- \n")
                    .append("DateStart: ").append(event.getDateStart() != null ? event.getDateStart().getValue().toString() : "").append("\n")
                    .append("DateEnd: ").append(event.getDateEnd() != null ? event.getDateEnd().getValue().toString() : "").append("\n")
                    .append("Summary: ").append(event.getSummary() != null ? event.getSummary().getValue() : "").append("\n")
                    .append("Location: ").append(event.getLocation() != null ? event.getLocation().getValue() : "").append("\n")
                    .append("Description: ").append(event.getDescription() != null ? event.getDescription().getValue() : "").append("\n");
            eventCounter++;
        }
        return returnString.toString();
    }

    public Day(ICalDate date) {
        this.date = date;
        this.events = new ArrayList<>();
        calendar.setTime(date);
    }

    @Override
    public String toString() {
        return "\n------------- Day " + dayOfWeek() + " --------------" +
                "\n ----- DATE ----- \n" +
                date +
                "\n ----- EVENTS ----- \n" +
                getEventsStr() +
                "--------------------------------";
    }

    public void addEvent(VEvent event) {
        this.events.add(event);
    }

    public boolean hasDate(ICalDate date) {
        return this.date.getRawComponents().getDate() == date.getRawComponents().getDate()
                && this.date.getRawComponents().getMonth() == date.getRawComponents().getMonth();
    }

    public int getWeekOfYear() {
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int dayOfWeek() {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }
}
