package com.teamvoy.testtaskjava.controller;

import com.teamvoy.testtaskjava.model.Order;
import com.teamvoy.testtaskjava.model.Product;
import com.teamvoy.testtaskjava.service.OrderService;
import com.teamvoy.testtaskjava.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/client/products")
public class ClientProductController {
    private final ProductService productService;
    private final OrderService orderService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getClientProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public String createClientOrder(@RequestBody Order order) {

        order = orderService.createOrder(order);
        return "Заказ був створений ID заказа: " + order.getId();
    }
    @PostMapping("/pay/{orderId}")
    public void payClientOrder(@PathVariable Integer orderId) {
        orderService.markOrderAsPaid(orderId);
    }

}

