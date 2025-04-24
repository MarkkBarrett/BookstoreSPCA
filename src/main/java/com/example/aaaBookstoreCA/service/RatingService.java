package com.example.aaaBookstoreCA.service;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.entity.Rating;
import com.example.aaaBookstoreCA.entity.User;
import com.example.aaaBookstoreCA.pattern.observer.AverageRatingObserver;
import com.example.aaaBookstoreCA.repository.BookRepository;
import com.example.aaaBookstoreCA.repository.RatingRepository;
import com.example.aaaBookstoreCA.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Add or update rating by a user for a specific book
    public String rateBook(Long userId, Long bookId, int stars, String comment) {
        User user = userRepository.findById(userId).orElse(null);
        Book book = bookRepository.findById(bookId).orElse(null);

        if (user == null || book == null) {
            return "User or book not found.";
        }

        // Check if the user has already rated the book
        Rating existing = ratingRepository.findByUserAndBook(user, book);
        if (existing != null) {
            existing.setStars(stars);
            existing.setComment(comment);
            ratingRepository.save(existing);
        } else {
            Rating rating = new Rating(user, book, stars, comment);
            ratingRepository.save(rating);
        }

        // Refresh book's ratings list
        List<Rating> allRatings = ratingRepository.findByBook(book);
        book.setRatings(allRatings);

        // Trigger observer update
        new AverageRatingObserver(book).update();

        bookRepository.save(book);

        return "Rating submitted successfully.";
    }

    // Get all ratings for a book
    public List<Rating> getRatingsForBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        // If the book doesn't exist, return an empty list. otherwise return all its ratings
        return (book == null) ? List.of() : ratingRepository.findByBook(book);
    }
}
