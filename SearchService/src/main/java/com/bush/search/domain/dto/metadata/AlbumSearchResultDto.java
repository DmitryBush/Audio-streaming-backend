package com.bush.search.domain.dto.metadata;

public record AlbumSearchResultDto(Long albumId,
                                   String name,
                                   String coverArtUrl,
                                   ArtistSearchResultDto artist,
                                   GenreSearchResultDto genre) {
}
