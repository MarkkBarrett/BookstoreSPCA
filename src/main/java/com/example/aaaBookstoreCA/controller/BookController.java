package com.example.aaaBookstoreCA.controller;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.entity.User;
import com.example.aaaBookstoreCA.repository.OrderRepository;
import com.example.aaaBookstoreCA.repository.UserRepository;
import com.example.aaaBookstoreCA.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

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

	// Sort using STRATEGY pattern
	@GetMapping("/sort")
	public List<Book> getSortedBooks(@RequestParam String by, @RequestParam boolean ascending) {
		return bookService.getSortedBooks(by, ascending);
	}

	// Admin endpoint, Get all users with their orders
	@GetMapping("/admin/users-with-orders")
	public List<Map<String, Object>> getAllUsersWithOrders() {
		List<User> users = userRepository.findAll();
		List<Map<String, Object>> result = new ArrayList<>();

		for (User user : users) {
			Map<String, Object> userData = new HashMap<>();
			userData.put("user", user);
			userData.put("orders", orderRepository.findByUserId(user.getId()));
			result.add(userData);
		}

		return result;
	}
}