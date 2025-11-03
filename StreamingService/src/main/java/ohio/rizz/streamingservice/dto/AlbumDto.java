package ohio.rizz.streamingservice.dto;

import java.time.Year;

public record AlbumDto(String name,
                       Year releaseDate,
                       GenreDto genreDto) {
}
