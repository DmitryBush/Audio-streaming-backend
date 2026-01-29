package com.bush.search.event.kafka.index.handler;

import com.bush.search.domain.index.ChangeDataEvent;

/**
 * The interface used to handle cdc events coming from apache kafka
 * @param <T> Type of payload class
 */
public interface CdcEventHandler<T> {
    void handle(ChangeDataEvent<T> changeDataEvent);
}
