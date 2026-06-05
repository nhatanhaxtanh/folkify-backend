package com.folkify.progress.entity;

import com.folkify.auth.entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_achievements")
public class UserAchievement {

    @EmbeddedId
    private UserAchievementId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("achievementId")
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @CreationTimestamp
    @Column(name = "unlocked_at", nullable = false, updatable = false)
    private LocalDateTime unlockedAt;

    public UserAchievement() {}

    public UserAchievement(User user, Achievement achievement) {
        this.id = new UserAchievementId(user.getId(), achievement.getId());
        this.user = user;
        this.achievement = achievement;
    }

    public UserAchievementId getId() { return id; }
    public User getUser() { return user; }
    public Achievement getAchievement() { return achievement; }
    public LocalDateTime getUnlockedAt() { return unlockedAt; }
}
