package com.example.aaaBookstoreCA.service;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.aaaBookstoreCA.pattern.strategy.*;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Save a new book to the database
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // Return all books from the database
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Find books by title
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    // Find books by author
    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    // Find books by publisher
    public List<Book> searchByPublisher(String publisher) {
        return bookRepository.findByPublisherContainingIgnoreCase(publisher);
    }

    // Find books by category
    public List<Book> searchByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }
    
    // Sort books with STRATEGY pattern
    public List<Book> getSortedBooks(String sortBy, boolean ascending) {
        // Get all books from DB
        List<Book> books = bookRepository.findAll();

        // Decide which sorting strategy to use
        Sorter sorter;

        switch (sortBy.toLowerCase()) {
            case "title":
                sorter = new SortByTitle();
                break;
            case "price":
                sorter = new SortByPrice();
                break;
            case "author":
                sorter = new SortByAuthor();
                break;
            case "publisher":
                sorter = new SortByPublisher();
                break;
            case "category":
                sorter = new SortByCategory();
                break;
            case "stock":
                sorter = new SortByStock();
                break;
            default:
                // If no valid sort type is passed, return unsorted list
                return books;
        }

        // Use the selected strategy to sort and return the list
        return sorter.sort(books, ascending);
    }
}
