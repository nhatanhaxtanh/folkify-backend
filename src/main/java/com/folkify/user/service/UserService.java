package com.folkify.user.service;

import com.folkify.auth.entity.User;
import com.folkify.user.dto.ChangePasswordRequest;
import com.folkify.user.dto.UpdateProfileRequest;
import com.folkify.user.dto.UserProfileResponse;

public interface UserService {
    UserProfileResponse getProfile(User currentUser);
    UserProfileResponse updateProfile(User currentUser, UpdateProfileRequest request);
    void changePassword(User currentUser, ChangePasswordRequest request);
    void deleteAccount(User currentUser);
}
