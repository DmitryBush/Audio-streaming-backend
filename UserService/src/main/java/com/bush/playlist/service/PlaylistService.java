package com.bush.playlist.service;

import com.bush.playlist.dto.PlaylistCreateDto;
import com.bush.playlist.dto.PlaylistReadDto;
import com.bush.playlist.entity.PlaylistTracks;
import com.bush.playlist.entity.PlaylistTracksId;
import com.bush.playlist.repository.PlaylistRepository;
import com.bush.playlist.service.mapper.PlaylistCreateMapper;
import com.bush.playlist.service.mapper.PlaylistReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    private final PlaylistCreateMapper createMapper;
    private final PlaylistReadMapper readMapper;

    @Transactional("playlistTransactionManager")
    public PlaylistReadDto createPlaylistInformation(PlaylistCreateDto createDto) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.ofNullable(createDto)
                .map(createMapper::mapToPlaylist)
                .map(playlist -> {
                    playlist.setCreatorId(userId);
                    return playlist;
                })
                .map(playlistRepository::save)
                .map(readMapper::mapToPlaylistReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @Transactional("playlistTransactionManager")
    public PlaylistReadDto updatePlaylistInformation(Long playlistId, PlaylistCreateDto createDto) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return playlistRepository.findById(playlistId)
                .map(playlist -> {
                    if (!playlist.getCreatorId().equals(userId)) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                    }
                    playlist.setName(createDto.name());
                    return playlist;
                })
                .map(playlistRepository::saveAndFlush)
                .map(readMapper::mapToPlaylistReadDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional("playlistTransactionManager")
    public void deletePlaylistInformation(Long playlistId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional.ofNullable(playlistId)
                .map(playlistRepository::findById)
                .ifPresentOrElse(optionalPlaylist -> optionalPlaylist
                        .ifPresentOrElse(playlist -> {
                            if (!playlist.getCreatorId().equals(userId)) {
                                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                            }
                            playlistRepository.deleteById(playlist.getPlaylistId());
                        }, () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }), () -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                });
    }

    public Page<PlaylistReadDto> findAllUserPlaylists(String userId, Pageable pageable) {
        return playlistRepository.findByCreatorId(userId, pageable)
                .map(readMapper::mapToPlaylistReadDto);
    }

    @Transactional("playlistTransactionManager")
    public void addTrackToPlaylist(Long playlistId, Long trackId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        playlistRepository.findById(playlistId)
                .map(playlist -> {
                    if (!playlist.getCreatorId().equals(userId)) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                    }
                    playlist.getTracks().add(new PlaylistTracks(new PlaylistTracksId(playlist, trackId)));
                    return playlist;
                })
                .map(playlistRepository::saveAndFlush)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional("playlistTransactionManager")
    public void removeTrackFromPlaylist(Long playlistId, Long trackId) {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        playlistRepository.findById(playlistId)
                .map(playlist -> {
                    if (!playlist.getCreatorId().equals(userId)) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
                    }
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
