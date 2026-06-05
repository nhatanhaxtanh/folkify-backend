package com.folkify.instrument.dto;

import com.folkify.instrument.entity.Instrument;

import java.util.UUID;

public record InstrumentSummaryResponse(
        UUID id,
        String slug,
        String name,
        String englishName,
        String category,
        String emoji,
        String color,
        String imageUrl,
        String shortDesc,
        int difficulty,
        int popularity,
        int lessonCount
) {
    public static InstrumentSummaryResponse from(Instrument i) {
        return new InstrumentSummaryResponse(
                i.getId(), i.getSlug(), i.getName(), i.getEnglishName(),
                i.getCategory(), i.getEmoji(), i.getColor(), i.getImageUrl(),
                i.getShortDesc(), i.getDifficulty(), i.getPopularity(), i.getLessonCount()
        );
    }
}
