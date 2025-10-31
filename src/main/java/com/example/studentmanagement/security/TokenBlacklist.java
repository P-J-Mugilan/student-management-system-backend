/**
 * Token Blacklist Manager
 *
 * Manages blacklisted JWT tokens to prevent their reuse after logout.
 * Provides simple in-memory token invalidation with periodic cleanup.
 */
package com.example.studentmanagement.security;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlacklist {

    private final Set<String> blacklistedTokens = new HashSet<>();

    /**
     * Add token to blacklist to prevent reuse
     */
    public void blacklistToken(String token) {
        if (!blacklistedTokens.contains(token)) {
            blacklistedTokens.add(token);
        }
    }

    /**
     * Check if token is blacklisted
     */
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    /**
     * Clean up blacklist periodically to prevent memory issues
     * Runs every hour and clears if list exceeds 1000 tokens
     */
    @Scheduled(fixedRate = 3600000)
    public void cleanupTokens() {
        if (blacklistedTokens.size() > 1000) {
            blacklistedTokens.clear();
        }
    }
}