package com.folkify.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record LessonRequest(
        @NotNull UUID instrumentId,
        @NotBlank String slug,
        @NotBlank String title,
        String duration,
        String level,
        String description,
        List<String> steps,
        List<String> tips,
        int xp,
        String youtubeUrl,
        int orderIndex
) {}
