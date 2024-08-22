package com.testing.tasks.steps;

import com.testing.tasks.managers.DriverManager;
import com.testing.tasks.managers.PageManager;
import com.testing.tasks.managers.TestPropertiesManager;
import com.testing.tasks.models.Product;
import com.testing.tasks.models.ProductType;
import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.*;
import org.junit.jupiter.api.Assertions;

import static com.testing.tasks.utils.PropertiesConstants.BASE_URL;

public class FoodPageSteps {

    private final TestPropertiesManager properties = TestPropertiesManager.getInstance();
    private final DriverManager driverManager = DriverManager.getInstance();
    private final PageManager pageManager = PageManager.getInstance();

    @Допустим("пользователь переходит на страницу продуктов")
    public void userNavigateToProductPage() {
        driverManager.getDriver().get(properties.getProperty(BASE_URL));
    }

    @И("пользователь нажимает на кнопку добавления нового продукта")
    public void userClickAddButton() {
        pageManager.getFoodPage().clickAddFoodButton();
    }

    @И("/пользователь вводит в форму \"([^\"]*)\", \"([^\"]*)\", (true|false) и отправляет форму/")
    public void userFillAndSubmitProductForm(String name, String type, Boolean is_exotic) {
        Product product = Product.builder()
                .name(name)
                .type(ProductType.fromString(type))
                .exotic(is_exotic)
                .build();
        pageManager.getFoodPage().fillAndSubmitFoodForm(product);
    }

    @И("/пользователь видит в последней строке таблицы \"([^\"]*)\", \"([^\"]*)\" и (true|false)/")
    public void userCheckLastProductFromTable(String name, String type, Boolean is_exotic) {
        Product product = Product.builder()
                .name(name)
                .type(ProductType.fromString(type))
                .exotic(is_exotic)
                .build();
        Assertions.assertEquals(pageManager.getFoodPage().getLastRowFromFoodTable(), product,
                "Переданные и сохраненные данные о продукте не совпадают");
    }

    @И("пользователь очищает все добавленные в таблицу данные")
    public void userResetDataFromTable() {
        pageManager.getFoodPage().resetDataFromTable();
    }

}
