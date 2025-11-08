package ohio.rizz.streamingservice.service;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Entities.Song;
import ohio.rizz.streamingservice.Repositories.SongRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    public Song findById(long id) {
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Песня не найдена"));
    }
}
