package com.testing.tasks.pages.base;

import com.testing.tasks.managers.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected DriverManager driverManager = DriverManager.getInstance();
    private final WebDriverWait webDriverWait;

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
        webDriverWait = (WebDriverWait) new WebDriverWait(driverManager.getDriver(),
                Duration.ofSeconds(10),
                Duration.ofSeconds(1))
                .ignoring(StaleElementReferenceException.class);
    }

    protected WebElement waitUntilWebElementToBeClickable(WebElement webElement) {
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
    }


    public void waitForNewRowAdded(int initialRowsCount) {
        webDriverWait.until((ExpectedCondition<Boolean>) webDriver -> {
            List<WebElement> element = webDriver.findElements(By.xpath("//tbody/tr"));
            return element != null && element.size() > initialRowsCount;
        });
    }

    @Attachment(value = "Скриншот")
    protected byte[] attachScreenshot() {
        return ((TakesScreenshot) driverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
