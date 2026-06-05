package com.folkify.user.service.impl;

import com.folkify.auth.entity.User;
import com.folkify.auth.repository.RefreshTokenRepository;
import com.folkify.auth.repository.UserRepository;
import com.folkify.common.exception.ApiException;
import com.folkify.common.exception.ErrorCode;
import com.folkify.user.dto.ChangePasswordRequest;
import com.folkify.user.dto.UpdateProfileRequest;
import com.folkify.user.dto.UserProfileResponse;
import com.folkify.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserProfileResponse getProfile(User currentUser) {
        return UserProfileResponse.from(currentUser);
    }

    @Override
    @Transactional
    public UserProfileResponse updateProfile(User currentUser, UpdateProfileRequest request) {
        currentUser.setName(request.name());
        userRepository.save(currentUser);
        return UserProfileResponse.from(currentUser);
    }

    @Override
    @Transactional
    public void changePassword(User currentUser, ChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.currentPassword(), currentUser.getPassword())) {
            throw new ApiException(ErrorCode.WRONG_PASSWORD);
        }
        currentUser.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(currentUser);
        // Thu hồi tất cả refresh token — buộc đăng nhập lại trên thiết bị khác
        refreshTokenRepository.deleteAllByUser(currentUser);
    }

    @Override
    @Transactional
    public void deleteAccount(User currentUser) {
        refreshTokenRepository.deleteAllByUser(currentUser);
        userRepository.delete(currentUser);
    }
}
