package com.folkify.progress.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class UserLessonProgressId implements Serializable {

    private UUID userId;
    private UUID lessonId;

    public UserLessonProgressId() {}

    public UserLessonProgressId(UUID userId, UUID lessonId) {
        this.userId = userId;
        this.lessonId = lessonId;
    }

    public UUID getUserId() { return userId; }
    public UUID getLessonId() { return lessonId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLessonProgressId that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(lessonId, that.lessonId);
    }

    @Override
    public int hashCode() { return Objects.hash(userId, lessonId); }
}
