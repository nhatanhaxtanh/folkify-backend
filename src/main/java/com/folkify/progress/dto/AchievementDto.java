package com.folkify.progress.dto;

import com.folkify.progress.entity.Achievement;
import com.folkify.progress.entity.UserAchievement;

import java.time.LocalDateTime;
import java.util.UUID;

public class AchievementDto {

    private UUID id;
    private String slug;
    private String name;
    private String description;
    private String icon;
    private LocalDateTime unlockedAt;

    private AchievementDto() {}

    public static AchievementDto fromUnlocked(UserAchievement ua) {
        AchievementDto dto = new AchievementDto();
        Achievement a = ua.getAchievement();
        dto.id = a.getId();
        dto.slug = a.getSlug();
        dto.name = a.getName();
        dto.description = a.getDescription();
        dto.icon = a.getIcon();
        dto.unlockedAt = ua.getUnlockedAt();
        return dto;
    }

    public static AchievementDto fromLocked(Achievement a) {
        AchievementDto dto = new AchievementDto();
        dto.id = a.getId();
        dto.slug = a.getSlug();
        dto.name = a.getName();
        dto.description = a.getDescription();
        dto.icon = a.getIcon();
        return dto;
    }

    public static AchievementDto fromNewlyUnlocked(Achievement a, LocalDateTime unlockedAt) {
        AchievementDto dto = new AchievementDto();
        dto.id = a.getId();
        dto.slug = a.getSlug();
        dto.name = a.getName();
        dto.description = a.getDescription();
        dto.icon = a.getIcon();
        dto.unlockedAt = unlockedAt;
        return dto;
    }

    public UUID getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIcon() { return icon; }
    public LocalDateTime getUnlockedAt() { return unlockedAt; }
}
