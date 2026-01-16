package com.bush.search.service.metadata.album;

import com.bush.search.domain.document.Album;
import com.bush.search.domain.document.Artist;
import com.bush.search.domain.document.Genre;
import com.bush.search.domain.index.AlbumPayload;
import com.bush.search.repository.AlbumRepository;
import com.bush.search.service.metadata.album.mapper.AlbumCreateMapper;
import com.bush.search.service.metadata.artist.ArtistService;
import com.bush.search.service.metadata.genre.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    private final GenreService genreService;
    private final ArtistService artistService;

    private final AlbumCreateMapper albumCreateMapper;

    public void createAlbum(AlbumPayload albumPayload) {
        Genre genre = genreService.getGenreById(albumPayload.genrePayload());
        Artist artist = artistService.getArtistById(albumPayload.artistPayload());
        Optional.of(albumPayload)
                .map(albumCreateMapper::mapToAlbum)
                .map(album -> albumCreateMapper.mapExternalDocuments(album, artist, genre))
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
}
