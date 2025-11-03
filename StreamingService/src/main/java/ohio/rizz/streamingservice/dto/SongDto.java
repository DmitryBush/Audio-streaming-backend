package ohio.rizz.streamingservice.dto;

public record SongDto(String name,
                      Short discNumber,
                      Integer duration,
                      Short trackNumberAlbum,
                      ArtistDto artistDto,
                      AlbumDto albumDto) {
}
