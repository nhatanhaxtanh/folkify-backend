package com.folkify.progress.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class DailyActivityId implements Serializable {

    private UUID userId;
    private LocalDate activityDate;

    public DailyActivityId() {}

    public DailyActivityId(UUID userId, LocalDate activityDate) {
        this.userId = userId;
        this.activityDate = activityDate;
    }

    public UUID getUserId() { return userId; }
    public LocalDate getActivityDate() { return activityDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailyActivityId that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(activityDate, that.activityDate);
    }

    @Override
    public int hashCode() { return Objects.hash(userId, activityDate); }
}
