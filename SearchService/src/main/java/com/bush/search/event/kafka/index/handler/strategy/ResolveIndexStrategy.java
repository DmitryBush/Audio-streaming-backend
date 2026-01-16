package com.bush.search.event.kafka.index.handler.strategy;

import com.bush.outbox.domain.entity.CrudOperationType;

public interface ResolveIndexStrategy {
    boolean isProcessingSupported(String objectName);

    void indexObject(String jsonPayload, CrudOperationType operationType);
}
