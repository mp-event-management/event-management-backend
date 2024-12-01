package com.eventmanagement.EventManagementBackend.infrastructure.users.dto;

import com.eventmanagement.EventManagementBackend.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    @NotNull
    private Integer userId;

    @NotNull
    private Role role;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private String profilePictureUrl;

}
