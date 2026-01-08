package com.bush.search.domain.index;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenrePayload(@JsonProperty("genre_id") Short genreId,
                           String name) {
}
