package ohio.rizz.streamingservice.Repositories;

import ohio.rizz.streamingservice.Entities.SongStreamingMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioMetadataRepository extends JpaRepository<SongStreamingMetadata, Long> {
}
