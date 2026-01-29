package com.bush.search.event.kafka.index.handler.strategy;

import com.bush.search.domain.index.service.CrudOperationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Register class responsible for defining and providing a strategy applicable to an target object with a specified scope
 */
@Component
@RequiredArgsConstructor
public class ResolveIndexStrategyRegistry {
    private final List<ResolveIndexStrategy> resolveIndexStrategyList;

    public void resolveIndexStrategy(String jsonPayload, CrudOperationConstants crudOperationConstantsType, String objectName) {
        resolveIndexStrategyList.stream()
                .filter(resolveIndexStrategy -> resolveIndexStrategy.isProcessingSupported(objectName))
                .findFirst()
                .ifPresentOrElse(strategy -> strategy.indexObject(jsonPayload, crudOperationConstantsType),
                        () -> {
                            throw new IllegalArgumentException("There is no handler for this " +
                                    objectName + " object in bean registry");
                        });
    }
}
