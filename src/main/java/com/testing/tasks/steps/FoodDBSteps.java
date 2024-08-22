package com.testing.tasks.steps;

import com.testing.tasks.managers.TestPropertiesManager;
import com.testing.tasks.models.Product;
import com.testing.tasks.models.ProductType;
import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;

import java.sql.*;

import static com.testing.tasks.utils.PropertiesConstants.*;
import static com.testing.tasks.utils.PropertiesConstants.DB_PASSWORD;

public class FoodDBSteps {

    private final TestPropertiesManager properties = TestPropertiesManager.getInstance();
    private Connection connection;
    private int lastId = 0;
    private String lastError = "";

    @Допустим("пользователь устанавливает соединение с базой данных")
    public void connectToDataBase() throws SQLException {
        connection = DriverManager.getConnection(properties.getProperty(DB_URL),
                properties.getProperty(DB_USERNAME),
                properties.getProperty(DB_PASSWORD));
    }

    @И("пользователь находит самый большой id в таблице FOOD")
    public void getLastIdFromFoodTable() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT MAX(food_id) FROM FOOD");
        if (resultSet.next()) {
            lastId = resultSet.getInt(1);
        } else {
            lastId = 0;
        }
    }

    @И("/пользователь выполняет запрос на добавление нового продукта в таблицу: id(.)(\\d+), \"([^\"]*)\", \"([^\"]*)\" и (true|false)/")
    public void addProductToFoodTable(String idAction, int diff, String name, String type, Boolean is_exotic) throws SQLException {
        int id = changeIdByAction(idAction, diff);

        Product product = Product.builder()
                .name(name)
                .type(ProductType.fromString(type))
                .exotic(is_exotic)
                .build();

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO FOOD VALUES(?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, product.getName());
        statement.setString(3, product.getType().name());
        statement.setInt(4, product.isExotic() ? 1 : 0);

        try {
            statement.executeUpdate();
        } catch (SQLException e) {
            lastError = e.getMessage();
        }
    }

    @И("/пользователь выполняет запрос на получение строки из таблицы с id(.)(\\d+) и проверяет содержимое: \"([^\"]*)\", \"([^\"]*)\" и (true|false)/")
    public void getProductFromFoodTableByIdAndCheck(String idAction, int diff, String name, String type, Boolean is_exotic) throws SQLException {
        int id = changeIdByAction(idAction, diff);

        Product product = Product.builder()
                .name(name)
                .type(ProductType.fromString(type))
                .exotic(is_exotic)
                .build();

        PreparedStatement statement = connection.prepareStatement("SELECT * FROM FOOD WHERE FOOD_ID = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        Product addedProduct = null;
        if (resultSet.next()) {
            resultSet.last();
            addedProduct = getProductFromResultSet(resultSet);
        }

        Assertions.assertEquals(product, addedProduct,
                "Переданные и сохраненные данные о продукте не совпадают");
    }

    @И("/пользователь удаляет строку из таблицы с id(.)(\\d+)/")
    public void resetDataFromTableById(String idAction, int diff) throws SQLException {
        int id = changeIdByAction(idAction, diff);

        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM FOOD WHERE food_id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @И("пользователь проверяет, что появилось сообщение об ошибке {string}")
    public void checkLastError(String expectedError) throws SQLException {
        Assertions.assertTrue(lastError.contains(expectedError),
                "Неверное sql error сообщение");
    }

    @И("пользователь закрывает соединение с базой данных")
    public void CloseConnectionToDataBase() throws SQLException {
        connection.close();
    }


    private int changeIdByAction(String action, int diff) {
        return switch (action) {
            case ("+") -> lastId + diff;
            case ("-") -> lastId - diff;
            default -> 0;
        };
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .name(resultSet.getString(2))
                .type(ProductType.valueOf(resultSet.getString(3)))
                .exotic(resultSet.getInt(4) == 1)
                .build();
    }


}
