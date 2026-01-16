package com.bush.search.event.kafka.index.handler.metadata.strategy.crud.album;

import com.bush.outbox.domain.entity.CrudOperationType;
import com.bush.search.domain.index.AlbumPayload;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.metadata.album.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAlbumStrategy extends AbstractCrudOperationStrategy<AlbumService, AlbumPayload> {
    @Autowired
    private AlbumService albumService;

    public CreateAlbumStrategy() {
        super(CrudOperationType.C, AlbumService.class, AlbumPayload.class);
    }

    @Override
    protected void processInternal(AlbumPayload payload) {
        albumService.createAlbum(payload);
    }
}
