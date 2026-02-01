package ohio.rizz.streamingservice.it.service.artist;

import com.redis.testcontainers.RedisContainer;
import ohio.rizz.streamingservice.dto.ArtistDto;
import ohio.rizz.streamingservice.dto.ArtistReadDto;
import ohio.rizz.streamingservice.service.artist.ArtistService;
import ohio.rizz.streamingservice.service.storage.ObjectStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
public class ArtistServiceIT {
    @Autowired
    private ArtistService artistService;

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

    @Test
    public void testArtistCreate() {
        ArtistDto artistDto = new ArtistDto("example");

        ArtistReadDto artistReadDto = artistService.createArtist(artistDto);

        Assertions.assertEquals(artistReadDto, artistService.findArtistById(artistReadDto.id()));
    }
}
