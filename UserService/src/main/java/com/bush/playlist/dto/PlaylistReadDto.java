package com.bush.playlist.dto;

/**
 * DTO for get information about playlist
 * @param id Unique playlist identifier
 * @param name Playlist name
 */
public record PlaylistReadDto(Long id, String name) {
}
