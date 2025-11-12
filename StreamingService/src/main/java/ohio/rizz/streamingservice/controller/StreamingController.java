package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.SongReadDto;
import ohio.rizz.streamingservice.service.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class StreamingController {
    private final SongService songService;
    private final PagedResourcesAssembler<SongReadDto> assembler;

    @GetMapping("/stream/{songId}")
    public String streamAudio(@PathVariable Long songId) {
        try {
            SongReadDto song = songService.findById(songId);
            return "redirect:";
        } catch (Exception e) {
            return "redirect:/error";
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
