package reader;

import java.util.ArrayList;

public class Week {
    int weekNumber;
    ArrayList<Day> days = new ArrayList<>();

    public Week(int weekNumber, ArrayList<Day> days) {
        this.weekNumber = weekNumber;
        this.days = days;
    }

    @Override
    public String toString() {
        return "Week{" +
                "weekNumber=" + weekNumber +
                ", days=" + days +
                '}';
    }
}
