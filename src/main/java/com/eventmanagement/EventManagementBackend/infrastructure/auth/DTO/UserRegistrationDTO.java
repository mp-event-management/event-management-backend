package com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO;

import com.eventmanagement.EventManagementBackend.entity.Role;
import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.repository.RoleRepository;
import com.eventmanagement.EventManagementBackend.infrastructure.users.repository.UsersAccountRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class UserRegistrationDTO {
    // Fields from OAuth2 (pre-filled)
    private String name;
    private String email;
    // Fields for manual input
    private String password;
    private String role;
    private String referral_code;
    private String roleName;

}