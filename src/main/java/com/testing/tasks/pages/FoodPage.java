package com.testing.tasks.pages;

import com.testing.tasks.models.Product;
import com.testing.tasks.models.ProductType;
import com.testing.tasks.pages.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class FoodPage extends BasePage {

    @FindBy(xpath = "//button[contains(text(), 'Добавить')]")
    private WebElement addFoodButton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement foodNameInput;

    @FindBy(xpath = "//select[@id='type']")
    private WebElement foodTypeSelect;

    @FindBy(xpath = "//input[@id='exotic']")
    private WebElement foodExoticCheckbox;

    @FindBy(xpath = "//button[@id='save']")
    private WebElement saveFoodButton;

    @FindBy(xpath = "//li/a[contains(text(), 'Песочница')]")
    private WebElement sandboxDropdownMenu;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> tableRows;

    @FindBy(css = "tbody tr:last-child")
    private WebElement lastRow;

    @FindBy(xpath = "//a[@id='reset']")
    private WebElement resetData;


    public FoodPage clickAddFoodButton() {
        addFoodButton.click();
        return this;
    }

    public FoodPage fillAndSubmitFoodForm(Product product) {
        waitUntilWebElementToBeClickable(foodNameInput);
        foodNameInput.sendKeys(product.getName());
        new Select(foodTypeSelect).selectByValue(product.getType().name());
        if (product.isExotic()) foodExoticCheckbox.click();
        int initialRowsCount = tableRows.size();
        attachScreenshot();
        saveFoodButton.click();
        waitForNewRowAdded(initialRowsCount);
        return this;
    }

    public Product getLastRowFromFoodTable() {
        String name = lastRow.findElement(By.cssSelector("td:nth-child(2)")).getText();
        ProductType type = ProductType.fromString(lastRow.findElement(
                By.cssSelector("td:nth-child(3)")).getText());
        boolean exotic = Boolean.parseBoolean(lastRow.findElement(
                By.cssSelector("td:nth-child(4)")).getText());
        attachScreenshot();
        return new Product(name, type, exotic);
    }

    public void resetDataFromTable() {
        sandboxDropdownMenu.click();
        waitUntilWebElementToBeClickable(resetData);
        resetData.click();
    }
}
