package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class StreamingController {
    SongService songService;

    @PostMapping("/stream")
    public void streamAudio() {
        System.out.println("Уээээ");
    }

    @PostMapping("/songs")
    public void listSongs() {
        System.out.println(songService.getAllSongs());
    }
}
