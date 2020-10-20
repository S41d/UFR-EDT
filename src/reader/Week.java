package reader;

import java.util.ArrayList;

public class Week {
    private final int weekNumber;
    private final ArrayList<Day> days;

    public int getWeekNumber() {
        return weekNumber;
    }

    public ArrayList<Day> getDays () {
        return this.days;
    }

    public Week(int weekNumber) {
        this.days = new ArrayList<>();
        this.weekNumber = weekNumber;
    }

    @Override
    public String toString() {
        return "\n>>>>>>>>>>>>>>> WEEK <<<<<<<<<<<<<<<<\n" +
                days +
                "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n";
    }

    public void addDay(Day day) {
        this.days.add(day);
    }

    public boolean containsDay(Day day) {
        return this.days.contains(day);
    }
}
