package app;

import java.io.*;
import java.util.Properties;

public class Props {
    public static final String URL = "url";
    public static final String SET_REFRESH_ON_START = "setRefreshOnStart";
    public static final String THEME_NAME = "theme";
    Properties properties;

    public Props() {
        try {
            Files files = new Files();
            files.check();
            InputStream inputStream = new FileInputStream(files.getSettingsFile());
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String property) {
        return properties.getProperty(property);
    }

    public void set(String property, String value) {
        try (OutputStream outputStream = new FileOutputStream(new Files().getSettingsFile())) {
            properties.setProperty(property, value);
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
