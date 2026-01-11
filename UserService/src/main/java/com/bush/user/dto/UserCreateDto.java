package com.bush.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserCreateDto(@NotBlank @Length(min = 4, max = 32) String login,
                            @NotBlank @Length(min = 8, max = 255) String password,
                            @NotNull Short roleId) {
}
