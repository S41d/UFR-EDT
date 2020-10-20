package reader;

import java.util.ArrayList;

public class Week {
    ArrayList<Day> days;

    public Week() {
        this.days = new ArrayList<>();
    }

    @Override
    public String toString() {
        return ">>>>>>>>>>>>>>> WEEK <<<<<<<<<<<<<<<<" +
                "days=" + days +
                ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
    }

    public void addDay(Day day) {
        this.days.add(day);
    }
}
