package com.bush.search.service.metadata.genre;

import com.bush.search.domain.document.Genre;
import com.bush.search.domain.index.GenrePayload;
import com.bush.search.repository.GenreRepository;
import com.bush.search.service.metadata.genre.mapper.GenreCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    private final GenreCreateMapper genreCreateMapper;

    public void createGenre(GenrePayload genrePayload) {
        Optional.of(genrePayload)
                .map(genreCreateMapper::mapToGenre)
                .map(genreRepository::save);
    }

    public Genre getGenreById(Short genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(NoSuchElementException::new);
    }
}
