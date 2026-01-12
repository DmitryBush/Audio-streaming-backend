package com.bush.outbox.service.mapper;

import com.bush.outbox.domain.dto.OutboxRecordDto;
import com.bush.outbox.domain.entity.OutboxRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutboxCreateMapper {
    @Mapping(target = "payload", source = "payload", qualifiedByName = "convertToJsonPayload")
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OutboxRecord mapToOutboxMetadata(OutboxRecordDto<?> outboxMetadataDto);

    @Named("convertToJsonPayload")
    default String convertToJsonPayload(Object payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(payload);
    }
}
