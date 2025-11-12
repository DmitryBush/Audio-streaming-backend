package ohio.rizz.streamingservice.service.genre;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Genre;
import ohio.rizz.streamingservice.Repositories.GenreRepository;
import ohio.rizz.streamingservice.dto.GenreDto;
import ohio.rizz.streamingservice.dto.GenreReadDto;
import ohio.rizz.streamingservice.service.genre.mapper.GenreCreateMapper;
import ohio.rizz.streamingservice.service.genre.mapper.GenreReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    private final GenreCreateMapper genreCreateMapper;
    private final GenreReadMapper genreReadMapper;

    public GenreReadDto createGenre(GenreDto genreDto) {
        return genreRepository
                .findByName(genreDto.name())
                .map(genreReadMapper::mapToGenreReadDto)
                .orElseGet(() -> Optional.of(genreDto)
                        .map(genreCreateMapper::mapToGenre)
                        .map(genreRepository::save)
                        .map(genreReadMapper::mapToGenreReadDto)
                        .orElseThrow());
    }

    public Genre getReferenceById(Short id) {
        return genreRepository.getReferenceById(id);
    }

    public GenreReadDto getGenreById(Short id) {
        return genreRepository.findById(id)
                .map(genreReadMapper::mapToGenreReadDto)
                .orElseThrow(NoSuchElementException::new);
    }

    public Page<GenreReadDto> findAllGenres(Pageable pageable) {
        return genreRepository.findAll(pageable)
                .map(genreReadMapper::mapToGenreReadDto);
    }
}
