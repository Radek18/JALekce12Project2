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

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable("id") int id) throws Exception {
        return productService.getProduct(id);
    }

    @PostMapping("/products")
    public Product saveProduct(@RequestBody Product product) throws Exception {
        int generatedId = productService.saveProduct(product);
        product.setId(generatedId);
        return productService.getProduct(product.getId());
    }

    @PatchMapping("/products/{id}")
    public Product updateProductPrice(@PathVariable("id") int id, @RequestParam(value = "price") BigDecimal price) throws Exception {
        productService.updateProductPrice(id, price);
        return productService.getProduct(id);
    }

    @DeleteMapping("/products")
    public String deleteProductsNotForSale() throws SQLException {
        return productService.deleteProductsNotForSale();
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") int id) throws Exception {
        return productService.deleteProduct(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse(e.getMessage());
    }

}