package com.example.aaaBookstoreCA.service;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
