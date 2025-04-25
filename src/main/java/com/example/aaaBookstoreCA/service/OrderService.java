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
import com.example.aaaBookstoreCA.pattern.decorator.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Order order = new Order(); // Create order first

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            Book book = item.getBook();

            if (item.getQuantity() > book.getStock()) {
                return "Not enough stock for: " + book.getTitle();
            }

            // Update stock
            book.setStock(book.getStock() - item.getQuantity());
            bookRepository.save(book);

            // Update total
            total += book.getPrice() * item.getQuantity();
        }

        // Call getDiscountInfo
        Map<String, Object> discountInfo = getDiscountInfo(userId);
        DiscountCalculator discount = (DiscountCalculator) discountInfo.get("discount");
        double discountedTotal = discount.calculateDiscount(total, 
            cartItems.stream().mapToInt(CartItem::getQuantity).sum(), 
            orderRepository.findByUserId(userId).size());

        double discountMultiplier = discountedTotal / total;

        // Create order items with adjusted priceAtPurchase
        for (CartItem item : cartItems) {
            Book book = item.getBook();
            double discountedUnitPrice = book.getPrice() * discountMultiplier;
            OrderItem orderItem = new OrderItem(book, item.getQuantity(), discountedUnitPrice, order);
            orderItems.add(orderItem);
        }

        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalPrice(discountedTotal);
        order.setOrderDate(new Date());

        orderRepository.save(order);

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return "Checkout successful. Order ID: " + order.getId();
    }
    
    // get appropriate discount 
    public Map<String, Object> getDiscountInfo(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        List<Order> previousOrders = orderRepository.findByUserId(userId);

        double total = cartItems.stream()
            .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
            .sum();

        int totalBooks = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
        int totalOrders = previousOrders.size();

        // Apply correct decorator based on condition
        DiscountCalculator discount = new BaseDiscount();

        if (totalBooks >= 10) {
            discount = new BulkOrderDiscount(discount);
        } else if (totalOrders >= 5) {
            discount = new Loyalty5Discount(discount);
        } else if (totalOrders >= 3) {
            discount = new Loyalty3Discount(discount);
        } else if (totalOrders >= 1) {
            discount = new Loyalty1Discount(discount);
        }

        double discountedTotal = discount.calculateDiscount(total, totalBooks, totalOrders);
        String message = discount.getMessage();

        Map<String, Object> result = new HashMap<>();
        result.put("discountedTotal", discountedTotal);
        result.put("message", message);
        result.put("discount", discount);
        return result;
    }
}
