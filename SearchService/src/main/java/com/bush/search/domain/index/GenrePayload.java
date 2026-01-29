package com.bush.search.domain.index;

/**
 * The payload of genre data used in the CDC event
 */
public record GenrePayload(Short id,
                           String name) {
}
