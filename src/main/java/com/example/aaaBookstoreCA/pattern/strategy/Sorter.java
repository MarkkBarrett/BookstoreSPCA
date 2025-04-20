package com.example.aaaBookstoreCA.pattern.strategy;

import com.example.aaaBookstoreCA.entity.Book;
import java.util.List;

public interface Sorter {
    // Every sorting strategy will implement this. Pass Book list and order boolean 
    List<Book> sort(List<Book> books, boolean ascending);
}
