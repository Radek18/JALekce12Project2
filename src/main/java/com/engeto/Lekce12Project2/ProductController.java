package com.engeto.Lekce12Project2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ProductController {

    ProductService productService;

    public ProductController() throws SQLException {
        productService = new ProductService();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() throws SQLException {
        return ProductService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") int id) throws SQLException {
        return productService.getProduct(id);
    }

    @PostMapping("/product")
    public Product saveProduct(@RequestBody Product product) throws SQLException {
        int generatedId = productService.saveProduct(product);
        product.setId(generatedId);
        return product;
    }

    @PatchMapping("/product/{id}")
    public Product updateProductPrice(@PathVariable("id") int id, @RequestParam(value = "price") BigDecimal price) throws SQLException {
        productService.updateProductPrice(id, price);
        return productService.getProduct(id);
    }

    @DeleteMapping("/products")
    public void deleteProductsNotForSale() throws SQLException {
        productService.deleteProductsNotForSale();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(e.getMessage());
    }

}