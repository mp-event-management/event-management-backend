package com.eventmanagement.EventManagementBackend.usecase.auth.impl;

import com.eventmanagement.EventManagementBackend.DTO.TokenPairResponseDTO;
import com.eventmanagement.EventManagementBackend.common.exceptions.DataNotFoundException;
import com.eventmanagement.EventManagementBackend.infrastructure.users.dto.LoginRequestDTO;
import com.eventmanagement.EventManagementBackend.usecase.auth.AuthenticationServiceUseCase;
import com.eventmanagement.EventManagementBackend.usecase.auth.TokenServiceUseCase;
import lombok.extern.java.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Log
@Service
public class AuthenticationServiceUseCaseImpl implements AuthenticationServiceUseCase {
    private final long ACCESS_TOKEN_EXPIRY = 900L;
    private final long REFRESH_TOKEN_EXPIRY = 86400L;

    private final AuthenticationManager authenticationManager;
    private final TokenServiceUseCase tokenService;

    public AuthenticationServiceUseCaseImpl (AuthenticationManager authenticationManager, TokenServiceUseCase tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    @Override
    public TokenPairResponseDTO authenticate(LoginRequestDTO request) {
        try {
            log.info("Logging with");
            log.info(request.getEmail());
            log.info(request.getPassword());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String accessToken = tokenService.generateToken(authentication, TokenServiceUseCase.TokenType.ACCESS);
            String refreshToken = tokenService.generateToken(authentication, TokenServiceUseCase.TokenType.REFRESH);
            return new TokenPairResponseDTO(accessToken, refreshToken, "Bearer");
        } catch (AuthenticationException e) {
            throw new DataNotFoundException("Wrong credentials");
        }
    }


    @Override
    public TokenPairResponseDTO refreshToken(String refreshToken) {
        String newAccessToken = tokenService.refreshAccessToken(refreshToken);
        return new TokenPairResponseDTO(newAccessToken, refreshToken, "Bearer");
    }
}
