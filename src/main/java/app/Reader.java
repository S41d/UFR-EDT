package app;

import app.objects.Day;
import app.objects.Week;
import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Reader {
    ArrayList<Day> days = new ArrayList<>();
    ArrayList<Week> weeks = new ArrayList<>();
    int maxWeekNumber = 0;
    int minWeekNumber = 0;

    public ArrayList<Week> getWeeks() {
        return weeks;
    }

    public static void main(String[] args) {
        new Reader();
    }

    public void addToWeek(Day day) {
        for (Week week : weeks) {
            if (week.getWeekNumber() == day.getWeekOfYear()) {
                week.addDay(day);
                break;
            }
        }
    }

    public void addToDay(VEvent event) {
        boolean dayExists = false;
        for (Day day : days) {
            if (day.hasDate(event.getDateStart().getValue())) { // if the day is already in the list then add to day
                dayExists = true;
                day.addEvent(event);
                break;
            }
        }
        if (!dayExists) { // if day isn't in the list then create a new day and add the event to it
            days.add(new Day(event.getDateStart().getValue()));
            days.get(days.size() - 1).addEvent(event);
        }
    }

    public void createWeeks() {
        minWeekNumber = Week.getThisWeek();
        days.forEach(day -> {
            maxWeekNumber = Math.max(maxWeekNumber, day.getWeekOfYear());
            minWeekNumber = Math.min(minWeekNumber, day.getWeekOfYear());
        });
        for (int i = minWeekNumber; i < maxWeekNumber; i++) {
            weeks.add(new Week(i));
        }
        if (weeks.isEmpty()) {
            weeks.add(new Week(minWeekNumber));
        }
    }


    public Reader() {
        run();
    }

    public void run() {
        try {
            ICalendar iCalendar = Biweekly.parse(Files.CALENDAR).first();
            iCalendar.getEvents().forEach(this::addToDay); // Store all the events in days
            days.forEach(day -> day.getEvents().sort(Comparator.comparing(event -> event.getDateStart().getValue()))); // Sort each day's events
            days.sort(Comparator.comparing(Day::getDate)); // Sort days by date
            createWeeks();
            days.forEach(this::addToWeek); //add days to weeks
        } catch (IOException e) {
            createWeeks();
        }
    }
}
