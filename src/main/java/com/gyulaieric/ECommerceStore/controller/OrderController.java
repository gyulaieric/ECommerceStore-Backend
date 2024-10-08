package com.gyulaieric.ECommerceStore.controller;

import com.gyulaieric.ECommerceStore.model.Order;
import com.gyulaieric.ECommerceStore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public List<Order> getOrdersByUserId(Authentication authentication) {
        return orderService.getOrdersByUserId(authentication);
    }

    @PostMapping("/")
    public Long addOrder(@Valid @RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(Authentication authentication, @PathVariable Long orderId) {
        return orderService.getOrderById(authentication, orderId);
    }
}
