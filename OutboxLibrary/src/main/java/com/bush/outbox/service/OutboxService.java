package com.bush.outbox.service;

import com.bush.outbox.domain.dto.OutboxRecordDto;
import com.bush.outbox.repository.OutboxRepository;
import com.bush.outbox.service.mapper.OutboxCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OutboxService {
    private final OutboxRepository outboxMetadataRepository;

    private final OutboxCreateMapper outboxCreateMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public <T> T createRecord(OutboxRecordDto<T> outboxMetadataDto) {
        Optional.of(outboxMetadataDto)
                .map(outboxCreateMapper::mapToOutboxMetadata)
                .map(outboxMetadataRepository::save);
        return outboxMetadataDto.payload();
    }
}
