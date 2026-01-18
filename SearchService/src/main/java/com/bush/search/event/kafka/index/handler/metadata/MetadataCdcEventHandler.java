package com.bush.search.event.kafka.index.handler.metadata;

import com.bush.search.domain.index.ChangeDataEvent;
import com.bush.search.event.kafka.index.handler.CdcEventHandler;
import com.bush.search.event.kafka.index.handler.strategy.ResolveIndexStrategyRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "search-metadata.public.outbox_service_table", groupId = "metadata-processing-group",
        containerFactory = "concurrentKafkaListenerContainerFactory")
@RequiredArgsConstructor
public class MetadataCdcEventHandler implements CdcEventHandler<String> {
    private final ResolveIndexStrategyRegistry resolveIndexStrategyRegistry;

    @KafkaHandler
    @Override
    public void handle(@Payload ChangeDataEvent<String> changeDataEvent) {
        resolveIndexStrategyRegistry.resolveIndexStrategy(changeDataEvent.payload(), changeDataEvent.operationType(),
                changeDataEvent.objectName());
    }
}
