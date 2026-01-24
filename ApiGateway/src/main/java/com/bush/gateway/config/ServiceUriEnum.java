package com.bush.gateway.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ServiceUriEnum {
    STREAMING_SERVICE_URI("lb://StreamingService"), USER_SERVICE_URI("lb://UserService"),
    SEARCH_SERVICE_URI("lb://SearchService");

    private final String serviceUri;
}
