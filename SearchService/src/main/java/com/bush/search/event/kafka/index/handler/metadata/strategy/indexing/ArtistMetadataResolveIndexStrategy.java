package com.bush.search.event.kafka.index.handler.metadata.strategy.indexing;

import com.bush.search.domain.index.ArtistPayload;
import com.bush.search.domain.index.service.CrudOperationConstants;
import com.bush.search.event.kafka.index.handler.strategy.crud.CrudOperationStrategyRegistry;
import com.bush.search.event.kafka.index.handler.strategy.ResolveIndexStrategy;
import com.bush.search.service.metadata.artist.ArtistService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArtistMetadataResolveIndexStrategy implements ResolveIndexStrategy {
    private final ObjectMapper objectMapper;
    private final CrudOperationStrategyRegistry crudOperationStrategyRegistry;

    @Override
    public boolean isProcessingSupported(String objectName) {
        return objectName.equals("artist");
    }

    @Override
    public void indexObject(String jsonPayload, CrudOperationConstants crudOperationConstantsType) {
        try {
            ArtistPayload artistPayload = objectMapper.readValue(jsonPayload, ArtistPayload.class);
            crudOperationStrategyRegistry.processStrategy(artistPayload, crudOperationConstantsType, ArtistService.class);
        } catch (JsonProcessingException e) {
            log.error("An error has occurred while parsing JSON - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
