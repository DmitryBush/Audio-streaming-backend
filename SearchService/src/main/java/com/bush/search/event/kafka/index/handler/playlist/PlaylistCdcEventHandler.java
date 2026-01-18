package com.bush.search.event.kafka.index.handler.playlist;

import com.bush.search.domain.index.ChangeDataEvent;
import com.bush.search.domain.index.PlaylistPayload;
import com.bush.search.event.kafka.index.handler.CdcEventHandler;
import com.bush.search.event.kafka.index.handler.strategy.crud.CrudOperationStrategyRegistry;
import com.bush.search.service.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "search-playlist.public.outbox_service_table", groupId = "playlist-processing-group",
        containerFactory = "concurrentKafkaListenerContainerFactory")
@RequiredArgsConstructor
public class PlaylistCdcEventHandler implements CdcEventHandler<PlaylistPayload> {
    private final CrudOperationStrategyRegistry crudOperationStrategyRegistry;

    @KafkaHandler
    @Override
    public void handle(@Payload ChangeDataEvent<PlaylistPayload> changeDataEvent) {
        crudOperationStrategyRegistry.processStrategy(changeDataEvent.payload(), changeDataEvent.operationType(),
                PlaylistService.class);
    }
}
