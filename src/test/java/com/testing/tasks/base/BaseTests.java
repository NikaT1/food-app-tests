package com.testing.tasks.base;

import com.testing.tasks.managers.DriverManager;
import com.testing.tasks.managers.InitManager;
import com.testing.tasks.managers.PageManager;
import com.testing.tasks.managers.TestPropertiesManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.testing.tasks.utils.PropertiesConstants.BASE_URL;

public class BaseTests {

    protected final TestPropertiesManager properties = TestPropertiesManager.getInstance();
    protected final DriverManager driverManager = DriverManager.getInstance();
    protected final PageManager pageManager = PageManager.getInstance();


    @BeforeAll
    static void beforeAll() {
        InitManager.initFramework();
    }

    @BeforeEach
    void beforeEach() {
        driverManager.getDriver().get(properties.getProperty(BASE_URL));
    }

    @AfterEach
    void afterEach() {
        pageManager.getFoodPage().resetDataFromTable();
    }

    @AfterAll
    static void afterAll() {
        InitManager.quitFramework();
    }
}
