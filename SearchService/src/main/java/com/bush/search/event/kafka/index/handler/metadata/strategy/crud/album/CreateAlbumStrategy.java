package com.bush.search.event.kafka.index.handler.metadata.strategy.crud.album;

import com.bush.search.domain.index.AlbumPayload;
import com.bush.search.domain.index.service.CrudOperationConstants;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.metadata.album.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateAlbumStrategy extends AbstractCrudOperationStrategy<AlbumService, AlbumPayload> {
    @Autowired
    private AlbumService albumService;

    public CreateAlbumStrategy() {
        super(CrudOperationConstants.CREATE, AlbumService.class, AlbumPayload.class);
    }

    @Override
    protected void processInternal(AlbumPayload payload) {
        albumService.createAlbum(payload);
    }
}
