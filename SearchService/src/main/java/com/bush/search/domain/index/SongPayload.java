package com.bush.search.domain.index;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SongPayload(@JsonProperty("song_id") Long songId,
                          String name,
                          @JsonProperty("track_number_album") Short trackNumberAlbum,
                          Integer duration,
                          @JsonProperty("disc_number") Short discNumber,
                          @JsonProperty("artist") ArtistPayload artistPayload,
                          @JsonProperty("album") AlbumPayload albumPayload) {
}
