package reader;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;

public class Read {
    ArrayList<Day> days = new ArrayList<>();
    ArrayList<Week> weeks = new ArrayList<>();
    File file = new File("./downloaded/calendar.ics");

    public void addToWeek(Day day) {
        boolean weekExists = false;
        for (Week week : weeks) {
            if (week.getWeekNumber() == day.getWeekOfYear()){
                weekExists = true;
                week.addDay(day);
                break;
            }
        }

        if (!weekExists){
            weeks.add(new Week(day.getWeekOfYear()));
            weeks.get(weeks.size() -1).addDay(day);
        }

    }

    public void addToDay(VEvent event) {
        boolean dayExists = false;
        for (Day day : days) {
            if (day.hasDate(event.getDateStart().getValue())) {
                dayExists = true;
                day.addEvent(event);
                break;
            }
        }
        if (!dayExists) {
            days.add(new Day(event.getDateStart().getValue()));
            days.get(days.size() - 1).addEvent(event);
        }
    }

    public void run() throws IOException {
        downloadFile();
        ICalendar iCalendar = Biweekly.parse(file).first();
        iCalendar.getEvents().forEach(this::addToDay); // Store all the events in days
        days.forEach(day -> day.events.sort(Comparator.comparing(event -> event.getDateStart().getValue()))); // Sort each day's events
        days.sort((day, t1) -> day.date.compareTo(t1.date.getRawComponents().toDate())); // Sort days by date
        days.forEach(this::addToWeek);
        weeks.forEach(System.out::println);
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

    public Read() throws IOException {
        run();
    }

    public static void main(String[] args) throws IOException {
        new Read();
    }
}
