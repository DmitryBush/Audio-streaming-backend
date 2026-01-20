package com.bush.search.event.kafka.index.handler.metadata.strategy.crud.genre;

import com.bush.search.domain.index.GenrePayload;
import com.bush.search.domain.index.service.Operation;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.metadata.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreDeleteStrategy extends AbstractCrudOperationStrategy<GenreService, GenrePayload> {
    @Autowired
    private GenreService genreService;

    public GenreDeleteStrategy() {
        super(Operation.DELETE, GenreService.class, GenrePayload.class);
    }

    @Override
    protected void processInternal(GenrePayload payload) {
        genreService.deleteGenre(payload.id());
    }
}
