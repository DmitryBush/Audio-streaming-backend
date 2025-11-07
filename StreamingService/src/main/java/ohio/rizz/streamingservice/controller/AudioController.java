package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.SongDto;
import ohio.rizz.streamingservice.dto.SongReadDto;
import ohio.rizz.streamingservice.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/audio")
@RequiredArgsConstructor
public class AudioController {
    private final UploadService uploadService;

    @PostMapping("/upload")
    public ResponseEntity<SongReadDto> uploadAudio(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(uploadService.uploadFile(file), HttpStatus.OK);
    }
}
