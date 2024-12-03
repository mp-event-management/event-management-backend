package com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    // Fields from OAuth2 (pre-filled)
    private String name;
    private String email;
    // Fields for manual input
    private String password;
    private String roleName;
}