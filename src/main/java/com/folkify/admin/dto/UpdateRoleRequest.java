package com.folkify.admin.dto;

import com.folkify.auth.entity.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(
        @NotNull(message = "Role không được để trống")
        Role role
) {}
