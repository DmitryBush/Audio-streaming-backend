package com.bush.search.domain.dto;

public record SongSearchResultDto(Long songId,
                                  String name,
                                  Integer duration,
                                  ArtistSearchResultDto artist,
                                  AlbumSearchResultDto album) {
}
