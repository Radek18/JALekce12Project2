package com.engeto.Lekce12Project2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
            this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() throws SQLException {
        return ProductService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") int id) throws Exception {
            return productService.getProduct(id);
    }

    @PostMapping("/product")
    public void saveProduct(@RequestBody Product product) throws SQLException {
        productService.saveProduct(product);
    }

    @PatchMapping("/product/{id}")
    public void updateProductPrice(@PathVariable("id") int id, @RequestParam(value = "price") BigDecimal price) throws SQLException {
        productService.updateProductPrice(id, price);
    }

    @DeleteMapping("/products")
    public void deleteProductsNotForSale() throws SQLException {
        productService.deleteProductsNotForSale();
    }

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable("id") int id) throws SQLException {
        productService.deleteProduct(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(e.getMessage());
    }

}