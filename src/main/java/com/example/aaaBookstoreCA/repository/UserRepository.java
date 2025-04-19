package com.example.aaaBookstoreCA.repository;

import com.example.aaaBookstoreCA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}