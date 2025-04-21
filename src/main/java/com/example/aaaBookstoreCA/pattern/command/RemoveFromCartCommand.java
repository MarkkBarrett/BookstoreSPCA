package com.example.aaaBookstoreCA.pattern.command;

import com.example.aaaBookstoreCA.service.CartService;

// This command removes a book from a user's cart
public class RemoveFromCartCommand implements Command {

    private CartService cartService;

    private Long userId;
    private Long bookId;
    private int quantity;

    // Store info for undo later
    public RemoveFromCartCommand(CartService cartService, Long userId, Long bookId, int quantity) {
        this.cartService = cartService;
        this.userId = userId;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    // Remove the book from the user's cart
    @Override
    public void execute() {
        cartService.removeFromCart(userId, bookId);
    }

    // Undo by re-adding it
    @Override
    public void undo() {
        cartService.addToCart(userId, bookId, quantity);
    }
}