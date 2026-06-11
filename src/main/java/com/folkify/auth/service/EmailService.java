package com.folkify.auth.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String resetToken);
}
