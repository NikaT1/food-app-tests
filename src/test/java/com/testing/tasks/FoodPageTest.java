package com.testing.tasks;

import com.testing.tasks.base.BaseWebTests;
import com.testing.tasks.models.Product;
import com.testing.tasks.models.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class FoodPageTest extends BaseWebTests {

    @ParameterizedTest
    @MethodSource("provideFruitsToBeAdded")
    @DisplayName("Проверка корректного добавления фруктов в таблицу")
    void addNewFruitToTableTest(Product newProduct) {
        Product addedProduct = pageManager.getFoodPage()
                .clickAddFoodButton()
                .fillAndSubmitFoodForm(newProduct)
                .getLastRowFromFoodTable();
        Assertions.assertEquals(newProduct, addedProduct, "Данные поступившего " +
                "и сохраненного продукта не совпадают");
    }

    @ParameterizedTest
    @MethodSource("provideVegetablesToBeAdded")
    @DisplayName("Проверка корректного добавления овощей в таблицу")
    void addNewVegetableToTableTest(Product newProduct) {
        Product addedProduct = pageManager.getFoodPage()
                .clickAddFoodButton()
                .fillAndSubmitFoodForm(newProduct)
                .getLastRowFromFoodTable();
        Assertions.assertEquals(newProduct, addedProduct, "Данные поступившего " +
                "и сохраненного продукта не совпадают");
    }

    private static Stream<Product> provideFruitsToBeAdded() {
        return Stream.of(
                new Product("Мандарин", ProductType.FRUIT, true),
                new Product("Груша", ProductType.FRUIT, false)
        );
    }

    private static Stream<Product> provideVegetablesToBeAdded() {
        return Stream.of(
                new Product("Огурец", ProductType.VEGETABLE, false),
                new Product("Авокадо", ProductType.VEGETABLE, true)
        );
    }
}
