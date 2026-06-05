package com.folkify.progress.dto;

import java.util.List;

public class UserProgressResponse {

    private long totalXp;
    private int totalLessonsCompleted;
    private int currentStreak;
    private int longestStreak;
    private List<InstrumentProgressDto> instruments;

    private UserProgressResponse() {}

    public static UserProgressResponse of(long totalXp, int totalLessonsCompleted, int currentStreak, int longestStreak, List<InstrumentProgressDto> instruments) {
        UserProgressResponse r = new UserProgressResponse();
        r.totalXp = totalXp;
        r.totalLessonsCompleted = totalLessonsCompleted;
        r.currentStreak = currentStreak;
        r.longestStreak = longestStreak;
        r.instruments = instruments;
        return r;
    }

    public long getTotalXp() { return totalXp; }
    public int getTotalLessonsCompleted() { return totalLessonsCompleted; }
    public int getCurrentStreak() { return currentStreak; }
    public int getLongestStreak() { return longestStreak; }
    public List<InstrumentProgressDto> getInstruments() { return instruments; }
}
