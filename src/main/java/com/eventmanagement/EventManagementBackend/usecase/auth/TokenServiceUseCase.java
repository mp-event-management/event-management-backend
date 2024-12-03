package com.eventmanagement.EventManagementBackend.usecase.auth;

import org.springframework.security.core.Authentication;

public interface TokenServiceUseCase {
        enum TokenType {
            ACCESS, REFRESH
        }

        String generateToken(Authentication authentication, TokenType tokenType);
        String refreshAccessToken(String refreshToken);
    }
