package com.bush.search.domain.index.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Operation {
    CREATE("c"), READ("r"), UPDATE("u"), DELETE("d");

    private final String code;
}
