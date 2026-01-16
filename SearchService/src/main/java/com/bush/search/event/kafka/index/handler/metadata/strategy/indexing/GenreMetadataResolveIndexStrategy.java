package com.bush.search.event.kafka.index.handler.metadata.strategy.indexing;

import com.bush.search.domain.index.GenrePayload;
import com.bush.search.domain.index.service.Operation;
import com.bush.search.event.kafka.index.handler.strategy.crud.CrudOperationStrategyRegistry;
import com.bush.search.event.kafka.index.handler.strategy.ResolveIndexStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GenreMetadataResolveIndexStrategy implements ResolveIndexStrategy {
    private final ObjectMapper objectMapper;
    private final CrudOperationStrategyRegistry crudOperationStrategyRegistry;

    @Override
    public boolean isProcessingSupported(String objectName) {
        return objectName.equals("genre");
    }

    @Override
    public void indexObject(String jsonPayload, Operation operationType) {
        try {
            GenrePayload genrePayload = objectMapper.readValue(jsonPayload, GenrePayload.class);
            crudOperationStrategyRegistry.processStrategy(genrePayload, operationType, GenrePayload.class);
        } catch (JsonProcessingException e) {
            log.error("An error has occurred while parsing JSON - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
