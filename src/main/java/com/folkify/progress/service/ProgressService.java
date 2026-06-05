package com.folkify.progress.service;

import com.folkify.auth.entity.User;
import com.folkify.progress.dto.*;

import java.util.List;
import java.util.UUID;

public interface ProgressService {

    CompleteLessonResponse completeLesson(UUID lessonId, User user);

    UserProgressResponse getProgress(User user);

    AchievementsResponse getAchievements(User user);

    List<ActivityDayDto> getWeeklyActivity(User user);
}
