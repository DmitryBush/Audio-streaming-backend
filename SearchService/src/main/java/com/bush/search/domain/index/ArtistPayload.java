package com.bush.search.domain.index;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArtistPayload(@JsonProperty("artist_id") Long artistId,
                            String name,
                            String biography) {
}
