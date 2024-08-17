package com.testing.tasks.managers;

import com.testing.tasks.utils.PropertiesConstants;

import java.time.Duration;

public class InitManager {

    private static final DriverManager driverManager = DriverManager.getInstance();
    private final static TestPropertiesManager properties = TestPropertiesManager.getInstance();

    public static void initFramework() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                Integer.parseInt(properties.getProperty(PropertiesConstants.PAGE_LOAD_TIMEOUT))));
        driverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(properties.getProperty(PropertiesConstants.IMPLICITLY_WAIT))));
    }

    public static void quitFramework() {
        driverManager.quiteDriver();
    }
}
