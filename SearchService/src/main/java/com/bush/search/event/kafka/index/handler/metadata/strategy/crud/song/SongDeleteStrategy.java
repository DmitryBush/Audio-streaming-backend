package com.bush.search.event.kafka.index.handler.metadata.strategy.crud.song;

import com.bush.outbox.domain.entity.CrudOperationType;
import com.bush.search.domain.index.SongPayload;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.metadata.song.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SongDeleteStrategy extends AbstractCrudOperationStrategy<SongService, SongPayload> {
    @Autowired
    private SongService service;

    public SongDeleteStrategy() {
        super(CrudOperationType.D, SongService.class, SongPayload.class);
    }

    @Override
    protected void processInternal(SongPayload payload) {
        service.deleteSong(payload.songId());
    }
}
