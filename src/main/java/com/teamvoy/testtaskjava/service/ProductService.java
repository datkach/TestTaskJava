package com.teamvoy.testtaskjava.service;

import com.teamvoy.testtaskjava.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product createProduct(Product product);
    Product getProductById(Integer id);
    List<Product> findProductsByName(String name);
    void decreaseProductQuantity(Integer productId, int quantityToReduce);
}
