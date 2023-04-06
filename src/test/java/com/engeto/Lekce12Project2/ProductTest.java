package com.engeto.Lekce12Project2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    public void idValidationTest() throws Exception {
        ProductService test = new ProductService();
        int testResult = test.getProduct(17).getId();
        Assertions.assertEquals(17, testResult);
    }

    @Test
    public void deleteProductsNotForSale() throws Exception {
        ProductService test = new ProductService();
        String testResult = test.deleteProductsNotForSale();
        Assertions.assertEquals("Odstraněno záznamů: 2", testResult);
    }

    @Test
    public void deleteProductTest() throws Exception {
        ProductService test = new ProductService();
        String testResult = test.deleteProduct(100);
        Assertions.assertEquals("Odstraněno záznamů: 0", testResult);
    }

}