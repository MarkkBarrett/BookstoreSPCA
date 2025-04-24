package com.example.aaaBookstoreCA.repository;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.entity.Rating;
import com.example.aaaBookstoreCA.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByBook(Book book);
    Rating findByUserAndBook(User user, Book book);
}
