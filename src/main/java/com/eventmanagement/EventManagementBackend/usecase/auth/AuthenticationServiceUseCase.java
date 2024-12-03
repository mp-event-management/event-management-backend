package com.eventmanagement.EventManagementBackend.usecase.auth;

import com.eventmanagement.EventManagementBackend.DTO.TokenPairResponseDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.dto.LoginRequestDTO;

public interface AuthenticationServiceUseCase {
    TokenPairResponseDTO authenticate(LoginRequestDTO request);
    TokenPairResponseDTO refreshToken(String refreshToken);
}
