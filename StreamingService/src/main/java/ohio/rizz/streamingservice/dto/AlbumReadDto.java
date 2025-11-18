package ohio.rizz.streamingservice.dto;

public record AlbumReadDto(Long id,
                           String name,
                           Integer releaseYear,
                           Short discCount,
                           GenreReadDto genreDto) {
}
