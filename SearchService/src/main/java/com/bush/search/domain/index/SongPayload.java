package com.bush.search.domain.index;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SongPayload(@JsonProperty("id") Long songId,
                          String name,
                          Short trackNumberAlbum,
                          Integer duration,
                          Short discNumber,
                          @JsonProperty("artist") ArtistPayload artistPayload,
                          @JsonProperty("album") AlbumPayload albumPayload) {
}
