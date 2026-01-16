package com.bush.search.domain.index;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record AlbumPayload(@JsonProperty("album_id") Long id,
                           String name,
                           @JsonProperty("release_date") LocalDate releaseDate,
                           @JsonProperty("cover_art_url") String coverArtUrl,
                           @JsonProperty("disc_count") Short discCount,
                           @JsonProperty("artist") ArtistPayload artistPayload,
                           @JsonProperty("genre") GenrePayload genrePayload) {
}
