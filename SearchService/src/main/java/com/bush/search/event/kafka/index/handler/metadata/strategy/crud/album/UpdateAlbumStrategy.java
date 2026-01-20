package com.bush.search.event.kafka.index.handler.metadata.strategy.crud.album;

import com.bush.search.domain.index.AlbumPayload;
import com.bush.search.domain.index.service.Operation;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.metadata.album.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateAlbumStrategy extends AbstractCrudOperationStrategy<AlbumService, AlbumPayload> {
    @Autowired
    private AlbumService albumService;

    public UpdateAlbumStrategy() {
        super(Operation.UPDATE, AlbumService.class, AlbumPayload.class);
    }

    @Override
    protected void processInternal(AlbumPayload payload) {
        albumService.updateAlbum(payload.id(), payload);
    }
}
