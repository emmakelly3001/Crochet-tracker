package com.example.crochet_tracker.service;

import com.example.crochet_tracker.model.User; //user class
import com.example.crochet_tracker.repository.UserRepository; //userRepository class
import jakarta.persistence.EntityManager; //interact with JPA persistence
import org.springframework.beans.factory.annotation.Autowired; //dependancy injection
import org.springframework.security.core.userdetails.UserDetails; //represents authentication and authority info
import org.springframework.security.core.userdetails.UsernameNotFoundException; //uname not found exception
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;//represents a granted authority
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;//marks a class as a service provider
import org.springframework.transaction.annotation.Transactional;//defines a method/class  that should be wrapped in a database transacton


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    //connection to the database
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    //constructor injection for UserRepository
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    //flushing the database manually
    @Autowired
    private EntityManager entityManager;

    //creating a new user
    //transactional means ensuring DB consistency
    @Transactional
    public void createUser(User user) {
        // Validate if username or email already exists
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }

        // Save the user
        userRepository.save(user);
    }

    //find user bt username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //update existing users information
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        //find user by userID
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            //get the existing user
            User existingUser = existingUserOptional.get();
            //update fields of the existing user with new values
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setRole(updatedUser.getRole());
            //save the role
            return userRepository.save(existingUser);
        }
        //if the user was not found,
        return null;
    }

    //method to delete a user from the database
    @Transactional
    public boolean deleteUser(Long id) {
        //find user by ID
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            //delete user if found
            userRepository.delete(userOptional.get());
            //if user was deleted return true
            return true;
        }
        //if user was not found return false
        return false;
    }

    // Method from UserDetailsService to load a user by their username for authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // fetch user by username
        User user = userRepository.findByUsername(username);
        //throw exception if user not found
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        // Return a UserDetails object, which Spring Security uses for authentication
        // Map user roles to SimpleGrantedAuthority for security purposes
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase())))
                .build();
    }
}
