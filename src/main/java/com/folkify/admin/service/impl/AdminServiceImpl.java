package com.folkify.admin.service.impl;

import com.folkify.admin.dto.AdminStatsResponse;
import com.folkify.admin.dto.AdminUserResponse;
import com.folkify.admin.service.AdminService;
import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.Role;
import com.folkify.auth.entity.User;
import com.folkify.auth.repository.UserRepository;
import com.folkify.common.exception.ApiException;
import com.folkify.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AdminStatsResponse getStats() {
        List<User> all = userRepository.findAll();
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);

        long totalUsers  = all.size();
        long freeUsers   = all.stream().filter(u -> u.getPlan() == Plan.FREE).count();
        long basicUsers  = all.stream().filter(u -> u.getPlan() == Plan.BASIC).count();
        long proUsers    = all.stream().filter(u -> u.getPlan() == Plan.PRO).count();
        long newThisWeek = all.stream().filter(u -> u.getCreatedAt() != null && u.getCreatedAt().isAfter(weekAgo)).count();
        long totalAdmins = all.stream().filter(u -> u.getRole() == Role.ADMIN).count();

        return new AdminStatsResponse(totalUsers, freeUsers, basicUsers, proUsers, newThisWeek, totalAdmins);
    }

    @Override
    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .sorted((a, b) -> {
                    if (a.getCreatedAt() == null) return 1;
                    if (b.getCreatedAt() == null) return -1;
                    return b.getCreatedAt().compareTo(a.getCreatedAt());
                })
                .map(AdminUserResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public AdminUserResponse updateUserPlan(UUID userId, Plan plan) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        user.setPlan(plan);
        return AdminUserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public AdminUserResponse updateUserRole(UUID userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        user.setRole(role);
        return AdminUserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }
}
