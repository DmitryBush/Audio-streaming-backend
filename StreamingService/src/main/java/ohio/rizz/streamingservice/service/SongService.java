package ohio.rizz.streamingservice.service;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.Entities.Song;
import ohio.rizz.streamingservice.Repositories.SongRepository;
import ohio.rizz.streamingservice.dto.SongDto;
import ohio.rizz.streamingservice.dto.SongReadDto;
import ohio.rizz.streamingservice.service.metadata.mapper.SongCreateMapper;
import ohio.rizz.streamingservice.service.metadata.mapper.SongReadMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song findById(long id) {
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Песня не найдена"));
    }
}
