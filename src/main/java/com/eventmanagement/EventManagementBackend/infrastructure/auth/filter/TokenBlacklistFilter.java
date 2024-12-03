package com.eventmanagement.EventManagementBackend.infrastructure.auth.filter;

import com.eventmanagement.EventManagementBackend.usecase.auth.TokenBlacklistUsecase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenBlacklistFilter extends OncePerRequestFilter {
    private final TokenBlacklistUsecase tokenBlacklistUsecase;

    public TokenBlacklistFilter(TokenBlacklistUsecase tokenBlacklistUsecase) {
        this.tokenBlacklistUsecase = tokenBlacklistUsecase;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);
        if (token != null && tokenBlacklistUsecase.isTokenBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
            if (cookie.getName().equals("SID")) {
                return cookie.getValue();
            }
        }
        }
        // Get from headers instead of cookies
        var header = request.getHeader("Authorization");
        if (header != null) {
            return header.replace("Bearer ", "");
        }

        return null;
    }
}

