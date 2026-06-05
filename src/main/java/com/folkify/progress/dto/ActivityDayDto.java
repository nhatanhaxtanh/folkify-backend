package com.folkify.progress.dto;

import java.time.LocalDate;

public class ActivityDayDto {

    private LocalDate date;
    private String dayLabel;
    private int xpEarned;
    private int minutes;
    private boolean isToday;

    private ActivityDayDto() {}

    public static ActivityDayDto of(LocalDate date, int xpEarned, int minutes, boolean isToday) {
        ActivityDayDto dto = new ActivityDayDto();
        dto.date = date;
        dto.dayLabel = toDayLabel(date);
        dto.xpEarned = xpEarned;
        dto.minutes = minutes;
        dto.isToday = isToday;
        return dto;
    }

    private static String toDayLabel(LocalDate date) {
        return switch (date.getDayOfWeek()) {
            case MONDAY -> "T2";
            case TUESDAY -> "T3";
            case WEDNESDAY -> "T4";
            case THURSDAY -> "T5";
            case FRIDAY -> "T6";
            case SATURDAY -> "T7";
            case SUNDAY -> "CN";
        };
    }

    public LocalDate getDate() { return date; }
    public String getDayLabel() { return dayLabel; }
    public int getXpEarned() { return xpEarned; }
    public int getMinutes() { return minutes; }
    public boolean isToday() { return isToday; }
}
