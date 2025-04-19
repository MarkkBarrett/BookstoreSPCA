package com.example.aaaBookstoreCA.controller;

import com.example.aaaBookstoreCA.entity.User;
import com.example.aaaBookstoreCA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestBody User user) {
        User loggedIn = userService.login(user.getEmail(), user.getPassword());
        return (loggedIn != null) ? "Login successful" : "Invalid credentials";
    }
}
