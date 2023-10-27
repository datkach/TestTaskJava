package com.teamvoy.testtaskjava;

import com.teamvoy.testtaskjava.model.Product;
import com.teamvoy.testtaskjava.repository.ProductRepository;
import com.teamvoy.testtaskjava.service.ProductServiceBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceBeanTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceBean productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceBean(productRepository);
    }

    @Test
    void createProduct() {
        Product testProduct = new Product();
        testProduct.setId(1);

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(testProduct);

        Product productToSave = new Product();

        Product createdProduct = productService.createProduct(productToSave);

        assertThat(createdProduct).isEqualTo(testProduct);
    }

    @Test
    void getAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> testProducts = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(testProducts);

        List<Product> allProducts = productService.getAllProducts();

        assertThat(allProducts).isEqualTo(testProducts);
    }

    @Test
    void getProductById() {
        Product testProduct = new Product();
        testProduct.setId(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(testProduct));

        Product product = productService.getProductById(1);

        assertThat(product).isEqualTo(testProduct);
    }


    @Test
    void findProductsByNameTest() {
        String productName = "Test Product";
        List<Product> productList = new ArrayList<>();
        when(productRepository.findByName(productName)).thenReturn(productList);

        List<Product> foundProducts = productService.findProductsByName(productName);

        assertSame(productList, foundProducts);
        verify(productRepository, times(1)).findByName(productName);
    }


}