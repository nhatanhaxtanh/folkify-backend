package com.folkify.admin.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record SheetMusicRequest(
        @NotBlank String title,
        String author,
        String genre,
        String difficulty,
        int pages,
        boolean isPremium,
        String fileUrl,
        String description,
        UUID instrumentId
) {}
