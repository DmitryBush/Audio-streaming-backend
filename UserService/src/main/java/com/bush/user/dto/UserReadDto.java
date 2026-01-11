package com.bush.user.dto;

public record UserReadDto(String login, String role, Long passwordVersion) {
}
