package ohio.rizz.streamingservice.service.metadata;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.Entities.Artist;
import ohio.rizz.streamingservice.Repositories.SongRepository;
import ohio.rizz.streamingservice.dto.SongDto;
import ohio.rizz.streamingservice.dto.SongReadDto;
import ohio.rizz.streamingservice.service.metadata.mapper.SongCreateMapper;
import ohio.rizz.streamingservice.service.metadata.mapper.SongReadMapper;
import ohio.rizz.streamingservice.service.storage.StorageService;
import ohio.rizz.streamingservice.service.type.ContentTypeService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    private final SongCreateMapper songCreateMapper;
    private final SongReadMapper songReadMapper;

    public SongReadDto createSong(SongDto songDto, Album album, String objectReference) {
        return songRepository.findByName(songDto.name())
                .map(songReadMapper::mapToSongReadDto)
                .orElseGet(() -> Optional.of(songDto)
                        .map(songCreateMapper::mapToSong)
                        .map(song -> {
                            song.setArtist(album.getArtist());
                            song.setAlbum(album);
                            song.setFileUrl(objectReference);
                            return song;
                        })
                        .map(songRepository::save)
                        .map(songReadMapper::mapToSongReadDto)
                        .orElseThrow());
    }
}
