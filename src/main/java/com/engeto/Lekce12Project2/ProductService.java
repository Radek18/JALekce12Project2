package com.engeto.Lekce12Project2;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    static Connection connection;

    public ProductService () throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eshop", "" + Settings.user() + "", "" + Settings.password() + "");

    }

    public static List<Product> getAllProducts() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM eshop.product");
        List<Product> resultList = new ArrayList<>();
        while (resultSet.next()) {
            Product product = extractProductData(resultSet);
            resultList.add(product);
        }
        return resultList;
    }

    public Product getProduct(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM eshop.product WHERE id = " + id);
        if (resultSet.next()) {
        return extractProductData(resultSet);
        }
        return null;
    }

    public int saveProduct(Product product) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
            "INSERT INTO eshop.product(partNo, name, description, isForSale, price) VALUES(" +
            product.getPartNo() + ", '" + product.getName() + "', '" + product.getDescription() + "', " + product.isForSale() + ", " + product.getPrice() + ")",
            Statement.RETURN_GENERATED_KEYS
        );
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    public void updateProductPrice(int id, BigDecimal price) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE eshop.product SET price = " + price + " WHERE id = " + id);
    }

    public void deleteProductsNotForSale() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM eshop.product WHERE isForSale = 0");
    }

    public static Product extractProductData(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getInt("partNo"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBoolean("isForSale"),
                resultSet.getBigDecimal("price")
        );
    }

}