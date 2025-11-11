package ohio.rizz.streamingservice.service.artist;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Artist;
import ohio.rizz.streamingservice.Repositories.ArtistRepository;
import ohio.rizz.streamingservice.dto.ArtistDto;
import ohio.rizz.streamingservice.dto.ArtistReadDto;
import ohio.rizz.streamingservice.service.artist.mapper.ArtistCreateMapper;
import ohio.rizz.streamingservice.service.artist.mapper.ArtistReadMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    private final ArtistCreateMapper artistCreateMapper;
    private final ArtistReadMapper artistReadMapper;

    public ArtistReadDto createArtist(ArtistDto artistDto) {
        return artistRepository
                .findByName(artistDto.name())
                .map(artistReadMapper::mapToArtistReadMapper)
                .orElseGet(() -> Optional
                        .of(artistDto)
                        .map(artistCreateMapper::mapToArtist)
                        .map(artistRepository::save)
                        .map(artistReadMapper::mapToArtistReadMapper)
                        .orElseThrow());
    }

    public Artist getReferenceById(Long id) {
        return artistRepository.getReferenceById(id);
    }

    public ArtistReadDto findArtistById(Long id) {
        return artistRepository.findById(id)
                .map(artistReadMapper::mapToArtistReadMapper)
                .orElseThrow(NoSuchElementException::new);
    }
}
