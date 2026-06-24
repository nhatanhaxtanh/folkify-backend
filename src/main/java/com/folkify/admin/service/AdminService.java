package com.folkify.admin.service;

import com.folkify.admin.dto.AdminStatsResponse;
import com.folkify.admin.dto.AdminUserResponse;
import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.Role;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    AdminStatsResponse getStats();
    List<AdminUserResponse> getAllUsers();
    AdminUserResponse updateUserPlan(UUID userId, Plan plan);
    AdminUserResponse updateUserRole(UUID userId, Role role);
    void deleteUser(UUID userId);
}
