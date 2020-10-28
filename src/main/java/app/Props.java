package app;

import java.io.*;
import java.util.Properties;

public class Props {
    private String url;
    private String themeName;
    private Boolean refreshOnStart;
    Properties properties;

    public Props() {
        try {
            InputStream inputStream = new FileInputStream(new Files().getSettingsFile());
            properties = new Properties();
            properties.load(inputStream);
            this.url = properties.getProperty("url");
            this.themeName = properties.getProperty("theme");
            this.refreshOnStart = Boolean.parseBoolean(properties.getProperty("setRefreshOnStart"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getThemeName() {
        return themeName;
    }

    public Boolean isRefreshOnStart() {
        return refreshOnStart;
    }
}
