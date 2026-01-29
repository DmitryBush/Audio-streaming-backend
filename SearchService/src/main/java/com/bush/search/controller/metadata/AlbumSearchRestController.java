package com.bush.search.controller.metadata;

import com.bush.search.domain.dto.metadata.AlbumSearchResultDto;
import com.bush.search.service.metadata.album.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/albums")
@RequiredArgsConstructor
public class AlbumSearchRestController {
    private final AlbumService albumService;

    @GetMapping(value = "/name", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlbumSearchResultDto>> findByNameContaining(String name) {
        return ResponseEntity.ok(albumService.findByNameContaining(name));
    }
}
