package ohio.rizz.streamingservice.service.album;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.Entities.Artist;
import ohio.rizz.streamingservice.Entities.Genre;
import ohio.rizz.streamingservice.Repositories.AlbumRepository;
import ohio.rizz.streamingservice.dto.AlbumDto;
import ohio.rizz.streamingservice.dto.AlbumReadDto;
import ohio.rizz.streamingservice.service.album.mapper.AlbumCreateMapper;
import ohio.rizz.streamingservice.service.album.mapper.AlbumReadMapper;
import ohio.rizz.streamingservice.service.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {
    private final AlbumRepository albumRepository;

    private final AlbumCreateMapper albumCreateMapper;
    private final AlbumReadMapper albumReadMapper;

    private final StorageService storageService;

    public AlbumReadDto createAlbum(AlbumDto albumDto, Artist artist, Genre genre) {
        return albumRepository.findByName(albumDto.name())
                .map(albumReadMapper::mapToAlbumReadDto)
                .orElseGet(() -> Optional.of(albumDto)
                        .map(albumCreateMapper::mapToAlbum)
                        .map(album -> {
                            album.setGenre(genre);
                            album.setArtist(artist);
                            return album;
                        })
                        .map(albumRepository::save)
                        .map(albumReadMapper::mapToAlbumReadDto)
                        .orElseThrow());
    }

    public Album getReferenceById(Long id) {
        return albumRepository.getReferenceById(id);
    }

    public AlbumReadDto findAlbumById(Long id) {
        return albumRepository.findById(id)
                .map(albumReadMapper::mapToAlbumReadDto)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<AlbumReadDto> findAlbumsByArtistId(Long artistId) {
        return albumRepository.findByArtistId(artistId).stream()
                .map(albumReadMapper::mapToAlbumReadDto)
                .toList();
    }

    public Resource getAlbumArtwork(Long id) {
        return albumRepository.findById(id)
                .map(album -> storageService.loadResource("art", album.getCoverArtUrl()))
                .orElseThrow(NoSuchElementException::new);
    }

    public Page<AlbumReadDto> findAllAlbums(Pageable pageable) {
        return albumRepository.findAll(pageable)
                .map(albumReadMapper::mapToAlbumReadDto);
    }
}
