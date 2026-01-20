package com.bush.search.event.kafka.index.handler.metadata.strategy.indexing;

import com.bush.search.domain.index.AlbumPayload;
import com.bush.search.domain.index.service.Operation;
import com.bush.search.event.kafka.index.handler.strategy.crud.CrudOperationStrategyRegistry;
import com.bush.search.event.kafka.index.handler.strategy.ResolveIndexStrategy;
import com.bush.search.service.metadata.album.AlbumService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlbumMetadataResolveIndexStrategy implements ResolveIndexStrategy {
    private final ObjectMapper objectMapper;
    private final CrudOperationStrategyRegistry crudOperationStrategyRegistry;

    @Override
    public boolean isProcessingSupported(String objectName) {
        return objectName.equals("album");
    }

    @Override
    public void indexObject(String jsonPayload, Operation operationType) {
        try {
            AlbumPayload albumPayload = objectMapper.readValue(jsonPayload, AlbumPayload.class);
            crudOperationStrategyRegistry.processStrategy(albumPayload, operationType, AlbumService.class);
        } catch (JsonProcessingException e) {
            log.error("An error has occurred while parsing JSON - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
