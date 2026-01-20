package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.search.domain.index.service.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCrudOperationStrategy<S, P> implements CrudOperationStrategy {
    private final Operation operationType;
    private final Class<S> requiredService;
    private final Class<P> payloadClazz;

    @Override
    public boolean isProcessingSupport(Operation operationType, Class<?> requiredService, Class<?> payloadClazz) {
        return operationType.equals(this.operationType) && requiredService.equals(this.requiredService)
                && payloadClazz.equals(this.payloadClazz);
    }

    @Override
    public void process(Object payload) {
        if (payloadClazz.isAssignableFrom(payload.getClass())) {
            P castedPayload = payloadClazz.cast(payload);
            processInternal(castedPayload);
        }
    }

    protected abstract void processInternal(P payload);
}
