package com.eventmanagement.EventManagementBackend.usecase.auth;

import com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO.LogoutRequestDTO;

public interface LogoutUsecase {
    Boolean logoutUser(LogoutRequestDTO req);
}
