package com.folkify.admin.dto;

import java.util.List;

public record InstrumentUpdateRequest(
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
        Integer difficulty,
        Integer popularity,
        List<String> facts
) {}
