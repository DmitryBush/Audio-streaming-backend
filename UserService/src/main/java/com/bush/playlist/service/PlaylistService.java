package com.bush.playlist.service;

import com.bush.playlist.dto.PlaylistCreateDto;
import com.bush.playlist.dto.PlaylistReadDto;
import com.bush.playlist.entity.PlaylistTracks;
import com.bush.playlist.entity.PlaylistTracksId;
import com.bush.playlist.repository.PlaylistRepository;
import com.bush.playlist.service.mapper.PlaylistCreateMapper;
import com.bush.playlist.service.mapper.PlaylistReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    private final PlaylistCreateMapper createMapper;
    private final PlaylistReadMapper readMapper;

    @Transactional("playlistTransactionManager")
    public PlaylistReadDto createPlaylistInformation(PlaylistCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(createMapper::mapToPlaylist)
                .map(playlistRepository::save)
                .map(readMapper::mapToPlaylistReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @Transactional("playlistTransactionManager")
    public PlaylistReadDto updatePlaylistInformation(Long id, PlaylistCreateDto createDto) {
        return playlistRepository.findById(id)
                .map(playlist -> {
                    playlist.setName(createDto.name());
                    return playlist;
                })
                .map(playlistRepository::saveAndFlush)
                .map(readMapper::mapToPlaylistReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional("playlistTransactionManager")
    public void deletePlaylistInformation(Long playlistId) {
        Optional.ofNullable(playlistId)
                .ifPresentOrElse(id -> {
                    if (!playlistRepository.existsById(id)) {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                    }
                    playlistRepository.deleteById(id);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }, () ->  { throw new ResponseStatusException(HttpStatus.BAD_REQUEST); });
    }

    public Page<PlaylistReadDto> findAllUserPlaylists(String userId, Pageable pageable) {
        return playlistRepository.findByCreatorId(userId, pageable)
                .map(readMapper::mapToPlaylistReadDto);
    }

    @Transactional("playlistTransactionManager")
    public void addTrackToPlaylist(Long playlistId, Long trackId) {
        playlistRepository.findById(playlistId)
                .map(playlist -> {
                    playlist.getTracks().add(new PlaylistTracks(new PlaylistTracksId(playlist, trackId)));
                    return playlist;
                })
                .map(playlistRepository::saveAndFlush)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional("playlistTransactionManager")
    public void removeTrackFromPlaylist(Long playlistId, Long trackId) {
        playlistRepository.findById(playlistId)
                .map(playlist -> {
                    playlist.getTracks().remove(new PlaylistTracks(new PlaylistTracksId(playlist, trackId)));
                    return playlist;
                })
                .map(playlistRepository::saveAndFlush)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public PlaylistReadDto findPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId)
                .map(readMapper::mapToPlaylistReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Page<Long> findPlaylistTracksById(Long playlistId, Pageable pageable) {
        return playlistRepository.findTracksByPlaylistId(playlistId, pageable)
                .map(playlistTracks -> playlistTracks.getId().getTrackId());
    }
}
