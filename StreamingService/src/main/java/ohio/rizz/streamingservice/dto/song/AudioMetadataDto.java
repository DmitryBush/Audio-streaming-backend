package ohio.rizz.streamingservice.dto.song;

import ohio.rizz.streamingservice.Entities.Song;

public record AudioMetadataDto(Long id,
                               Long contentLength,
                               String contentType,
                               String objectPath) {
}
