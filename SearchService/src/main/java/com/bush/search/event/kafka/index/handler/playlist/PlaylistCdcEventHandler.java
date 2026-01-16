package com.bush.search.event.kafka.index.handler.playlist;

import com.bush.search.domain.index.ChangeDataEvent;
import com.bush.search.domain.index.PlaylistPayload;
import com.bush.search.event.kafka.index.handler.CdcEventHandler;
import org.springframework.kafka.annotation.KafkaListener;

@KafkaListener(topics = "search-playlist.public.outbox_service_table")
public class PlaylistCdcEventHandler implements CdcEventHandler<PlaylistPayload> {
    @Override
    public void handle(ChangeDataEvent<PlaylistPayload> changeDataEvent) {

    }
}
