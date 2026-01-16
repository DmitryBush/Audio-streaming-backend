package com.bush.search.event.kafka.index.handler;

import com.bush.search.domain.index.ChangeDataEvent;

public interface CdcEventHandler<T> {
    void handle(ChangeDataEvent<T> changeDataEvent);
}
