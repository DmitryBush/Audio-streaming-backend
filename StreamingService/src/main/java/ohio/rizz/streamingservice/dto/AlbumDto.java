package ohio.rizz.streamingservice.dto;

import java.time.LocalDate;

/**
 * DTO to create album metadata
 * @param genreDto DTO to create genre metadata
 * @param artworkDto DTO to create artwork
 */
public record AlbumDto(String name,
                       LocalDate releaseDate,
                       Short discCount,
                       GenreDto genreDto,
                       ArtworkDto artworkDto) {
}
