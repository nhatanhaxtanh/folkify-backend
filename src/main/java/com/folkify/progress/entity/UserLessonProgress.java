package com.folkify.progress.entity;

import com.folkify.auth.entity.User;
import com.folkify.instrument.entity.Lesson;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_lesson_progress")
public class UserLessonProgress {

    @EmbeddedId
    private UserLessonProgressId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @CreationTimestamp
    @Column(name = "completed_at", nullable = false, updatable = false)
    private LocalDateTime completedAt;

    public UserLessonProgress() {}

    public UserLessonProgress(User user, Lesson lesson) {
        this.id = new UserLessonProgressId(user.getId(), lesson.getId());
        this.user = user;
        this.lesson = lesson;
    }

    public UserLessonProgressId getId() { return id; }
    public User getUser() { return user; }
    public Lesson getLesson() { return lesson; }
    public LocalDateTime getCompletedAt() { return completedAt; }
}
