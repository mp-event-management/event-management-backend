package com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogoutRequestDTO {
    @NotNull
    private String refreshToken;
    @NotNull
    private String accessToken;
}