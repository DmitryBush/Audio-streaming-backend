package com.bush.search.event.kafka.index.handler.metadata;

import com.bush.search.domain.index.ChangeDataEvent;
import com.bush.search.event.kafka.index.handler.CdcEventHandler;
import com.bush.search.event.kafka.index.handler.strategy.ResolveIndexStrategyRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

@KafkaListener(topics = "search-metadata.public.outbox_service_table")
@RequiredArgsConstructor
public class MetadataCdcEventHandler implements CdcEventHandler<String> {
    private final ResolveIndexStrategyRegistry resolveIndexStrategyRegistry;

    @Override
    public void handle(ChangeDataEvent<String> changeDataEvent) {
        resolveIndexStrategyRegistry.resolveIndexStrategy(changeDataEvent.payload(), changeDataEvent.operationType(),
                changeDataEvent.objectName());
    }
}
