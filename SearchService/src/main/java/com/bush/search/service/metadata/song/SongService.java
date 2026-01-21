package com.bush.search.service.metadata.song;

import com.bush.search.domain.dto.metadata.SongSearchResultDto;
import com.bush.search.domain.index.SongPayload;
import com.bush.search.repository.SongRepository;
import com.bush.search.service.metadata.song.mapper.SongCreateMapper;
import com.bush.search.service.metadata.song.mapper.SongReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    private final SongCreateMapper songCreateMapper;
    private final SongReadMapper songReadMapper;

    public void createSong(SongPayload songPayload) {
        Optional.of(songPayload)
                .map(songCreateMapper::mapToSong)
                .map(songRepository::save);
    }

    public void updateSong(Long songId, SongPayload songPayload) {
        Optional.of(songId)
                .map(String::valueOf)
                .flatMap(songRepository::findById)
                .map(song -> songCreateMapper.mapToSong(songPayload))
                .map(songRepository::save);
    }

    public void deleteSong(Long songId) {
        Optional.of(songId)
                .map(String::valueOf)
                .flatMap(songRepository::findById)
                .ifPresent(songRepository::delete);
    }

    public List<SongSearchResultDto> findByNameContaining(String name) {
        return songRepository.findByNameContainingIgnoreCase(name).stream()
                .map(songReadMapper::mapToSongSearchResultDto)
                .toList();
    }
}
