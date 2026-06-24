package com.folkify.sheet.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import com.folkify.instrument.entity.Instrument;
import jakarta.persistence.*;

@Entity
@Table(name = "sheet_music")
public class SheetMusic extends BaseEntity {

    @Column(nullable = false)
    private String title;

    private String author;
    private String genre;
    private String difficulty;

    @Column(nullable = false)
    private int pages = 1;

    @Column(nullable = false)
    private boolean isPremium = false;

    @Column(columnDefinition = "TEXT")
    private String fileUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    public SheetMusic() {}

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getDifficulty() { return difficulty; }
    public int getPages() { return pages; }
    public boolean isPremium() { return isPremium; }
    public String getFileUrl() { return fileUrl; }
    public String getDescription() { return description; }
    public Instrument getInstrument() { return instrument; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public void setPages(int pages) { this.pages = pages; }
    public void setPremium(boolean premium) { isPremium = premium; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public void setDescription(String description) { this.description = description; }
    public void setInstrument(Instrument instrument) { this.instrument = instrument; }
}
