package com.folkify.admin.dto;

import com.folkify.sheet.entity.SheetMusic;

import java.util.UUID;

public record SheetMusicResponse(
        UUID id,
        String title,
        String author,
        String genre,
        String difficulty,
        int pages,
        boolean isPremium,
        String fileUrl,
        String description,
        UUID instrumentId,
        String instrumentName
) {
    public static SheetMusicResponse from(SheetMusic s) {
        return new SheetMusicResponse(
                s.getId(), s.getTitle(), s.getAuthor(), s.getGenre(),
                s.getDifficulty(), s.getPages(), s.isPremium(),
                s.getFileUrl(), s.getDescription(),
                s.getInstrument() != null ? s.getInstrument().getId() : null,
                s.getInstrument() != null ? s.getInstrument().getName() : null
        );
    }
}
