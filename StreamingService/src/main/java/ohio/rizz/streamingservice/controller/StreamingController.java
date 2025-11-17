package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.song.SongReadDto;
import ohio.rizz.streamingservice.service.song.SongService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class StreamingController {
    private final SongService songService;
    private final PagedResourcesAssembler<SongReadDto> assembler;

    private final int maxChunkSizeBytes = 1024 * 1024;

    // Реализация стриминга на Range запросах
    // Пример запроса диапазона от 0 до 999 байт
    // GET http://localhost:8080/api/v1/audio/stream/1
    // Headers:
    // Range: bytes=0-999
    @GetMapping("/stream/{songId}")
    public ResponseEntity<ResourceRegion> streamAudio(
            @PathVariable Long songId,
            @RequestHeader HttpHeaders headers) {

        try {
            // Тут нужно искать файл по id (ну либо оставить всегда говновоз, может юзерам понравится)
            Resource resource = new ClassPathResource("govn.mp3");
            long contentLength = resource.contentLength();

            List<HttpRange> ranges = headers.getRange();
            HttpRange range = ranges.isEmpty() ? null : ranges.get(0);

            ResourceRegion region;
            if (range != null) {
                long start = range.getRangeStart(contentLength);
                long end = range.getRangeEnd(contentLength);
                long rangeLength = Math.min(maxChunkSizeBytes, end - start + 1);
                region = new ResourceRegion(resource, start, rangeLength);
            } else {
                // Если диапазон не указан, возвращаем первый чанк
                long chunkSize = Math.min(maxChunkSizeBytes, contentLength);
                region = new ResourceRegion(resource, 0, chunkSize);
            }

            return ResponseEntity.status(range != null ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                    .contentType(MediaType.parseMediaType("audio/mpeg"))
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .body(region);

        } catch (IOException e) {
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
