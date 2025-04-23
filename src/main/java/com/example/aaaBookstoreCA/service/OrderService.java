package com.example.aaaBookstoreCA.service;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.entity.CartItem;
import com.example.aaaBookstoreCA.entity.Order;
import com.example.aaaBookstoreCA.entity.OrderItem;
import com.example.aaaBookstoreCA.entity.User;
import com.example.aaaBookstoreCA.repository.BookRepository;
import com.example.aaaBookstoreCA.repository.CartItemRepository;
import com.example.aaaBookstoreCA.repository.OrderRepository;
import com.example.aaaBookstoreCA.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    // Handles checkout process for a user
    public String checkout(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "User not found.";
        }

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            return "Cart is empty.";
        }

        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            Book book = item.getBook();

            if (item.getQuantity() > book.getStock()) {
                return "Not enough stock for: " + book.getTitle();
            }

            // Update stock
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);

            // Add to order item list
            OrderItem orderItem = new OrderItem(book, item.getQuantity(), book.getPrice());
            orderItems.add(orderItem);

            // Update total
            total += book.getPrice() * item.getQuantity();
        }

        // Create and save order
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalPrice(total);
        order.setOrderDate(new Date());

        orderRepository.save(order);

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return "Checkout successful. Order ID: " + order.getId();
    }
}
