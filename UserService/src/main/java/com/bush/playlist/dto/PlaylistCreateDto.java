package com.bush.playlist.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * DTO to create playlist
 * @param name Playlist name
 */
public record PlaylistCreateDto(@NotBlank @Length(max = 255) String name) {
}
