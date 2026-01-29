package ohio.rizz.streamingservice.dto;

/**
 * DTO to get information about artist
 * @param id Unique artist identifier
 * @param name Name of artist
 */
public record ArtistReadDto(Long id, String name) {
}
