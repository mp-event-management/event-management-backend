package com.eventmanagement.EventManagementBackend.infrastructure.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponseDTO implements Serializable {
    private Integer id;
    private String email;
}
