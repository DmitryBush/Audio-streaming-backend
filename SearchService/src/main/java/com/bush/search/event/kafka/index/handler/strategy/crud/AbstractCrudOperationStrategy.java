package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.search.domain.index.service.CrudOperationConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCrudOperationStrategy<S, P> implements CrudOperationStrategy {
    private final CrudOperationConstants crudOperationConstantsType;
    private final Class<S> requiredService;
    private final Class<P> payloadClazz;

    @Override
    public boolean isProcessingSupport(CrudOperationConstants crudOperationConstantsType, Class<?> requiredService, Class<?> payloadClazz) {
        return crudOperationConstantsType.equals(this.crudOperationConstantsType) && requiredService.equals(this.requiredService)
                && payloadClazz.equals(this.payloadClazz);
    }

    @Override
    public void process(Object payload) {
        if (payloadClazz.isAssignableFrom(payload.getClass())) {
            P castedPayload = payloadClazz.cast(payload);
            processInternal(castedPayload);
        }
    }

    /**
     * Processes the transferred object according to the logic defined in a specific inheritor class
     * @param payload Target object
     */
    protected abstract void processInternal(P payload);
}
