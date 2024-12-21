package com.example.crochet_tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/dbtest")
public class DatabaseTestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/check")
    public ResponseEntity<String> testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return ResponseEntity.ok("Database connection is successful!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database connection failed: " + e.getMessage());
        }
    }

    @PostMapping("/insert-sample")
    public ResponseEntity<String> insertSampleUser() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {

            stmt.setString(1, "testuser");
            stmt.setString(2, "testpassword");
            stmt.executeUpdate();

            return ResponseEntity.ok("Sample user inserted successfully!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to insert sample user: " + e.getMessage());
        }
    }
}

