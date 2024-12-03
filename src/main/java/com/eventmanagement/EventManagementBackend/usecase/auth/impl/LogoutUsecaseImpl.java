package com.eventmanagement.EventManagementBackend.usecase.auth.impl;

import com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO.LogoutRequestDTO;
import com.eventmanagement.EventManagementBackend.usecase.auth.LogoutUsecase;
import com.eventmanagement.EventManagementBackend.usecase.auth.TokenBlacklistUsecase;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LogoutUsecaseImpl implements LogoutUsecase {
    private final JwtDecoder jwtDecoder;
    private final TokenBlacklistUsecase TokenBlacklistUsecase;


    public LogoutUsecaseImpl(JwtDecoder jwtDecoder, com.eventmanagement.EventManagementBackend.usecase.auth.TokenBlacklistUsecase tokenBlacklistUsecase) {
        this.jwtDecoder = jwtDecoder;
        TokenBlacklistUsecase = tokenBlacklistUsecase;
    }

    @Override
    public Boolean logoutUser(LogoutRequestDTO req) {
        Jwt accessToken = jwtDecoder.decode(req.getAccessToken());
        Jwt refreshToken = jwtDecoder.decode(req.getRefreshToken());

        TokenBlacklistUsecase.blacklistToken(accessToken.getTokenValue(), Objects.requireNonNull(accessToken.getExpiresAt()).toString());
        TokenBlacklistUsecase.blacklistToken(refreshToken.getTokenValue(), Objects.requireNonNull(refreshToken.getExpiresAt()).toString());
        return Boolean.TRUE;
    }
}

