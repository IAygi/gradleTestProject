package ru.iaygi.ui.tests;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class TestBaseUi {
    ChromeOptions options;

    public void initDriver() {
        options = new ChromeOptions();
        options.setCapability("browserVersion", "113.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "Website Test");
            put("sessionTimeout", "30m");
            put("enableVNC", false);
            put("screenResolution", "1920x1080x24");
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            put("enableVideo", true);
        }});
    }
}
