package org.example.dto.auth;

public record TokenResponse(String accessToken, String refreshToken, String tokenType, long expiresInSeconds, UserResponse user) { }

