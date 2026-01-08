package com.bush.search.domain.index;

import com.bush.search.domain.index.service.CdcMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.time.LocalDate;

public record AlbumPayload(@JsonProperty("album_id") Long id,
                           String name,
                           @JsonProperty("release_date") LocalDate releaseDate,
                           @JsonProperty("cover_art_url") String coverArtUrl,
                           @JsonProperty("disc_count") Short discCount,
                           @JsonProperty("f_key_artist_id") Long artistId,
                           @JsonProperty("f_key_genre_id") Short genreId,
                           @JsonUnwrapped CdcMetadata metadata) {
}
