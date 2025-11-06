package ohio.rizz.streamingservice.dto;

import java.time.LocalDate;

public record AlbumReadDto(Long id,
                           String name,
                           LocalDate releaseDate,
                           Short discCount,
                           GenreDto genreDto) {
}
