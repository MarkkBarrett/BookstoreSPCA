package com.example.aaaBookstoreCA.repository;

import com.example.aaaBookstoreCA.entity.CartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // find a specific item in a user's cart
    CartItem findByUserIdAndBookId(Long userId, Long bookId);
    
    // Find users cart by userId
    List<CartItem> findByUserId(Long userId);
}