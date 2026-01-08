package com.bush.search.domain.index;

import com.bush.search.domain.index.service.CdcMetadata;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record ArtistPayload(@JsonProperty("artist_id") Long artistId,
                            String name,
                            String biography,
                            @JsonUnwrapped CdcMetadata metadata) {
}
