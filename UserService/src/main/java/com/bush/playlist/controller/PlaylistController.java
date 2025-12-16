package com.bush.playlist.controller;

import com.bush.playlist.dto.PlaylistCreateDto;
import com.bush.playlist.dto.PlaylistReadDto;
import com.bush.playlist.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    private final PagedResourcesAssembler<Long> assembler;

    @PostMapping
    public ResponseEntity<PlaylistReadDto> createPlaylist(PlaylistCreateDto createDto) {
        return new ResponseEntity<>(playlistService.createPlaylistInformation(createDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistReadDto> updatePlaylist(@PathVariable Long id, PlaylistCreateDto createDto) {
        return ResponseEntity.ok(playlistService.updatePlaylistInformation(id, createDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylistInformation(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistReadDto> findPlaylistById(@PathVariable Long id) {
        return ResponseEntity.ok(playlistService.findPlaylistById(id));
    }

    @PostMapping("/{playlistId}/tracks")
    public ResponseEntity<Void> addTrackToPlaylist(@PathVariable Long playlistId, Long trackId) {
        playlistService.addTrackToPlaylist(playlistId, trackId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{playlistId}/tracks")
    public ResponseEntity<Void> deleteTrackFromPlaylist(@PathVariable Long playlistId, Long trackId) {
        playlistService.removeTrackFromPlaylist(playlistId, trackId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{playlistId}/tracks")
    public ResponseEntity<PagedModel<EntityModel<Long>>> findPlaylistTracks(@PathVariable Long playlistId, Pageable pageable) {
        PagedModel<EntityModel<Long>> tracks = assembler.toModel(playlistService.findPlaylistTracks(playlistId, pageable));
        return ResponseEntity.ok(tracks);
    }
}
