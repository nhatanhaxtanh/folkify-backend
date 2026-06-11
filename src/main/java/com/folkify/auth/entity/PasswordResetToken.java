package com.folkify.auth.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken extends BaseEntity {

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_password_reset_token_user"))
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean used = false;

    public PasswordResetToken() {}

    private PasswordResetToken(Builder builder) {
        this.token = builder.token;
        this.user = builder.user;
        this.expiresAt = builder.expiresAt;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token;
        private User user;
        private LocalDateTime expiresAt;

        public Builder token(String token) { this.token = token; return this; }
        public Builder user(User user) { this.user = user; return this; }
        public Builder expiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; return this; }
        public PasswordResetToken build() { return new PasswordResetToken(this); }
    }

    public String getToken() { return token; }
    public User getUser() { return user; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }

    public boolean isExpired() { return LocalDateTime.now().isAfter(expiresAt); }
    public boolean isValid() { return !used && !isExpired(); }
}
