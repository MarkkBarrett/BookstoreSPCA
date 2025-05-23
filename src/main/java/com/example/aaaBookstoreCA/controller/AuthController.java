package com.example.aaaBookstoreCA.controller;

import com.example.aaaBookstoreCA.entity.User;
import com.example.aaaBookstoreCA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User loggedIn = userService.login(user.getEmail(), user.getPassword());
        if (loggedIn != null) {
            return ResponseEntity.ok(loggedIn); // Return full user object
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    
    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
