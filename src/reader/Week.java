package reader;

import java.util.ArrayList;

public class Week {
    private int weekNumber;
    private ArrayList<Day> days;

    public int getWeekNumber() {
        return weekNumber;
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
