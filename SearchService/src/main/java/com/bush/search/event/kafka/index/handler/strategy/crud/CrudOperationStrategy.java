package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.search.domain.index.service.Operation;

public interface CrudOperationStrategy {
    boolean isProcessingSupport(Operation operationType, Class<?> requiredService, Class<?> payloadClazz);

    void process(Object payload);
}
