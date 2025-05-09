package com.example.aaaBookstoreCA.pattern.command;

import com.example.aaaBookstoreCA.service.CartService;

// This command adds a book to a user's cart
public class AddToCartCommand implements Command {

	private CartService cartService;

	private Long userId;
	private Long bookId;
	private int quantity;
	private String resultMessage = "";

	// Store values to allow undo later
	public AddToCartCommand(CartService cartService, Long userId, Long bookId, int quantity) {
		this.cartService = cartService;
		this.userId = userId;
		this.bookId = bookId;
		this.quantity = quantity;
	}

	// Add the book to the user's cart
	@Override
	public void execute() {
		resultMessage = cartService.addToCart(userId, bookId, quantity);
	}

	// Response if adding to cart fails
	public String getResultMessage() {
		return resultMessage;
	}

	// Remove it again if undo is triggered
	@Override
	public void undo() {
		cartService.removeFromCart(userId, bookId, quantity);
	}
}