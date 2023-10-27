package com.teamvoy.testtaskjava.controller;

import com.teamvoy.testtaskjava.model.Product;
import com.teamvoy.testtaskjava.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/manager/products")
@AllArgsConstructor
public class ManagerProductController {
    private final ProductService productService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return product;
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findProductsByName(@RequestParam String name) {
        return productService.findProductsByName(name);
    }

    @PostMapping("/decreaseQuantity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void decreaseProductQuantity(@RequestParam Integer productId, @RequestParam int quantityToReduce) {
        productService.decreaseProductQuantity(productId, quantityToReduce);
    }
}
