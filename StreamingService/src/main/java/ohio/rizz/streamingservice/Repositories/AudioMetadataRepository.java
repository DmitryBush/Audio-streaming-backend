package ohio.rizz.streamingservice.Repositories;

import ohio.rizz.streamingservice.Entities.SongMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioMetadataRepository extends JpaRepository<SongMetadata, Long> {
}
