package com.eventmanagement.EventManagementBackend.infrastructure.users.controller;

import com.eventmanagement.EventManagementBackend.common.response.ApiResponse;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO.UserRegistrationDTO;
import com.eventmanagement.EventManagementBackend.usecase.auth.CreateUserUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersPublicController {
    private final CreateUserUsecase createUserUsecase;

    public UsersPublicController(CreateUserUsecase createUserUsecase) {
        this.createUserUsecase = createUserUsecase;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDTO req) {
        var result = createUserUsecase.createUser(req);
        return ApiResponse.successfulResponse("Create new user success", result);
    }

}