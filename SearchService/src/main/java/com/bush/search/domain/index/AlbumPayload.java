package com.bush.search.domain.index;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record AlbumPayload(Long id,
                           String name,
                           LocalDate releaseDate,
                           String coverArtUrl,
                           Short discCount,
                           @JsonProperty("artist") ArtistPayload artistPayload,
                           @JsonProperty("genre") GenrePayload genrePayload) {
}
