package com.testing.tasks;

import com.testing.tasks.base.BaseDBTests;
import com.testing.tasks.models.Product;
import com.testing.tasks.models.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.stream.Stream;

public class FoodDBTest extends BaseDBTests {

    @ParameterizedTest
    @MethodSource("provideProductToBeAdded")
    @DisplayName("Проверка корректности добавления нового товара в таблицу FOOD " +
            "базы данных тестового стенда посредством выполнения SQL запроса")
    void addNewProductToDBTest(Product newProduct) throws SQLException {
        int newId = getLastIdFromFoodTable() + 1;
        addProductToFoodTable(newId, newProduct);
        Product addedProduct = getProductFromFoodTableById(newId);
        deleteProductFromFoodTableById(newId);
        Assertions.assertEquals(newProduct, addedProduct, "Данные поступившего " +
                "и сохраненного продукта не совпадают");
    }

    @ParameterizedTest
    @MethodSource("provideProductToBeAdded")
    @DisplayName("Проверка корректного сообщения об ошибке при попытке добавления " +
            "нового товара в таблицу FOOD с повторяющимся ID")
    void addNewProductToDBWithRepeatedIdTest(Product newProduct) throws SQLException {
        int lastId = getLastIdFromFoodTable();
        SQLException exception = Assertions.assertThrows(SQLException.class,
                () -> addProductToFoodTable(lastId, newProduct));
        Assertions.assertTrue(exception.getMessage().contains("Нарушение уникального индекса или первичного ключа"),
                "Неверное sql error сообщение");
    }


    private static Stream<Product> provideProductToBeAdded() {
        return Stream.of(
                new Product("Мандарин", ProductType.FRUIT, true),
                new Product("Огурец", ProductType.VEGETABLE, false)
        );
    }

}
