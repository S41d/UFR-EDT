package reader;

import biweekly.component.VEvent;
import biweekly.util.ICalDate;

import java.util.ArrayList;

public class Day {
    ICalDate date;
    ArrayList<VEvent> events;

    public ICalDate getDate() {
        return date;
    }

    public ArrayList<VEvent> getEvents() {
        return events;
    }

    public Day(ICalDate date) {
        this.date = date;
        this.events = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "\n############# Day ##############" +
                "\n ----- DATE ----- \n" + date +
                "\n ----- EVENTS ----- \n" + events +
                "\n###############################\n";
    }

    public void addEvent(VEvent event) {
        this.events.add(event);
    }

    public boolean hasDate(ICalDate date) {
        return this.date.getRawComponents().getDate() == date.getRawComponents().getDate()
                && this.date.getRawComponents().getMonth() == date.getRawComponents().getMonth();
    }
}
