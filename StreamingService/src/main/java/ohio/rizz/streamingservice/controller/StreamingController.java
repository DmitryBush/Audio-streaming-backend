package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.AudioMetadata;
import ohio.rizz.streamingservice.dto.song.SongReadDto;
import ohio.rizz.streamingservice.service.song.AudioMetadataService;
import ohio.rizz.streamingservice.service.song.SongService;
import ohio.rizz.streamingservice.service.storage.ObjectStorageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class StreamingController {
    private final SongService songService;
    private final AudioMetadataService metadataService;
    private final ObjectStorageService objectStorageService;
    private final PagedResourcesAssembler<SongReadDto> assembler;

    private final int maxChunkSizeBytes = 1024 * 1024;

    // Реализация стриминга на Range запросах
    // Пример запроса диапазона от 0 до 999 байт
    // GET http://localhost:8080/api/v1/audio/stream/1
    // Headers:
    // Range: bytes=0-999
    @GetMapping("/stream/{songId}")
    public ResponseEntity<Resource> streamAudio(
            @PathVariable Long songId,
            @RequestHeader HttpHeaders headers) {
        try {
            var metadata = metadataService.findMetadataById(songId);
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

    @GetMapping("/song/{songId}")
    @ResponseBody
    public ResponseEntity<SongReadDto> getSong(@PathVariable Long songId) {
        try {
            SongReadDto song = songService.findById(songId);
            return ResponseEntity.ok(song);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/songs-list")
    public String songsPage(Model model, Pageable pageable) {
        Page<SongReadDto> songs = songService.findAllSongs(pageable);
        model.addAttribute("songs", songs);
        return "songs";
    }

    @GetMapping("/songs")
    @ResponseBody
    public ResponseEntity<PagedModel<EntityModel<SongReadDto>>> listSongs(@PageableDefault(size = 15) Pageable pageable) {
        try {
            Page<SongReadDto> songs = songService.findAllSongs(pageable);
            return ResponseEntity.ok(assembler.toModel(songs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
