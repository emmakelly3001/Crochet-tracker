package com.example.crochet_tracker.controller;

import com.example.crochet_tracker.model.DTO.RegisterUserDTO;
import com.example.crochet_tracker.model.User;//importing user class
import com.example.crochet_tracker.service.UserService;//importing userservice class
//trigger validation with incoming user data
import jakarta.validation.Valid;
//Used to set HTTP response status codes
import org.springframework.http.HttpStatus;
//Wrapper forHTTP responses that include status codes and body content
import org.springframework.http.ResponseEntity;
//password encryption
import org.springframework.security.crypto.password.PasswordEncoder;
//Define RESTful endpoints
import org.springframework.web.bind.annotation.*;

//define the controller as a REST controller
@RestController
//defines the base URL path for all methods in thi controller
@RequestMapping("/api/auth")
public class UserController {

    //Declaring dependencies
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    //Constructor based dependency injection
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    //Handle HTTP POST requests to /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserDTO userDTO) {
        try {
            // Map DTO to User
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode password
            user.setEmail(userDTO.getEmail());
            user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "USER");

            // Save user
            userService.createUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during registration.");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<String> getLoggedInUser(@RequestBody User user) {
        return ResponseEntity.ok("Logged in as: " + user.getUsername());
    }
}
