package app.objects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class Week {
    private final int weekNumber;
    private final ArrayList<Day> days;
    private final int year;
    Calendar calendar = Calendar.getInstance();

    public static int getThisWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public ArrayList<Day> getDays() {
        return this.days;
    }

    public Week(int weekNumber, int year) {
        this.year = year;
        this.days = new ArrayList<>();
        this.weekNumber = weekNumber;
    }

    @Override
    public String toString() {
        return "\n>>>>>>>>>>>>>>> WEEK " + weekNumber + " <<<<<<<<<<<<<<<<\n" +
                days +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n";
    }

    public void addDay(Day day) {
        this.days.add(day);
    }

    public boolean containsDay(Day day) {
        return this.days.contains(day);
    }

    public int getYear() {
        return this.year;
    }

    public String getBeginDate() {
        days.sort(Comparator.comparing(day -> day.getDate().getRawComponents().toDate()));
        String formattedDateStr = "";
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        formattedDateStr += calendar.get(Calendar.DAY_OF_MONTH);
        formattedDateStr += "/";
        formattedDateStr += calendar.get(Calendar.MONTH);
        return formattedDateStr;
    }

    public String getEndDate() {
        days.sort(Comparator.comparing(day -> day.getDate().getRawComponents().toDate()));
        String formattedDateStr = "";
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber + 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        formattedDateStr += calendar.get(Calendar.DAY_OF_MONTH);
        formattedDateStr += "/";
        formattedDateStr += calendar.get(Calendar.MONTH);
        return formattedDateStr;
    }
}
