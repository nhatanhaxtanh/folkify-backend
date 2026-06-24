package com.folkify.admin.dto;

public record AdminStatsResponse(
        long totalUsers,
        long freeUsers,
        long basicUsers,
        long proUsers,
        long newUsersThisWeek,
        long totalAdmins
) {}
