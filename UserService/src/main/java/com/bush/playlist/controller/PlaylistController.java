package com.bush.playlist.controller;

import com.bush.playlist.dto.PlaylistCreateDto;
import com.bush.playlist.dto.PlaylistReadDto;
import com.bush.playlist.dto.PlaylistTrackDto;
import com.bush.playlist.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/playlists")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

    private final PagedResourcesAssembler<PlaylistTrackDto> trackAssembler;
    private final PagedResourcesAssembler<PlaylistReadDto> playlistAssembler;

    @PostMapping
    public ResponseEntity<PlaylistReadDto> createPlaylist(@RequestBody PlaylistCreateDto createDto) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(playlistService.createPlaylistInformation(createDto, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<PlaylistReadDto> updatePlaylist(@PathVariable Long playlistId,
                                                          @RequestBody PlaylistCreateDto createDto) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(playlistService.updatePlaylistInformation(playlistId, createDto, userId));
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long playlistId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        playlistService.deletePlaylistInformation(playlistId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistReadDto> findPlaylistById(@PathVariable Long playlistId) {
        return ResponseEntity.ok(playlistService.findPlaylistById(playlistId));
    }

    @PostMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<Void> addTrackToPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId) {
        playlistService.addTrackToPlaylist(playlistId, trackId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<Void> deleteTrackFromPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId) {
        playlistService.removeTrackFromPlaylist(playlistId, trackId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{playlistId}/tracks")
    public ResponseEntity<PagedModel<EntityModel<PlaylistTrackDto>>> findPlaylistTracks(@PathVariable Long playlistId, Pageable pageable) {
        PagedModel<EntityModel<PlaylistTrackDto>> tracks = trackAssembler.toModel(playlistService.findPlaylistTracksById(playlistId, pageable));
        return ResponseEntity.ok(tracks);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PlaylistReadDto>>> findUserPlaylists(UserDetails userDetails,
                                                                                      Pageable pageable) {
        PagedModel<EntityModel<PlaylistReadDto>> playlists =
                playlistAssembler.toModel(playlistService.findAllUserPlaylists(userDetails.getUsername(), pageable));
        return ResponseEntity.ok(playlists);
    }
}
