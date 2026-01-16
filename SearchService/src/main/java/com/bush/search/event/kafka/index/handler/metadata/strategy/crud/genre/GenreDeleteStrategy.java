package com.bush.search.event.kafka.index.handler.metadata.strategy.crud.genre;

import com.bush.outbox.domain.entity.CrudOperationType;
import com.bush.search.domain.index.GenrePayload;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.metadata.genre.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreDeleteStrategy extends AbstractCrudOperationStrategy<GenreService, GenrePayload> {
    @Autowired
    private GenreService genreService;

    public GenreDeleteStrategy() {
        super(CrudOperationType.D, GenreService.class, GenrePayload.class);
    }

    @Override
    protected void processInternal(GenrePayload payload) {

    }
}
