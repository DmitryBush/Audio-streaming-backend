package ohio.rizz.streamingservice.controller;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.dto.AlbumReadDto;
import ohio.rizz.streamingservice.service.album.AlbumService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
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
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
public class AlbumRestController {
    private final AlbumService albumService;

    private final PagedResourcesAssembler<AlbumReadDto> assembler;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumReadDto> findAlbumById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(albumService.findAlbumById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/artwork", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<Resource> getAlbumArtwork(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(albumService.getAlbumArtwork(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AlbumReadDto>>> findAlbums(@PageableDefault(size = 15) Pageable pageable) {
        var albums = assembler.toModel(albumService.findAllAlbums(pageable));
        return ResponseEntity.ok(albums);
    }
}
