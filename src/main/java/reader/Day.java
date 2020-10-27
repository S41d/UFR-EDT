package reader;

import biweekly.component.VEvent;
import biweekly.util.ICalDate;

import java.util.ArrayList;
import java.util.Calendar;

public class Day {
    ICalDate date;
    ArrayList<VEvent> events;

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
                    .append("DateStart: ").append(event.getDateStart().getValue().toString()).append("\n")
                    .append("DateEnd: ").append(event.getDateEnd().getValue().toString()).append("\n")
                    .append("Summary: ").append(event.getSummary().getValue()).append("\n")
                    .append("Location: ").append(event.getLocation().getValue()).append("\n")
                    .append("Description: ").append(event.getDescription().getValue()).append("\n");
            eventCounter++;
        }
        return returnString.toString();
    }

    public Day(ICalDate date) {
        this.date = date;
        this.events = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\n------------- Day --------------" +
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int dayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getDate());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
