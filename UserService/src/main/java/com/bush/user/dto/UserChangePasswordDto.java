package com.bush.user.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UserChangePasswordDto(@NotBlank @Length(min = 4, max = 32) String login,
                                    @NotBlank @Length(min = 8, max = 255) String oldPassword,
                                    @NotBlank @Length(min = 8, max = 255) String newPassword) {
}
