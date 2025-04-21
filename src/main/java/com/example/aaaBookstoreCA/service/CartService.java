package com.example.aaaBookstoreCA.service;

import com.example.aaaBookstoreCA.entity.Book;
import com.example.aaaBookstoreCA.entity.CartItem;
import com.example.aaaBookstoreCA.entity.User;
import com.example.aaaBookstoreCA.repository.BookRepository;
import com.example.aaaBookstoreCA.repository.CartItemRepository;
import com.example.aaaBookstoreCA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	// This method adds an item to the user's cart
	public String addToCart(Long userId, Long bookId, int quantity) {
		// Optional to avoid NPE
		Optional<User> userOpt = userRepository.findById(userId);
		Optional<Book> bookOpt = bookRepository.findById(bookId);

		if (userOpt.isEmpty() || bookOpt.isEmpty()) {
			return "User or book not found.";
		}

		Book book = bookOpt.get();
		if (quantity > book.getStock()) {
			return "Not enough stock available.";
		}

	    // Check if the user already has this book in their cart
	    CartItem existingItem = cartItemRepository.findByUserIdAndBookId(userId, bookId);

	    if (existingItem != null) {
	        // Merge quantities
	        existingItem.setQuantity(existingItem.getQuantity() + quantity);
	        cartItemRepository.save(existingItem);
	    } else {
	        // Create new item
	        CartItem item = new CartItem(userOpt.get(), book, quantity);
	        cartItemRepository.save(item);
	    }

	    return "Item added to cart.";
	}

	// This method removes a specific book from the user's cart
	public String removeFromCart(Long userId, Long bookId) {
		CartItem item = cartItemRepository.findByUserIdAndBookId(userId, bookId);

		if (item == null) {
			return "Item not found in cart.";
		}

		cartItemRepository.delete(item);
		return "Item removed from cart.";
	}
}