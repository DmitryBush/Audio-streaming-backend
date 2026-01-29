package ohio.rizz.streamingservice.dto;

/**
 * DTO to get information about genre
 * @param id Unique genre identifier
 * @param name Name of genre
 */
public record GenreReadDto(Short id, String name) {
}
