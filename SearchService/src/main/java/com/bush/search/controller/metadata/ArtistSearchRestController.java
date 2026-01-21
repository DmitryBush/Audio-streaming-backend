package com.bush.search.controller.metadata;

import com.bush.search.domain.dto.metadata.ArtistSearchResultDto;
import com.bush.search.service.metadata.artist.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/artists")
public class ArtistSearchRestController {
    private final ArtistService artistService;

    @GetMapping("/name")
    public ResponseEntity<List<ArtistSearchResultDto>> findByNameContaining(String name) {
        return ResponseEntity.ok(artistService.findByNameContaining(name));
    }
}
