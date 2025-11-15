package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.SongReadDto;
import ohio.rizz.streamingservice.service.SongService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class StreamingController {
    private final SongService songService;
    private final PagedResourcesAssembler<SongReadDto> assembler;

    @GetMapping("/stream/{songId}")
    public ResponseEntity<Resource> streamAudio(@PathVariable Long songId) {

        // надо получить файл по id (ну либо оставить всегда говновоз, может юзерам понравится)
        Resource resource = new ClassPathResource("govn.mp3");

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("audio/mpeg"));
        headers.setCacheControl("no-cache, no-store, must-revalidate");
        headers.setPragma("no-cache");
        headers.setExpires(0);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
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
