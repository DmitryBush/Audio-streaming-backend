package com.bush.search.service.metadata.genre;

import com.bush.search.domain.document.Genre;
import com.bush.search.domain.dto.metadata.GenreSearchResultDto;
import com.bush.search.domain.index.GenrePayload;
import com.bush.search.repository.GenreRepository;
import com.bush.search.service.metadata.genre.mapper.GenreCreateMapper;
import com.bush.search.service.metadata.genre.mapper.GenreReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    private final GenreCreateMapper genreCreateMapper;
    private final GenreReadMapper genreReadMapper;

    public void createGenre(GenrePayload genrePayload) {
        Optional.of(genrePayload)
                .map(genreCreateMapper::mapToGenre)
                .map(genreRepository::save);
    }

    public Genre getGenreById(Short genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(NoSuchElementException::new);
    }

    public void updateGenre(Short genreId, GenrePayload genrePayload) {
        Optional.of(genreId)
                .flatMap(genreRepository::findById)
                .map(genre -> genreCreateMapper.mapToGenre(genrePayload))
                .map(genreRepository::save);
    }

    public void deleteGenre(Short genreId) {
        Optional.of(genreId)
                .flatMap(genreRepository::findById)
                .ifPresent(genreRepository::delete);
    }

    public List<GenreSearchResultDto> findByNameContaining(String name) {
        return genreRepository.findByNameContainingIgnoreCase(name).stream()
                .map(genreReadMapper::mapToGenreSearchResultDto)
                .toList();
    }
}
