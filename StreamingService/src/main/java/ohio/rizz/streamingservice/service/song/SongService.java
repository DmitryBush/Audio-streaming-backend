package ohio.rizz.streamingservice.service.song;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Album;
import ohio.rizz.streamingservice.Entities.Song;
import ohio.rizz.streamingservice.Repositories.SongRepository;
import ohio.rizz.streamingservice.dto.song.SongDto;
import ohio.rizz.streamingservice.dto.song.SongReadDto;
import ohio.rizz.streamingservice.service.song.mapper.SongCreateMapper;
import ohio.rizz.streamingservice.service.song.mapper.SongReadMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    private final SongCreateMapper songCreateMapper;
    private final SongReadMapper songReadMapper;

    public SongReadDto createSong(SongDto songDto, Album album) {
        return songRepository.findByName(songDto.name())
                .map(songReadMapper::mapToSongReadDto)
                .orElseGet(() -> Optional.of(songDto)
                        .map(songCreateMapper::mapToSong)
                        .map(song -> {
                            song.setArtist(album.getArtist());
                            song.setAlbum(album);
                            return song;
                        })
                        .map(songRepository::save)
                        .map(songReadMapper::mapToSongReadDto)
                        .orElseThrow());
    }

    public Page<SongReadDto> findAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable)
                .map(songReadMapper::mapToSongReadDto);
    }

    public SongReadDto findById(long id) {
        return songRepository.findById(id)
                .map(songReadMapper::mapToSongReadDto)
                .orElseThrow(() -> new NoSuchElementException("Песня не найдена"));
    }

    public Song getReferenceById(Long id) {
        return songRepository.getReferenceById(id);
    }
}
