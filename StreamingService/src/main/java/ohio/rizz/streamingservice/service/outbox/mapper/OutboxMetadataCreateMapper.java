package ohio.rizz.streamingservice.service.outbox.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ohio.rizz.streamingservice.Entities.outbox.OutboxMetadata;
import ohio.rizz.streamingservice.dto.outbox.OutboxMetadataDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutboxMetadataCreateMapper {
    @Mapping(target = "payload", source = "payload", qualifiedByName = "convertToJsonPayload")
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OutboxMetadata mapToOutboxMetadata(OutboxMetadataDto<?> outboxMetadataDto);

    @Named("convertToJsonPayload")
    default String convertToJsonPayload(Object payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(payload);
    }
}
