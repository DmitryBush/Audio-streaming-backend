package com.bush.playlist.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record PlaylistCreateDto(@NotBlank @Length(max = 255) String name) {
}
