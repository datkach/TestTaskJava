package com.teamvoy.testtaskjava;

import com.teamvoy.testtaskjava.model.Order;
import com.teamvoy.testtaskjava.model.Product;
import com.teamvoy.testtaskjava.repository.OrderRepository;
import com.teamvoy.testtaskjava.service.OrderService;
import com.teamvoy.testtaskjava.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EnableScheduling
public class OrderServiceBeanTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @SpyBean
    private ProductService productService;

    @Test
    void createOrderTest() {
        Order order = new Order();
        Product product = new Product();
        product.setId(1);
        product.setQuantity(10);
        order.setProducts(new HashSet<>(Collections.singletonList(product)));
        order.setQuantityOrder(5);

        when(orderRepository.save(order)).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);

        assertEquals(order, createdOrder);
        verify(productService, times(1)).decreaseProductQuantity(1, 5);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void getAllOrdersTest() {
        Order order1 = new Order();
        Order order2 = new Order();
        List<Order> orderList = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> retrievedOrders = orderService.getAllOrders();

        assertEquals(orderList, retrievedOrders);
    }

    @Test
    void getOrderByIdTest() {
        Order order = new Order();
        when(orderRepository.findById(1)).thenReturn(java.util.Optional.of(order));

        Order retrievedOrder = orderService.getOrderById(1);

        assertEquals(order, retrievedOrder);
    }

    @Test
    void getUnpaidOrdersTest() {
        Order order1 = new Order();
        Order order2 = new Order();
        order1.setPaid(false);
        order2.setPaid(true);
        List<Order> unpaidOrders = Collections.singletonList(order1);

        when(orderRepository.findByPaid(false)).thenReturn(unpaidOrders);

        List<Order> retrievedUnpaidOrders = orderService.getUnpaidOrders();

        assertEquals(unpaidOrders, retrievedUnpaidOrders);
    }

    @Test
    void markOrderAsPaidTest() {
        Order order = new Order();
        when(orderRepository.findById(1)).thenReturn(java.util.Optional.of(order));

        orderService.markOrderAsPaid(1);

        assertTrue(order.isPaid());
        verify(orderRepository, times(1)).save(order);
    }
}