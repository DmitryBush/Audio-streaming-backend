package com.bush.search.controller.metadata;

import com.bush.search.domain.dto.metadata.GenreSearchResultDto;
import com.bush.search.service.metadata.genre.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search/genres")
@RequiredArgsConstructor
public class GenreSearchRestController {
    private final GenreService genreService;

    @GetMapping("/name")
    public ResponseEntity<List<GenreSearchResultDto>> findByNameContaining(String name) {
        return ResponseEntity.ok(genreService.findByNameContaining(name));
    }
}
