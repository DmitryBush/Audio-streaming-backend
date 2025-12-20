package ohio.rizz.streamingservice.dto.song;

import ohio.rizz.streamingservice.dto.ArtistReadDto;

public record SongReadDto(Long id,
                          String name,
                          Short discNumber,
                          Integer duration,
                          Short trackNumberAlbum,
                          ArtistReadDto artistDto,
                          Long albumId) {
}
