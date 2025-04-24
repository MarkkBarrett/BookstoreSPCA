package com.example.aaaBookstoreCA.controller;

import com.example.aaaBookstoreCA.entity.Rating;
import com.example.aaaBookstoreCA.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // Endpoint to submit a rating for a book
    @PostMapping("/submit")
    public String rateBook(@RequestParam Long userId,
                           @RequestParam Long bookId,
                           @RequestParam int stars,
                           @RequestParam String comment) {
        return ratingService.rateBook(userId, bookId, stars, comment);
    }

    // Endpoint to fetch all ratings for a book
    @GetMapping("/book/{bookId}")
    public List<Rating> getRatingsForBook(@PathVariable Long bookId) {
        return ratingService.getRatingsForBook(bookId);
    }
}

