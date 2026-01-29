package com.bush.user.dto;

/**
 * DTO used to get info about user in the application's security layers
 */
public record UserReadDto(String login, String role, Long passwordVersion) {
}
