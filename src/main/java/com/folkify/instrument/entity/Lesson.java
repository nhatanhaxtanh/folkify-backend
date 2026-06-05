package com.folkify.instrument.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrument_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_lesson_instrument"))
    private Instrument instrument;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    private String duration;
    private String level;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "lesson_steps", joinColumns = @JoinColumn(name = "lesson_id"))
    @Column(name = "step", columnDefinition = "TEXT")
    @OrderColumn(name = "order_index")
    private List<String> steps = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "lesson_tips", joinColumns = @JoinColumn(name = "lesson_id"))
    @Column(name = "tip", columnDefinition = "TEXT")
    @OrderColumn(name = "order_index")
    private List<String> tips = new ArrayList<>();

    private int xp;
    private String youtubeUrl;
    private int orderIndex;

    public Lesson() {}

    public Instrument getInstrument() { return instrument; }
    public String getSlug() { return slug; }
    public String getTitle() { return title; }
    public String getDuration() { return duration; }
    public String getLevel() { return level; }
    public String getDescription() { return description; }
    public List<String> getSteps() { return steps; }
    public List<String> getTips() { return tips; }
    public int getXp() { return xp; }
    public String getYoutubeUrl() { return youtubeUrl; }
    public int getOrderIndex() { return orderIndex; }
}
