package com.bush.search.controller.metadata;

import com.bush.search.domain.dto.metadata.SongSearchResultDto;
import com.bush.search.service.metadata.song.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/songs")
@RequiredArgsConstructor
public class SongSearchRestController {
    private final SongService songService;

    @GetMapping("/name")
    public ResponseEntity<List<SongSearchResultDto>> findByNameContaining(String name) {
        return ResponseEntity.ok(songService.findByNameContaining(name));
    }
}
