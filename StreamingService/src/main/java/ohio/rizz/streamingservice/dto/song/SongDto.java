package ohio.rizz.streamingservice.dto.song;

import ohio.rizz.streamingservice.dto.AlbumDto;
import ohio.rizz.streamingservice.dto.ArtistDto;

/**
 * DTO to create song metadata
 * @param artistDto DTO to create artist metadata
 * @param albumDto DTO to create album metadata
 */
public record SongDto(String name,
                      Short discNumber,
                      Integer duration,
                      Short trackNumberAlbum,
                      String objectStorageLink,
                      ArtistDto artistDto,
                      AlbumDto albumDto) {
}
