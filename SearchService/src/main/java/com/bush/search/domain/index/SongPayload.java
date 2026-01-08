package com.bush.search.domain.index;

import com.bush.search.domain.index.service.CdcMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record SongPayload(@JsonProperty("song_id") Long songId,
                          String name,
                          @JsonProperty("track_number_album") Short trackNumberAlbum,
                          Integer duration,
                          @JsonProperty("disc_number") Short discNumber,
                          @JsonProperty("f_key_artist_id") Long artistId,
                          @JsonProperty("f_key_album_id") Long albumId,
                          @JsonUnwrapped CdcMetadata metadata) {
}
