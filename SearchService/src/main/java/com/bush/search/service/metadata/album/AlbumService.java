package com.bush.search.service.metadata.album;

import com.bush.search.domain.document.Album;
import com.bush.search.domain.dto.metadata.AlbumSearchResultDto;
import com.bush.search.domain.index.AlbumPayload;
import com.bush.search.repository.AlbumRepository;
import com.bush.search.service.metadata.album.mapper.AlbumCreateMapper;
import com.bush.search.service.metadata.album.mapper.AlbumReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    private final AlbumCreateMapper albumCreateMapper;
    private final AlbumReadMapper albumReadMapper;

    public void createAlbum(AlbumPayload albumPayload) {
        Optional.of(albumPayload)
                .map(albumCreateMapper::mapToAlbum)
                .map(albumRepository::save);
    }

    public Album getAlbumById(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(NoSuchElementException::new);
    }

    public void updateAlbum(Long albumId, AlbumPayload albumPayload) {
        Optional.of(albumId)
                .flatMap(albumRepository::findById)
                .map(album -> albumCreateMapper.mapToAlbum(albumPayload))
                .map(albumRepository::save);
    }

    public void deleteAlbum(Long albumId) {
        Optional.of(albumId)
                .flatMap(albumRepository::findById)
                .ifPresent(albumRepository::delete);
    }

    public List<AlbumSearchResultDto> findByNameContaining(String name) {
        return albumRepository.findByNameContainingIgnoreCase(name).stream()
                .map(albumReadMapper::mapToAlbumSearchResultDto)
                .toList();
    }
}
