package com.example.aaaBookstoreCA.controller;

import com.example.aaaBookstoreCA.pattern.command.*;
import com.example.aaaBookstoreCA.service.CartService;
import com.example.aaaBookstoreCA.entity.CartItem;
import com.example.aaaBookstoreCA.repository.CartItemRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartItemRepository cartItemRepository;


	// Instantiate CartInvoker
	private CartInvoker invoker = new CartInvoker();

	// Add to cart with COMMAND pattern
	@PostMapping("/add")
	public String addToCart(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam int quantity) {
		AddToCartCommand addCommand = new AddToCartCommand(cartService, userId, bookId, quantity);
		invoker.executeCommand(addCommand);
		return addCommand.getResultMessage();
	}

	// Remove from cart with COMMAND pattern
	@PostMapping("/remove")
	public String removeFromCart(@RequestParam Long userId, @RequestParam Long bookId, @RequestParam int quantity) {
		RemoveFromCartCommand command = new RemoveFromCartCommand(cartService, userId, bookId, quantity);
		invoker.executeCommand(command);
		return command.getResultMessage();
	}

	// Undo last action with COMMAND pattern
	@PostMapping("/undo")
	public String undoLastAction() {
		invoker.undoLastCommand();
		return "Last command undone.";
	}
	
	// Get user's cart
	@GetMapping("/user/{userId}")
	public List<CartItem> getUserCart(@PathVariable Long userId) {
	    return cartItemRepository.findByUserId(userId);
	}
}
