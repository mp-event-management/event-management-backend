package com.eventmanagement.EventManagementBackend.usecase.auth;

import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import com.eventmanagement.EventManagementBackend.infrastructure.auth.DTO.UserRegistrationDTO;
import com.eventmanagement.EventManagementBackend.infrastructure.users.dto.UserDetailResponseDTO;

public interface CreateUserUsecase {
    UserDetailResponseDTO createUser(UserRegistrationDTO req);
    UsersAccount createUserWithEntity(UsersAccount req);
//to be implemented    List<UsersAccount> bulkCreateUser(BulkCreateUserRequestDTO req);
}
