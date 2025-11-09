package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Song;
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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class StreamingController {
    private final SongService songService;

    @GetMapping("/stream/{songId}")
    public String streamAudio(@PathVariable Long songId) {
        try {
            Song song = songService.findById(songId);
            return "redirect:" + song.getFileUrl();
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @GetMapping("/song/{songId}")
    @ResponseBody
    public ResponseEntity<Song> getSong(@PathVariable Long songId) {
        try {
            Song song = songService.findById(songId);
            return ResponseEntity.ok(song);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/songs")
    @ResponseBody
    public ResponseEntity<List<Song>> listSongs() {
        try {
            List<Song> songs = songService.getAllSongs();
            return ResponseEntity.ok(songs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/songs-page")
    public String songsPage(Model model) {
        List<Song> songs = songService.getAllSongs();
        model.addAttribute("songs", songs);
        return "songs";
    }
}
