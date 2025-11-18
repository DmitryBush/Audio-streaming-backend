package ohio.rizz.streamingservice.dto.song;

import ohio.rizz.streamingservice.dto.ArtistDto;

public record SongReadDto(Long id,
                          String name,
                          Short discNumber,
                          Integer duration,
                          Short trackNumberAlbum,
                          ArtistDto artistDto,
                          Long albumId) {
}
