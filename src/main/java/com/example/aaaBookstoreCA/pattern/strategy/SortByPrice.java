package com.example.aaaBookstoreCA.pattern.strategy;

import com.example.aaaBookstoreCA.entity.Book;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByPrice implements Sorter {

    @Override
    public List<Book> sort(List<Book> books, boolean ascending) {
        // If ascending is true, sort from lowest to highest
        Comparator<Book> comparator = Comparator.comparingDouble(Book::getPrice);

        // If false, sort from highest to lowest 
        if (!ascending) {
            comparator = comparator.reversed(); // flip to descending
        }

        // Apply comparator and return sorted list
        return books.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}