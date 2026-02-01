package ohio.rizz.streamingservice.it.service.song;

import com.redis.testcontainers.RedisContainer;
import ohio.rizz.streamingservice.dto.AlbumDto;
import ohio.rizz.streamingservice.dto.AlbumReadDto;
import ohio.rizz.streamingservice.dto.ArtistDto;
import ohio.rizz.streamingservice.dto.ArtistReadDto;
import ohio.rizz.streamingservice.dto.ArtworkDto;
import ohio.rizz.streamingservice.dto.GenreDto;
import ohio.rizz.streamingservice.dto.GenreReadDto;
import ohio.rizz.streamingservice.dto.song.SongDto;
import ohio.rizz.streamingservice.dto.song.SongReadDto;
import ohio.rizz.streamingservice.dto.song.SongStreamingMetadataDto;
import ohio.rizz.streamingservice.service.album.AlbumService;
import ohio.rizz.streamingservice.service.artist.ArtistService;
import ohio.rizz.streamingservice.service.genre.GenreService;
import ohio.rizz.streamingservice.service.song.AudioMetadataService;
import ohio.rizz.streamingservice.service.song.SongService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.UUID;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class AudioMetadataServiceIT {
    @Autowired
    private AudioMetadataService audioMetadataService;

    @Autowired
    private GenreService genreService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;

    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:18.0-alpine");

    @Container
    static MinIOContainer minIOContainer = new MinIOContainer("minio/minio:RELEASE.2025-09-07T16-13-09Z")
            .withUserName("minioadmin")
            .withPassword("minioadmin");

    @Container
    static RedisContainer redisContainer = new RedisContainer("redis:8.2-alpine");

    @DynamicPropertySource
    static void dynamicProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.port", () -> redisContainer.getMappedPort(6379));
    }

    @Transactional
    @Test
    public void createMetadata() {
        byte[] artworkData = new byte[] {1, 0, 1};
        ArtworkDto artworkDto = new ArtworkDto(UUID.nameUUIDFromBytes("art".getBytes()).toString(), artworkData);
        GenreDto genreDto = new GenreDto("example");
        ArtistDto artist = new ArtistDto("example");
        AlbumDto albumDto = new AlbumDto("example", LocalDate.now(), (short) 1, genreDto, artworkDto);
        SongDto songDto = new SongDto("example", (short) 1, 17, (short) 1,
                artworkDto.objectStorageLink(), artist, albumDto);

        GenreReadDto genreReadDto = genreService.createGenre(genreDto);
        ArtistReadDto artistReadDto = artistService.createArtist(artist);
        AlbumReadDto albumReadDto = albumService.createAlbum(albumDto,
                artistService.getReferenceById(artistReadDto.id()), genreService.getReferenceById(genreReadDto.id()));

        SongReadDto songReadDto = songService.createSong(songDto, albumService.getReferenceById(albumReadDto.id()));
        SongStreamingMetadataDto metadataDto = new SongStreamingMetadataDto(songReadDto.id(), 152L,
                "audio/flac", UUID.nameUUIDFromBytes("example".getBytes()).toString());

        SongStreamingMetadataDto songStreamingMetadataDto = audioMetadataService.createSongMetadata(metadataDto);

        Assertions.assertEquals(songStreamingMetadataDto, audioMetadataService.findMetadataById(metadataDto.id()));
    }
}
