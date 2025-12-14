package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.service.song.AudioMetadataService;
import ohio.rizz.streamingservice.service.song.SongService;
import ohio.rizz.streamingservice.service.storage.ObjectStorageService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;

import java.util.List;

@RestController
@RequestMapping("/api/v1/streaming")
@RequiredArgsConstructor
public class StreamingController {
    private final AudioMetadataService metadataService;
    private final ObjectStorageService objectStorageService;

    private final int maxChunkSizeBytes = 1024 * 1024;

    // Реализация стриминга на Range запросах
    // Пример запроса диапазона от 0 до 999 байт
    // GET http://localhost:8080/api/v1/audio/stream/1
    // Headers:
    // Range: bytes=0-999
    @GetMapping(value = "/stream/{id}", produces = {"audio/flac", "audio/mpeg", "audio/ogg", "audio/vorbis"})
    public ResponseEntity<Resource> streamAudio(
            @PathVariable Long id,
            @RequestHeader HttpHeaders headers) {
        try {
            var metadata = metadataService.findMetadataById(id);
            long contentLength = metadata.contentLength();
            List<HttpRange> ranges = headers.getRange();

            if (ranges.isEmpty()) {
                InputStreamResource resource = objectStorageService.loadStreamResource(
                        "audio", metadata.objectPath(), 0L, (long) maxChunkSizeBytes);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("audio/flac"))
                        .contentLength(contentLength)
                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                        .body(resource);
            } else {
                HttpRange range = ranges.get(0);
                long start = range.getRangeStart(contentLength);
                long end = range.getRangeEnd(contentLength);
                long rangeLength = Math.min(end - start + 1, maxChunkSizeBytes);

                InputStreamResource resource = objectStorageService.loadStreamResource(
                        "audio", metadata.objectPath(), start, rangeLength);

                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                        .contentType(MediaType.parseMediaType("audio/flac"))
                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                        .header(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + contentLength)
                        .contentLength(rangeLength)
                        .body(resource);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
