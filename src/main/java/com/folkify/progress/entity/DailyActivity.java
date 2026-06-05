package com.folkify.progress.entity;

import com.folkify.auth.entity.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_activities")
public class DailyActivity {

    @EmbeddedId
    private DailyActivityId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "xp_earned", nullable = false)
    private int xpEarned;

    @Column(nullable = false)
    private int minutes;

    public DailyActivity() {}

    public DailyActivity(User user, LocalDate date) {
        this.id = new DailyActivityId(user.getId(), date);
        this.user = user;
        this.xpEarned = 0;
        this.minutes = 0;
    }

    public DailyActivityId getId() { return id; }
    public User getUser() { return user; }
    public LocalDate getActivityDate() { return id.getActivityDate(); }
    public int getXpEarned() { return xpEarned; }
    public int getMinutes() { return minutes; }

    public void addXp(int xp) { this.xpEarned += xp; }
    public void addMinutes(int mins) { this.minutes += mins; }
}
