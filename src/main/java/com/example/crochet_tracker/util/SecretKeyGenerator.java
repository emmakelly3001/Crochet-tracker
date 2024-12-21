package com.example.crochet_tracker.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static void main(String[] args) {
        // Generate a 256-bit (32 bytes) or 512-bit (64 bytes) key based on your needs
        byte[] secretKey = new byte[64]; // Use 32 for HS256 or 64 for HS512
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(secretKey);

        // Encode the key in Base64
        String base64EncodedKey = Base64.getEncoder().encodeToString(secretKey);

        // Print the key and verification
        System.out.println("Generated Secret Key (Base64 encoded): " + base64EncodedKey);
        System.out.println("Key Length: " + secretKey.length * 8 + " bits");
    }
}
