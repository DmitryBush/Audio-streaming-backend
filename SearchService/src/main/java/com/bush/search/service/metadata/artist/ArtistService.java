package com.bush.search.service.metadata.artist;

import com.bush.search.domain.document.Artist;
import com.bush.search.domain.index.ArtistPayload;
import com.bush.search.repository.ArtistRepository;
import com.bush.search.service.metadata.artist.mapper.ArtistCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    private final ArtistCreateMapper artistCreateMapper;

    public void createArtist(ArtistPayload artistPayload) {
        Optional.of(artistPayload)
                .map(artistCreateMapper::mapToArtist)
                .map(artistRepository::save);
    }

    public Artist getArtistById(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(NoSuchElementException::new);
    }
}
