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

    @FindBy(xpath = "//table/tbody")
    private WebElement foodTable;

    @FindBy(xpath = "//li/a[contains(text(), 'Песочница')]")
    private WebElement sandboxDropdownMenu;

    @FindBy(xpath = "//a[@id='reset']")
    private WebElement resetData;


    public FoodPage clickAddFoodButton() {
        addFoodButton.click();
        return this;
    }

    public FoodPage fillAndSubmitFoodForm(Product product) {
        foodNameInput.sendKeys(product.getName());
        new Select(foodTypeSelect).selectByValue(product.getType().name());
        if (product.isExotic()) foodExoticCheckbox.click();
        saveFoodButton.click();
        return this;
    }

    public Product getLastRowFromFoodTable() {
        WebElement lastRow = foodTable.findElement(By.cssSelector("tr:last-child"));
        String name = lastRow.findElement(By.xpath("./td[0]")).getText();
        ProductType type = ProductType.fromString(lastRow.findElement(
                By.xpath("./td[1]")).getText());
        boolean exotic = Boolean.parseBoolean(lastRow.findElement(
                By.xpath("./td[2]")).getText());
        return new Product(name, type, exotic);
    }

    public void resetDataFromTable() {
        sandboxDropdownMenu.click();
        waitUntilWebElementToBeClickable(resetData);
        resetData.click();
    }
}
