package com.bush.user.dto;

public record UserCreateDto(String login, String password, Short roleId) {
}
