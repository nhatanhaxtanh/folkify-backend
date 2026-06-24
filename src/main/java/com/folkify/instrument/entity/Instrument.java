package com.folkify.instrument.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instruments")
public class Instrument extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private String name;

    private String englishName;
    private String region;
    private String category;
    private String emoji;
    private String color;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String shortDesc;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String origin;

    @Column(columnDefinition = "TEXT")
    private String material;

    private String soundRange;
    private int difficulty;
    private int popularity;

    @Formula("(SELECT COUNT(*) FROM lessons l WHERE l.instrument_id = id)")
    private int lessonCount;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "instrument_facts", joinColumns = @JoinColumn(name = "instrument_id"))
    @Column(name = "fact", columnDefinition = "TEXT")
    @OrderColumn(name = "order_index")
    private List<String> facts = new ArrayList<>();

    @OneToMany(mappedBy = "instrument", fetch = FetchType.LAZY)
    @OrderBy("orderIndex ASC")
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "instrument", fetch = FetchType.LAZY)
    @OrderBy("orderIndex ASC")
    private List<Song> songs = new ArrayList<>();

    public Instrument() {}

    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getEnglishName() { return englishName; }
    public String getRegion() { return region; }
    public String getCategory() { return category; }
    public String getEmoji() { return emoji; }
    public String getColor() { return color; }
    public String getImageUrl() { return imageUrl; }
    public String getShortDesc() { return shortDesc; }
    public String getDescription() { return description; }
    public String getOrigin() { return origin; }
    public String getMaterial() { return material; }
    public String getSoundRange() { return soundRange; }
    public int getDifficulty() { return difficulty; }
    public int getPopularity() { return popularity; }
    public int getLessonCount() { return lessonCount; }
    public List<String> getFacts() { return facts; }
    public List<Lesson> getLessons() { return lessons; }
    public List<Song> getSongs() { return songs; }

    public void setName(String name) { this.name = name; }
    public void setEnglishName(String englishName) { this.englishName = englishName; }
    public void setRegion(String region) { this.region = region; }
    public void setCategory(String category) { this.category = category; }
    public void setEmoji(String emoji) { this.emoji = emoji; }
    public void setColor(String color) { this.color = color; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setShortDesc(String shortDesc) { this.shortDesc = shortDesc; }
    public void setDescription(String description) { this.description = description; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setMaterial(String material) { this.material = material; }
    public void setSoundRange(String soundRange) { this.soundRange = soundRange; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    public void setPopularity(int popularity) { this.popularity = popularity; }
    public void setFacts(List<String> facts) { this.facts = facts; }
}
