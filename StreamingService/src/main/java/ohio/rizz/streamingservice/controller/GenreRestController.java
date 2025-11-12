package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.GenreReadDto;
import ohio.rizz.streamingservice.service.genre.GenreService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreRestController {
    private final GenreService genreService;

    private final PagedResourcesAssembler<GenreReadDto> assembler;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreReadDto> findGenreById(@PathVariable Short id) {
        try {
            return new ResponseEntity<>(genreService.getGenreById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<GenreReadDto>>> findAllGenres(@PageableDefault(size = 15) Pageable pageable) {
        var genres = assembler.toModel(genreService.findAllGenres(pageable));
        return ResponseEntity.ok(genres);
    }
}
