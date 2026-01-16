package com.bush.search.event.kafka.index.handler.metadata.strategy.crud.song;

import com.bush.outbox.domain.entity.CrudOperationType;
import com.bush.search.domain.index.SongPayload;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.metadata.song.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SongUpdateStrategy extends AbstractCrudOperationStrategy<SongService, SongPayload> {
    @Autowired
    private SongService service;

    public SongUpdateStrategy() {
        super(CrudOperationType.U, SongService.class, SongPayload.class);
    }

    @Override
    protected void processInternal(SongPayload payload) {
        service.updateSong(payload.songId(), payload);
    }
}
