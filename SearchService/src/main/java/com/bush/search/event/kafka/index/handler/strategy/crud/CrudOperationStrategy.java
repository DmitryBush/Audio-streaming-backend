package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.outbox.domain.entity.CrudOperationType;

public interface CrudOperationStrategy {
    boolean isProcessingSupport(CrudOperationType operationType, Class<?> requiredService, Class<?> payloadClazz);

    void process(Object payload);
}
