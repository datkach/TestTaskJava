package com.teamvoy.testtaskjava.service;

import com.teamvoy.testtaskjava.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Integer id);
    List<Order> getUnpaidOrders();
    void markOrderAsPaid(Integer orderId);
    void cleanupUnpaidOrders();
}
