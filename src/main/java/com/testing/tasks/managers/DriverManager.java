package com.testing.tasks.managers;

import com.testing.tasks.utils.PropertiesConstants;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
        setDriverDependsOnBrowser();
        driver = new ChromeDriver();
    }

    private void setDriverDependsOnBrowser() {
        switch (properties.getProperty(PropertiesConstants.TYPE_BROWSER, "chrome")) {
            case ("chrome"):
                System.setProperty("webdriver.chrome.driver",
                        properties.getProperty(PropertiesConstants.CHROME_DRIVER));
                break;
            case ("firefox"):
                System.setProperty("webdriver.gecko.driver",
                        properties.getProperty(PropertiesConstants.GECKO_DRIVER));
                break;
            default:
                Assertions.fail("Задан недопустимый браузер");
        }
    }

    public void quiteDriver() {
        driver.quit();
        driver = null;
    }
}