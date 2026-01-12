package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.song.SongReadDto;
import ohio.rizz.streamingservice.service.UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/uploads")
@RequiredArgsConstructor
public class UploadController {
    private final UploadService uploadService;

    @PostMapping(
            value = "/uploadSong",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SongReadDto> uploadAudio(@RequestBody MultipartFile file) {
        return new ResponseEntity<>(uploadService.uploadFile(file), HttpStatus.OK);
    }

}
