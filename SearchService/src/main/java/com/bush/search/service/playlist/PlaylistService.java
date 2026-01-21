package com.bush.search.service.playlist;

import com.bush.search.domain.dto.playlist.PlaylistSearchResultDto;
import com.bush.search.domain.index.PlaylistPayload;
import com.bush.search.repository.PlaylistRepository;
import com.bush.search.service.playlist.mapper.PlaylistCreateMapper;
import com.bush.search.service.playlist.mapper.PlaylistReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    private final PlaylistCreateMapper playlistCreateMapper;
    private final PlaylistReadMapper playlistReadMapper;

    public void createPlaylist(PlaylistPayload playlistPayload) {
        Optional.of(playlistPayload)
                .map(playlistCreateMapper::mapToPlaylist)
                .map(playlistRepository::save);
    }

    public void updatePlaylist(Long playlistId, PlaylistPayload playlistPayload) {
        Optional.of(playlistId)
                .flatMap(playlistRepository::findById)
                .map(playlist -> playlistCreateMapper.mapToPlaylist(playlistPayload))
                .map(playlistRepository::save);
    }

    public void deletePlaylist(Long playlistId) {
        Optional.of(playlistId)
                .flatMap(playlistRepository::findById)
                .ifPresent(playlistRepository::delete);
    }

    public List<PlaylistSearchResultDto> findByNameContaining(String name) {
        return playlistRepository.findByNameContainingIgnoreCase(name).stream()
                .map(playlistReadMapper::mapToPlaylistSearchResultDto)
                .toList();
    }
}
