package com.folkify.instrument.dto;

import com.folkify.instrument.entity.Song;

import java.util.UUID;

public record SongResponse(UUID id, String title, String artist, String duration) {
    public static SongResponse from(Song song) {
        return new SongResponse(song.getId(), song.getTitle(), song.getArtist(), song.getDuration());
    }
}
