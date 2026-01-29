package com.bush.search.domain.index;

/**
 * The payload of artist data used in the CDC event
 */
public record ArtistPayload(Long id,
                            String name,
                            String biography) {
}
