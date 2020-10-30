package app;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class Files {
    public static final File DOWNLOAD_PATH = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + File.separator + ".emploi-du-temps" + File.separator + "downloaded");
    public static final File PATH = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + File.separator + ".emploi-du-temps");
    public static final File SETTINGS = new File(PATH + File.separator + "settings.config");
    public static final File CALENDAR = new File(DOWNLOAD_PATH + File.separator + "calendar.ics");
    private static final Props PROPS = new Props();
    public static String theme = "/themes/" + PROPS.get(Props.THEME_NAME) + ".css";

    public static void refreshTheme() {
        theme = "/themes/" + new Props().get(Props.THEME_NAME) + ".css";
    }

    public void checkMainDir() {
        System.out.println("Checking if :PATH: exists");
        if (!DOWNLOAD_PATH.isDirectory()) {
            System.out.println("Creating Directory");
            DOWNLOAD_PATH.mkdirs();
        }
        System.out.println("--- createMainDir ---");
    }

    public void checkSettingsFile() {
        System.out.println("Checking if :SETTINGS: exists");
        if (!SETTINGS.isFile()) {
            try {
                System.out.println("Creating File");
                OutputStream outputStream = new FileOutputStream(SETTINGS);
                Properties properties = new Properties();
                properties.setProperty("url", "");
                properties.setProperty("theme", "Blue");
                properties.setProperty("setRefreshOnStart", "false");
                properties.store(outputStream, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("--- createSettings ---");
    }

    public File getSettingsFile() {
        return SETTINGS;
    }

    public void check() {
        checkMainDir();
        checkSettingsFile();
        System.out.println("---- Finished -----");
    }

    public static void downloadFile(String link, File file) {
        System.out.println("--- Downloading file ---");
        try {
            // The URL and creating connection
            URL url = new URL(link);
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

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
