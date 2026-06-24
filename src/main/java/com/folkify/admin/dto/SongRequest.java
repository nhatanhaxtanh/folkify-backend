package com.folkify.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SongRequest(
        @NotNull UUID instrumentId,
        @NotBlank String title,
        String artist,
        String duration,
        int orderIndex
) {}
