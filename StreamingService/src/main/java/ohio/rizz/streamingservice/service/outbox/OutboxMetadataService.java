package ohio.rizz.streamingservice.service.outbox;

import lombok.RequiredArgsConstructor;
import ohio.rizz.streamingservice.Repositories.OutboxMetadataRepository;
import ohio.rizz.streamingservice.dto.outbox.OutboxMetadataDto;
import ohio.rizz.streamingservice.service.outbox.mapper.OutboxMetadataCreateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OutboxMetadataService {
    private final OutboxMetadataRepository outboxMetadataRepository;

    private final OutboxMetadataCreateMapper outboxMetadataCreateMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public <T> T createRecord(OutboxMetadataDto<T> outboxMetadataDto) {
        Optional.of(outboxMetadataDto)
                .map(outboxMetadataCreateMapper::mapToOutboxMetadata)
                .map(outboxMetadataRepository::save);
        return outboxMetadataDto.payload();
    }
}
