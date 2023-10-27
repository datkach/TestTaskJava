package com.teamvoy.testtaskjava.service;

import com.teamvoy.testtaskjava.model.Product;
import com.teamvoy.testtaskjava.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceBean implements ProductService{
    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findProductsByName(String name) {
        return productRepository.findByName(name);
    }

    public void decreaseProductQuantity(Integer productId, int quantityToReduce) {
        Product product = getProductById(productId);
        if (product != null) {
            int currentQuantity = product.getQuantity();
            if (currentQuantity >= quantityToReduce) {
                product.setQuantity(currentQuantity - quantityToReduce);
                productRepository.save(product);
            }
        }
    }
}

