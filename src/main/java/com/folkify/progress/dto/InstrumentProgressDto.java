package com.folkify.progress.dto;

import com.folkify.instrument.entity.Instrument;

import java.util.UUID;

public class InstrumentProgressDto {

    private UUID id;
    private String slug;
    private String name;
    private String emoji;
    private String color;
    private int completedLessons;
    private int totalLessons;
    private double progressPercent;

    private InstrumentProgressDto() {}

    public static InstrumentProgressDto of(Instrument instrument, int completed, int total) {
        InstrumentProgressDto dto = new InstrumentProgressDto();
        dto.id = instrument.getId();
        dto.slug = instrument.getSlug();
        dto.name = instrument.getName();
        dto.emoji = instrument.getEmoji();
        dto.color = instrument.getColor();
        dto.completedLessons = completed;
        dto.totalLessons = total;
        dto.progressPercent = total > 0 ? Math.round((double) completed / total * 1000.0) / 10.0 : 0.0;
        return dto;
    }

    public UUID getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getEmoji() { return emoji; }
    public String getColor() { return color; }
    public int getCompletedLessons() { return completedLessons; }
    public int getTotalLessons() { return totalLessons; }
    public double getProgressPercent() { return progressPercent; }
}
