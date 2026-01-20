package com.bush.search.event.kafka.index.handler.metadata.strategy.indexing;

import com.bush.search.domain.index.SongPayload;
import com.bush.search.domain.index.service.Operation;
import com.bush.search.event.kafka.index.handler.strategy.crud.CrudOperationStrategyRegistry;
import com.bush.search.event.kafka.index.handler.strategy.ResolveIndexStrategy;
import com.bush.search.service.metadata.song.SongService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SongMetadataResolveIndexStrategy implements ResolveIndexStrategy {
    private final ObjectMapper objectMapper;
    private final CrudOperationStrategyRegistry crudOperationStrategyRegistry;

    @Override
    public boolean isProcessingSupported(String objectName) {
        return objectName.equals("song");
    }

    @Override
    public void indexObject(String jsonPayload, Operation operationType) {
        try {
            SongPayload albumPayload = objectMapper.readValue(jsonPayload, SongPayload.class);
            crudOperationStrategyRegistry.processStrategy(albumPayload, operationType, SongService.class);
        } catch (JsonProcessingException e) {
            log.error("An error has occurred while parsing JSON - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
