package com.bush.search.event.kafka.index.handler.playlist.strategy.crud;

import com.bush.search.domain.index.PlaylistPayload;
import com.bush.search.domain.index.service.Operation;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.playlist.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaylistUpdateStrategy extends AbstractCrudOperationStrategy<PlaylistService, PlaylistPayload> {
    @Autowired
    private PlaylistService playlistService;

    public PlaylistUpdateStrategy() {
        super(Operation.UPDATE, PlaylistService.class, PlaylistPayload.class);
    }

    @Override
    protected void processInternal(PlaylistPayload payload) {
        playlistService.updatePlaylist(payload.playlistId(), payload);
    }
}
