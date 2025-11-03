package ohio.rizz.streamingservice.dto;

public record SongDto(String name,
                      Integer duration,
                      Short trackNumberAlbum,
                      ArtistDto artistDto,
                      AlbumDto albumDto) {
}
