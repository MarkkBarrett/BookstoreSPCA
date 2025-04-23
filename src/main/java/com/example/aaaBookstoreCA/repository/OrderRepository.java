package com.example.aaaBookstoreCA.repository;

import com.example.aaaBookstoreCA.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders placed by a specific user
    List<Order> findByUserId(Long userId);
}