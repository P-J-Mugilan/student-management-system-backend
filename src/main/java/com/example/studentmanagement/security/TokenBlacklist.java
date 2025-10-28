package com.example.studentmanagement.security;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenBlacklist {

    // Simple list to store blacklisted tokens
    private final List<String> blacklistedTokens = new ArrayList<>();

    // Add token to blacklist
    public void blacklistToken(String token) {
        if (!blacklistedTokens.contains(token)) {
            blacklistedTokens.add(token);
            System.out.println("✅ Token added to blacklist");
        }
    }

    // Check if token is blacklisted
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    // Clean up the list periodically (optional)
    @Scheduled(fixedRate = 3600000) // Run every hour
    public void cleanupTokens() {
        // For simplicity, we'll just clear the list if it gets too big
        if (blacklistedTokens.size() > 1000) {
            blacklistedTokens.clear();
            System.out.println("🔄 Token blacklist cleared");
        }
    }
}