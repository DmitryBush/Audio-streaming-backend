package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class StreamingController {
    @PostMapping("/stream")
    public void streamAudio() {
        System.out.println("Уээээ");
    }
}
