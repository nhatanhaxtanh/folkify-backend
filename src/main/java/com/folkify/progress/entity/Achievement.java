package com.folkify.progress.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "achievements")
public class Achievement extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String icon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AchievementType type;

    @Column(nullable = false)
    private int threshold;

    public Achievement() {}

    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIcon() { return icon; }
    public AchievementType getType() { return type; }
    public int getThreshold() { return threshold; }
}
