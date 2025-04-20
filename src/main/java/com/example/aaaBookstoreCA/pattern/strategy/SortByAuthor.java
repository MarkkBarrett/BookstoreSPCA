package com.example.aaaBookstoreCA.pattern.strategy;

import com.example.aaaBookstoreCA.entity.Book;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortByAuthor implements Sorter {

    @Override
    public List<Book> sort(List<Book> books, boolean ascending) {
        // If ascending is true, sort A-Z
        Comparator<Book> comparator = Comparator.comparing(
                Book::getAuthor,
                String.CASE_INSENSITIVE_ORDER
        );
        // If false, sort Z-A
        if (!ascending) {
            comparator = comparator.reversed(); // flip to Z-A
        }

        // Apply comparator and return sorted list
        return books.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}