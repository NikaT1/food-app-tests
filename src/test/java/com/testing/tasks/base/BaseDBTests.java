package com.testing.tasks.base;

import com.testing.tasks.managers.TestPropertiesManager;
import com.testing.tasks.models.Product;
import com.testing.tasks.models.ProductType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.*;

import static com.testing.tasks.utils.PropertiesConstants.*;

public class BaseDBTests {
    protected static final TestPropertiesManager properties = TestPropertiesManager.getInstance();
    protected static Connection connection;


    @BeforeAll
    static void beforeAll() throws SQLException {
        connection = DriverManager.getConnection(properties.getProperty(DB_URL),
                properties.getProperty(DB_USERNAME),
                properties.getProperty(DB_PASSWORD));
    }

    @AfterAll
    static void afterAll() throws SQLException {
        connection.close();
    }

    protected void addProductToFoodTable(int id, Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO FOOD VALUES(?, ?, ?, ?)");
        statement.setInt(1, id);
        statement.setString(2, product.getName());
        statement.setString(3, product.getType().name());
        statement.setInt(4, product.isExotic() ? 1 : 0);

        statement.executeUpdate();
    }

    protected Product getProductFromFoodTableById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM FOOD WHERE FOOD_ID = ?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            resultSet.last();
            return getProductFromResultSet(resultSet);
        }
        return null;
    }

    protected Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .name(resultSet.getString(2))
                .type(ProductType.valueOf(resultSet.getString(3)))
                .exotic(resultSet.getInt(4) == 1)
                .build();
    }

    protected int getLastIdFromFoodTable() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT MAX(food_id) FROM FOOD");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    protected void deleteProductFromFoodTableById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM FOOD WHERE food_id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
