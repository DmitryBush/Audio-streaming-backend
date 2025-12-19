package com.bush.user.dto;

public record UserChangePasswordDto(String login, String oldPassword, String newPassword) {
}
