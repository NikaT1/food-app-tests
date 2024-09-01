package com.testing.tasks.managers;

import static com.testing.tasks.utils.PropertiesConstants.*;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

public class DriverManager {
    private WebDriver driver;
    private static DriverManager INSTANCE;

    private final TestPropertiesManager properties = TestPropertiesManager.getInstance();

    private DriverManager() {
    }

    public static DriverManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    private void initDriver() {
        if ("remote".equalsIgnoreCase(TYPE_DRIVER)) {
            initRemoteDriver();
        } else {
            setDriverDependsOnBrowser();
        }
    }

    private void initRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(TYPE_BROWSER);
        capabilities.setVersion("108.0");
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", false
        ));
        try {
            driver = new RemoteWebDriver(URI.create(SELENOID_URL).toURL(), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDriverDependsOnBrowser() {
        switch (properties.getProperty(TYPE_BROWSER, "chrome")) {
            case ("chrome") -> {
                System.setProperty("webdriver.chrome.driver",
                        properties.getProperty(CHROME_DRIVER));
                driver = new ChromeDriver();
            }
            case ("firefox") -> {
                System.setProperty("webdriver.gecko.driver",
                        properties.getProperty(GECKO_DRIVER));
                driver = new FirefoxDriver();
            }
            default -> Assertions.fail("Задан недопустимый браузер");
        }
    }

    public void quiteDriver() {
        driver.quit();
        driver = null;
    }
}