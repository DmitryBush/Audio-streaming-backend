package com.bush.search.domain.index;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlaylistPayload(@JsonProperty("playlist_id") Long playlistId,
                              String name,
                              @JsonProperty("creator_id") String creatorId) {
}
