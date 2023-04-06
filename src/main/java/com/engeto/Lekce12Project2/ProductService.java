package com.engeto.Lekce12Project2;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    private static Connection connection;

    public ProductService () throws SQLException {

        connection = DriverManager.getConnection(Settings.url(), Settings.user(), Settings.password());

    }

    public static List<Product> getAllProducts() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
        List<Product> resultList = new ArrayList<>();
        while (resultSet.next()) {
            Product product = extractProductData(resultSet);
            resultList.add(product);
            Collections.sort(resultList);
        }
        return resultList;
    }

    public Product getProduct(int id) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE id = " + id);
        if (resultSet.next()) {
            return extractProductData(resultSet);
        }
        throw new Exception("Zadané ID v databázi nenalezeno!");
    }

    public int saveProduct(Product product) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(
            "INSERT INTO product(partNo, name, description, isForSale, price) VALUES(" +
            product.getPartNo() + ", '" + product.getName() + "', '" + product.getDescription() + "', " + product.isForSale() + ", " + product.getPrice() + ")",
            Statement.RETURN_GENERATED_KEYS
        );
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }

    public void updateProductPrice(int id, BigDecimal price) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE product SET price = " + price + " WHERE id = " + id);
    }

    public String deleteProductsNotForSale() throws SQLException {
        Statement statement = connection.createStatement();
        int resultReturn =  statement.executeUpdate("DELETE FROM product WHERE isForSale = 0");
        return ("Odstraněno záznamů: " + resultReturn);
    }

    public String deleteProduct(int id) throws SQLException {
        Statement statement = connection.createStatement();
        int resultReturn = statement.executeUpdate("DELETE FROM product WHERE id = " + id);
        return ("Odstraněno záznamů: " + resultReturn);
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