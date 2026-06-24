package com.folkify.admin.dto;

import com.folkify.instrument.entity.Instrument;

import java.util.List;
import java.util.UUID;

public record InstrumentAdminResponse(
        UUID id,
        String slug,
        String name,
        String englishName,
        String region,
        String category,
        String emoji,
        String color,
        String imageUrl,
        String shortDesc,
        String description,
        String origin,
        String material,
        String soundRange,
        int difficulty,
        int popularity,
        int lessonCount,
        List<String> facts
) {
    public static InstrumentAdminResponse from(Instrument i) {
        return new InstrumentAdminResponse(
                i.getId(), i.getSlug(), i.getName(), i.getEnglishName(),
                i.getRegion(), i.getCategory(), i.getEmoji(), i.getColor(),
                i.getImageUrl(), i.getShortDesc(), i.getDescription(),
                i.getOrigin(), i.getMaterial(), i.getSoundRange(),
                i.getDifficulty(), i.getPopularity(), i.getLessonCount(),
                i.getFacts()
        );
    }
}
