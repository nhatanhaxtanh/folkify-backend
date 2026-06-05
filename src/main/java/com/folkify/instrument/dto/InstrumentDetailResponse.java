package com.folkify.instrument.dto;

import com.folkify.instrument.entity.Instrument;

import java.util.List;
import java.util.UUID;

public record InstrumentDetailResponse(
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
        List<String> facts,
        List<LessonSummaryResponse> lessons,
        List<SongResponse> songs
) {
    public static InstrumentDetailResponse from(Instrument i) {
        return new InstrumentDetailResponse(
                i.getId(), i.getSlug(), i.getName(), i.getEnglishName(),
                i.getRegion(), i.getCategory(), i.getEmoji(), i.getColor(),
                i.getImageUrl(), i.getShortDesc(), i.getDescription(),
                i.getOrigin(), i.getMaterial(), i.getSoundRange(),
                i.getDifficulty(), i.getPopularity(),
                i.getFacts(),
                i.getLessons().stream().map(LessonSummaryResponse::from).toList(),
                i.getSongs().stream().map(SongResponse::from).toList()
        );
    }
}
