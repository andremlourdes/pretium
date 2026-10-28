package org.example.dto.auth;

import org.example.entity.AppUser;

public record UserResponse(Long id, String username, String email, String role) {
    public static UserResponse from(AppUser user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getAuthorities().iterator().next().getAuthority().replace("ROLE_", ""));
    }
}

