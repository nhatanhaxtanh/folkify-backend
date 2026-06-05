package com.folkify.instrument.dto;

import com.folkify.instrument.entity.Lesson;

import java.util.UUID;

public record LessonSummaryResponse(
        UUID id,
        String slug,
        String title,
        String duration,
        String level,
        int xp,
        int orderIndex
) {
    public static LessonSummaryResponse from(Lesson lesson) {
        return new LessonSummaryResponse(
                lesson.getId(), lesson.getSlug(), lesson.getTitle(),
                lesson.getDuration(), lesson.getLevel(), lesson.getXp(), lesson.getOrderIndex()
        );
    }
}
