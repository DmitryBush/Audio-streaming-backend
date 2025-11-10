package ohio.rizz.streamingservice.dto;

public record SongReadDto(Long id,
                          String name,
                          Short discNumber,
                          Integer duration,
                          Short trackNumberAlbum,
                          ArtistDto artistDto,
                          AlbumDto albumDto) {
}
