package ohio.rizz.streamingservice.dto;

import java.time.LocalDate;

public record AlbumDto(String name,
                       LocalDate releaseDate,
                       GenreDto genreDto) {
}
