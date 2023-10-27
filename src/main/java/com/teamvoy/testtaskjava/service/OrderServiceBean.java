package com.teamvoy.testtaskjava.service;

import com.teamvoy.testtaskjava.exception.ResourceNotFoundException;
import com.teamvoy.testtaskjava.model.Order;
import com.teamvoy.testtaskjava.model.Product;
import com.teamvoy.testtaskjava.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
@EnableScheduling
public class OrderServiceBean implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public Order createOrder(Order order) {
       Set<Product> productSet = order.getProducts();
            for (Product product: productSet) {
                productService.decreaseProductQuantity(product.getId(),order.getQuantityOrder());
            }
                return orderRepository.save(order);
        }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getUnpaidOrders() {
        return orderRepository.findByPaid(false);
    }

    public void markOrderAsPaid(Integer orderId) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setPaid(true);
            orderRepository.save(order);
        }
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void cleanupUnpaidOrders() {
        List<Order> unpaidOrders = getUnpaidOrders();
        for(Order order : unpaidOrders){
            Set<Product> productSet = order.getProducts();
            for(Product product: productSet){
                if (product.getQuantity() > order.getQuantityOrder())
                    orderRepository.delete(order);
            }
        }
        orderRepository.deleteAll(unpaidOrders);
    }
}
