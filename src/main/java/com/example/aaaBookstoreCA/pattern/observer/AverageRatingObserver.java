package com.example.aaaBookstoreCA.pattern.observer;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.entity.Rating;

import java.util.List;

// Concrete Observer that reacts to changes in Book rating
public class AverageRatingObserver implements Observer {

    private Book book;

    public AverageRatingObserver(Book book) {
        this.book = book;
        book.attach(this); // Register itself to be notified
    }

    @Override
    public void update() {
        List<Rating> ratings = book.getRatings();

        if (ratings == null || ratings.isEmpty()) {
            book.setAverageRating(null);
            return;
        }

        double sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getStars();
        }

        double average = sum / ratings.size();
        book.setAverageRating(Math.round(average * 100.0) / 100.0); // Round to 2 decimals
    }
}