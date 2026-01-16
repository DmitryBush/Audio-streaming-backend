package com.bush.search.service.metadata.song;

import com.bush.search.domain.index.SongPayload;
import com.bush.search.repository.SongRepository;
import com.bush.search.service.metadata.song.mapper.SongCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    private final SongCreateMapper songCreateMapper;

    public void createSong(SongPayload songPayload) {
        Optional.of(songPayload)
                .map(songCreateMapper::mapToSong)
                .map(songRepository::save);
    }

    public void updateSong(Long songId, SongPayload songPayload) {
        Optional.of(songId)
                .flatMap(songRepository::findById)
                .map(song -> songCreateMapper.mapToSong(songPayload))
                .map(songRepository::save);
    }

    public void deleteSong(Long songId) {
        Optional.of(songId)
                .flatMap(songRepository::findById)
                .ifPresent(songRepository::delete);
    }
}
