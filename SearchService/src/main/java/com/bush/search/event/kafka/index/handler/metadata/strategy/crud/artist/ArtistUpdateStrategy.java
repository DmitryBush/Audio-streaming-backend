package com.bush.search.event.kafka.index.handler.metadata.strategy.crud.artist;

import com.bush.search.domain.index.ArtistPayload;
import com.bush.search.domain.index.service.CrudOperationConstants;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.metadata.artist.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistUpdateStrategy extends AbstractCrudOperationStrategy<ArtistService, ArtistPayload> {
    @Autowired
    private ArtistService artistService;

    public ArtistUpdateStrategy() {
        super(CrudOperationConstants.UPDATE, ArtistService.class, ArtistPayload.class);
    }

    @Override
    protected void processInternal(ArtistPayload payload) {
        artistService.updateArtist(payload.id(), payload);
    }
}
