package com.example.crochet_tracker.controller;

import com.example.crochet_tracker.model.User;
import com.example.crochet_tracker.service.UserService; //class import
import org.springframework.beans.factory.annotation.Autowired; //inject dependencies
import org.springframework.security.core.annotation.AuthenticationPrincipal;//access already authenticated users
import org.springframework.security.core.userdetails.UserDetails;//contains the current users details
import org.springframework.security.crypto.password.PasswordEncoder;//encode passwords securely
import org.springframework.stereotype.Controller;//mark the class as a Spring MVC controller
import org.springframework.ui.Model; //passing the data into view
import org.springframework.web.bind.annotation.GetMapping;//handling GET requests
import org.springframework.web.bind.annotation.PostMapping;//handling POST requests
import org.springframework.web.bind.annotation.RequestMapping;//Handling base URL mapping
import org.springframework.web.bind.annotation.RequestParam;// accessing request parameters

//marks this as a spring MVC controller that handles requests at /api/auth
@Controller
@RequestMapping("/api/auth")
public class AuthenticationController {

    //inject userservice to handle user operations
    private final UserService userService;

    //constructor injection
    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    // Show login page with error message if needed
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Incorrect username or password.");
        }
        return "login";  // This renders the login page (login.html)
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";  // Render home page after successful login
    }

    // Handle login submission
    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        // Custom login logic here
        if (userService.findByUsername(username) == null) {
            model.addAttribute("error", "Invalid username or password");
            return "login";  // Return to the login page if authentication fails
        }
        // Spring Security handles the authentication automatically
        return "redirect:/html/home.html";  // Redirect to the home page if authentication is successful
    }

    // Display the registration page
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  // Return registration form (register.html)
    }

    // Handle user registration
    @PostMapping("/register")
    public String registerUser(String username, String email, String password, String confirmPassword, Model model) {
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";  // Return to registration page if passwords don't match
        }

        // Check if username already exists
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Username is already taken");
            return "register";  // Return to registration page if username is taken
        }

        // Check if email already exists
        if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "Email is already registered");
            return "register";  // Return to registration page if email is already taken
        }

        // Create new user object
        User newUser = new User(username, email, password, "USER");

        // Encrypt the password
        newUser.setPassword(passwordEncoder.encode(password));

        // Save user to the database
        userService.createUser(newUser);

        return "redirect:/api/auth/login";  // Redirect to login page after successful registration
    }

    //handling password encryption if needed
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Check if the user is authenticated
    @GetMapping("/status")
    public String checkAuthentication(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
            return "home";  // Redirect to the home page if authenticated
        }
        return "login";  // Redirect to login page if not authenticated
    }
}
