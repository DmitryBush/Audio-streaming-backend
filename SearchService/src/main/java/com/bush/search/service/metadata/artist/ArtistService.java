package com.bush.search.service.metadata.artist;

import com.bush.search.domain.document.Artist;
import com.bush.search.domain.dto.metadata.ArtistSearchResultDto;
import com.bush.search.domain.index.ArtistPayload;
import com.bush.search.repository.ArtistRepository;
import com.bush.search.service.metadata.artist.mapper.ArtistCreateMapper;
import com.bush.search.service.metadata.artist.mapper.ArtistReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    private final ArtistCreateMapper artistCreateMapper;
    private final ArtistReadMapper artistReadMapper;

    public void createArtist(ArtistPayload artistPayload) {
        Optional.of(artistPayload)
                .map(artistCreateMapper::mapToArtist)
                .map(artistRepository::save);
    }

    public Artist getArtistById(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(NoSuchElementException::new);
    }

    public void updateArtist(Long artistId, ArtistPayload artistPayload) {
        Optional.of(artistId)
                .flatMap(artistRepository::findById)
                .map(artist -> artistCreateMapper.mapToArtist(artistPayload))
                .map(artistRepository::save);
    }

    public void deleteArtist(Long artistId) {
        Optional.of(artistId)
                .flatMap(artistRepository::findById)
                .ifPresent(artistRepository::delete);
    }

    public List<ArtistSearchResultDto> findByNameContaining(String name) {
        return artistRepository.findByNameContainingIgnoreCase(name).stream()
                .map(artistReadMapper::mapToArtistSearchResultDto)
                .toList();
    }
}
