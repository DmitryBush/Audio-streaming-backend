package com.bush.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateDto(@NotBlank @Min(4) @Max(32) String login,
                            @NotBlank @Min(8) @Max(255) String password,
                            @NotNull Short roleId) {
}
