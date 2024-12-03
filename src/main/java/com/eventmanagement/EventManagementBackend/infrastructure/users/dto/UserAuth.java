package com.eventmanagement.EventManagementBackend.infrastructure.users.dto;

import com.eventmanagement.EventManagementBackend.entity.UsersAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuth implements UserDetails {
    private UsersAccount user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("Role object: " + user.getRole());  // Debug print
        System.out.println("Role ID: " + (user.getRole() != null ? user.getRole().getRoleId() : "null"));  // Debug print
        System.out.println("Role Name: " + (user.getRole() != null ? user.getRole().getName() : "null"));  // Debug print

        if (user.getRole() != null) {
            String authority = "ROLE_" + user.getRole().getName();
            System.out.println("Generated Authority: " + user.getRole().getName());  // Debug print
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        }
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }
}

