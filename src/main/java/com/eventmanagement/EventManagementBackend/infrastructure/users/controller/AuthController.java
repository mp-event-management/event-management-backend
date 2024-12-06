package com.eventmanagement.EventManagementBackend.infrastructure.users.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.Claims;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO.LogoutRequestDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.dto.LoginRequestDTO;
import com.eventmanagement.EventManagementBackend.usecase.auth.AuthenticationServiceUseCase;
import com.eventmanagement.EventManagementBackend.usecase.auth.LogoutUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthenticationServiceUseCase authenticationServiceUseCase;
    private final LogoutUsecase logoutUsecase;

    public AuthController(AuthenticationServiceUseCase authenticationServiceUseCase, LogoutUsecase logoutUsecase) {
        this.authenticationServiceUseCase = authenticationServiceUseCase;
        this.logoutUsecase = logoutUsecase;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginRequestDTO loginRequestDTO) {
        return ApiResponse.successfulResponse("Login successful", authenticationServiceUseCase.authenticate(loginRequestDTO));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated @RequestBody LogoutRequestDTO req) {
        var accessToken = Claims.getJwtTokenString();
        req.setAccessToken(accessToken);
        return ApiResponse.successfulResponse("Logout successful", logoutUsecase.logoutUser(req));
    }


}
