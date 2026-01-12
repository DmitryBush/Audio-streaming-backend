package ohio.rizz.streamingservice.Repositories;

import ohio.rizz.streamingservice.Entities.outbox.OutboxMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxMetadataRepository extends JpaRepository<OutboxMetadata, UUID> {
}
