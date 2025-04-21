package com.example.aaaBookstoreCA.repository;

import com.example.aaaBookstoreCA.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // find a specific item in a user's cart
    CartItem findByUserIdAndBookId(Long userId, Long bookId);
}