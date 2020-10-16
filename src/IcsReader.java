import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class IcsReader {
    public void read() throws IOException {
        File file = new File("ADECal.ics");
        ICalendar iCalendar = Biweekly.parse(file).first();
        for (VEvent event : iCalendar.getEvents()) {
            String dateTimeStamp = event.getDateTimeStamp().getValue().toString();
            String dateStart = event.getDateStart().getValue().toString();
            String dateEnd = event.getDateEnd().getValue().toString();
            String summary = event.getSummary().getValue();
            String location = event.getLocation().getValue();
            String description = event.getDescription().getValue();
            System.out.println("dateTimeStamp = " + dateTimeStamp);
            System.out.println("dateStart = " + dateStart);
            System.out.println("dateEnd = " + dateEnd);
            System.out.println("summary = " + summary);
            System.out.println("location = " + location);
            System.out.println("description = " + description);
        }
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
        File file = new File("./downloaded/calendar.ics");
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), 1024);
        byte[] buffer = new byte[1024];
        int read;

        while ((read = inputStream.read(buffer, 0, 1024)) >= 0) {
            System.out.println(read);
            outputStream.write(buffer, 0, read);
        }

        // Finishing
        outputStream.close();
        inputStream.close();
        System.out.println("File downloaded");
    }

    public static void main(String[] args) throws IOException {
        new IcsReader().read();
    }
}
