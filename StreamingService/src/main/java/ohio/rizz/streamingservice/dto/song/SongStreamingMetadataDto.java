package ohio.rizz.streamingservice.dto.song;

/**
 * Essential track information for streaming
 * @param id Song identifier
 * @param contentLength Track size in bytes
 * @param contentType {@code MIME} type of the track
 * @param objectStorageLink Path to get track from s3 storage
 */
public record SongStreamingMetadataDto(Long id,
                                       Long contentLength,
                                       String contentType,
                                       String objectStorageLink) {
}
