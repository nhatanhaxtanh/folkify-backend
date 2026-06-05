package com.folkify.instrument.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrument_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_song_instrument"))
    private Instrument instrument;

    @Column(nullable = false)
    private String title;

    private String artist;
    private String duration;
    private int orderIndex;

    public Song() {}

    public Instrument getInstrument() { return instrument; }
    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getDuration() { return duration; }
    public int getOrderIndex() { return orderIndex; }
}
