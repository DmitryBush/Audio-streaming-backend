package ohio.rizz.streamingservice.dto.song;

import ohio.rizz.streamingservice.dto.AlbumDto;
import ohio.rizz.streamingservice.dto.ArtistDto;

public record SongDto(String name,
                      Short discNumber,
                      Integer duration,
                      Short trackNumberAlbum,
                      ArtistDto artistDto,
                      AlbumDto albumDto) {
}
