package com.example.aaaBookstoreCA.controller;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Add a new book 
    @PostMapping("/add")
    public Book addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    // Get all books 
    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Search by title
    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        return bookService.searchByTitle(title);
    }

    // Search by author
    @GetMapping("/search/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        return bookService.searchByAuthor(author);
    }

    // Search by publisher
    @GetMapping("/search/publisher")
    public List<Book> searchByPublisher(@RequestParam String publisher) {
        return bookService.searchByPublisher(publisher);
    }

    // Search by category
    @GetMapping("/search/category")
    public List<Book> searchByCategory(@RequestParam String category) {
        return bookService.searchByCategory(category);
    }
}
