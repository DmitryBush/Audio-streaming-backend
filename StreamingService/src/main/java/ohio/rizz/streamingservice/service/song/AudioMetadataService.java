package ohio.rizz.streamingservice.service.song;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Repositories.AudioMetadataRepository;
import ohio.rizz.streamingservice.dto.song.AudioMetadataDto;
import ohio.rizz.streamingservice.service.song.mapper.MetadataMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AudioMetadataService {
    private final AudioMetadataRepository metadataRepository;

    private final MetadataMapper metadataMapper;

    public AudioMetadataDto createSongMetadata(AudioMetadataDto createDto) {
        return Optional.ofNullable(createDto)
                .map(metadataMapper::mapToAudioMetadata)
                .map(metadataRepository::save)
                .map(metadataMapper::mapToAudioMetadataDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Cacheable(key = "#id", cacheNames = "metadata")
    public AudioMetadataDto findMetadataById(Long id) {
        return metadataRepository.findById(id)
                .map(metadataMapper::mapToAudioMetadataDto)
                .orElseThrow(NoSuchElementException::new);
    }
}
