package com.bush.search.domain.dto;

public record AlbumSearchResultDto(Long albumId,
                                   String name,
                                   String coverArtUrl,
                                   ArtistSearchResultDto artist,
                                   GenreSearchResultDto genre) {
}
