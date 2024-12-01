package com.eventmanagement.EventManagementBackend.infrastructure.users.dto;

import com.eventmanagement.EventManagementBackend.entity.City;
import com.eventmanagement.EventManagementBackend.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private Integer userId;

    @NotNull
    private Role role;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private LocalDate birthDate;

    private City city;

    private String address;

    private String profilePictureUrl;

    @NotNull
    private String referralCode;

    private String usedReferralCode;

    @NotNull
    private Boolean isFirstTimeDiscount = false;

}
