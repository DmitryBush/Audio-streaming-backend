package com.bush.user.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SecurityConstants {
    BLACKLIST_KEY_PREFIX("BLACKLIST_KEY_PREFIX");

    private final String value;
}
