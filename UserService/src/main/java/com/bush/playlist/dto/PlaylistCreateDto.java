package com.bush.playlist.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

public record PlaylistCreateDto(@NotBlank @Max(255) String name) {
}
