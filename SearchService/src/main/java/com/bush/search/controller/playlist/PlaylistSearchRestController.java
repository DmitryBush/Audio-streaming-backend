package com.bush.search.controller.playlist;

import com.bush.search.domain.dto.playlist.PlaylistSearchResultDto;
import com.bush.search.service.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search/playlists")
public class PlaylistSearchRestController {
    private final PlaylistService playlistService;

    @GetMapping("/name")
    public ResponseEntity<List<PlaylistSearchResultDto>> findByNameContaining(String name) {
        return ResponseEntity.ok(playlistService.findByNameContaining(name));
    }
}
