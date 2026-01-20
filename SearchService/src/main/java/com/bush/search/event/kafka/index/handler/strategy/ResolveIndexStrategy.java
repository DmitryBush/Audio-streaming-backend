package com.bush.search.event.kafka.index.handler.strategy;

import com.bush.search.domain.index.service.Operation;

public interface ResolveIndexStrategy {
    boolean isProcessingSupported(String objectName);

    void indexObject(String jsonPayload, Operation operationType);
}
