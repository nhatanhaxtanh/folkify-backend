package com.folkify.progress.dto;

import java.util.List;

public class CompleteLessonResponse {

    private int xpEarned;
    private long totalXp;
    private int currentStreak;
    private List<AchievementDto> newAchievements;

    private CompleteLessonResponse() {}

    public static CompleteLessonResponse of(int xpEarned, long totalXp, int currentStreak, List<AchievementDto> newAchievements) {
        CompleteLessonResponse r = new CompleteLessonResponse();
        r.xpEarned = xpEarned;
        r.totalXp = totalXp;
        r.currentStreak = currentStreak;
        r.newAchievements = newAchievements;
        return r;
    }

    public int getXpEarned() { return xpEarned; }
    public long getTotalXp() { return totalXp; }
    public int getCurrentStreak() { return currentStreak; }
    public List<AchievementDto> getNewAchievements() { return newAchievements; }
}
