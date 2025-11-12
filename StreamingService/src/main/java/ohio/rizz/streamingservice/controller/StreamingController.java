package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Song;
import ohio.rizz.streamingservice.dto.SongReadDto;
import ohio.rizz.streamingservice.service.SongService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class StreamingController {
    private final SongService songService;

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
    public String songsPage(Model model) {
        List<SongReadDto> songs = songService.getAllSongs();
        model.addAttribute("songs", songs);
        return "songs";
    }

    @GetMapping("/songs")
    @ResponseBody
    public ResponseEntity<List<SongReadDto>> listSongs() {
        try {
            List<SongReadDto> songs = songService.getAllSongs();
            return ResponseEntity.ok(songs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
