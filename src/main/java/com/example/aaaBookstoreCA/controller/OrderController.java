package com.example.aaaBookstoreCA.controller;

import com.example.aaaBookstoreCA.entity.Order;
import com.example.aaaBookstoreCA.repository.OrderRepository;
import com.example.aaaBookstoreCA.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    // Checkout endpoint handles order creation, stock update, and cart clear
    @PostMapping("/checkout")
    public String checkout(@RequestParam Long userId) {
        return orderService.checkout(userId);
    }

    // Get all orders for a specific user for customer order history
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Admin endpoint, Get all orders
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
