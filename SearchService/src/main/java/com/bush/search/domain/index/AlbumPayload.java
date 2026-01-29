package com.bush.search.domain.index;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * The payload of album data used in the CDC event
 */
public record AlbumPayload(Long id,
                           String name,
                           LocalDate releaseDate,
                           String coverArtUrl,
                           Short discCount,
                           @JsonProperty("artist") ArtistPayload artistPayload,
                           @JsonProperty("genre") GenrePayload genrePayload) {
}
