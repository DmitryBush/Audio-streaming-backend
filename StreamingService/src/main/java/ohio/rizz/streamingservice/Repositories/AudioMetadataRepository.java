package ohio.rizz.streamingservice.Repositories;

import ohio.rizz.streamingservice.Entities.AudioMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioMetadataRepository extends JpaRepository<AudioMetadata, Long> {
}
