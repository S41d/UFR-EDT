package reader;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

    File file = new File(Files.PATH + File.separator + "calendar.ics"); // path to our calendar file

    public void createWeeks() {
        minWeekNumber = Week.getThisWeek();
        days.forEach(day -> {
            maxWeekNumber = Math.max(maxWeekNumber, day.getWeekOfYear());
            minWeekNumber = Math.min(minWeekNumber, day.getWeekOfYear());
        });
        for (int i = minWeekNumber; i < maxWeekNumber; i++) {
            weeks.add(new Week(i));
        }
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

    public void run() throws IOException {
        ICalendar iCalendar = Biweekly.parse(file).first(); // parse the downloaded file
        iCalendar.getEvents().forEach(this::addToDay); // Store all the events in days
        days.forEach(day -> day.events.sort(Comparator.comparing(event -> event.getDateStart().getValue()))); // Sort each day's events
        days.sort((day, t1) -> day.date.compareTo(t1.date.getRawComponents().toDate())); // Sort days by date
        createWeeks();
        days.forEach(this::addToWeek); //add days to weeks
    }

    void downloadFile() throws IOException {
        // The URL and creating connection
        URL url = new URL("http://ade.univ-tours.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?data=dfb58676e694c7cf6d48933055c3eb5104e42cb1b6231bfde1a35f60af6eea1b8a209302a3a57afb3553857383c37db83b35a62046be8482c8e9a74d112f972a,1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int status = connection.getResponseCode();

        // handling redirect
        if (status == HttpURLConnection.HTTP_MOVED_TEMP
                || status == HttpURLConnection.HTTP_MOVED_PERM) {
            String location = connection.getHeaderField("Location");
            URL newUrl = new URL(location);
            connection = (HttpURLConnection) newUrl.openConnection();
        }

        // Reading the response and writing to a file
        BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), 1024);
        byte[] buffer = new byte[1024];
        int read;

        while ((read = inputStream.read(buffer, 0, 1024)) >= 0) {
            outputStream.write(buffer, 0, read);
        }

        // Finishing
        outputStream.close();
        inputStream.close();
        System.out.println("File downloaded");
    }


    public Reader() {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
