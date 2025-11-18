package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.AlbumReadDto;
import ohio.rizz.streamingservice.dto.ArtistReadDto;
import ohio.rizz.streamingservice.service.album.AlbumService;
import ohio.rizz.streamingservice.service.artist.ArtistService;
import org.springframework.data.domain.Page;
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

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistRestController {
    private final ArtistService artistService;
    private final AlbumService albumService;

    private final PagedResourcesAssembler<ArtistReadDto> assembler;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistReadDto> findArtistById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(artistService.findArtistById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlbumReadDto>> findAlbumsByArtistId(@PathVariable Long id) {
        return new ResponseEntity<>(albumService.findAlbumsByArtistId(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ArtistReadDto>>> findAllArtists(@PageableDefault(size = 15) Pageable pageable) {
        var artists = assembler.toModel(artistService.findAllArtists(pageable));
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }
}
