package ohio.rizz.streamingservice.dto.song;

import ohio.rizz.streamingservice.dto.ArtistReadDto;

/**
 * DTO to get information about song
 * @param artistDto DTO to get information about artist
 */
public record SongReadDto(Long id,
                          String name,
                          Short discNumber,
                          Integer duration,
                          Short trackNumberAlbum,
                          ArtistReadDto artistDto,
                          Long albumId) {
}
