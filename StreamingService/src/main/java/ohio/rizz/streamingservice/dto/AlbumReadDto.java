package ohio.rizz.streamingservice.dto;

/**
 * DTO to get information about album
 */
public record AlbumReadDto(Long id,
                           String name,
                           Integer releaseYear,
                           Short discCount,
                           GenreReadDto genreDto) {
}
