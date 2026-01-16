package com.bush.search.event.kafka.index.handler.strategy;

import com.bush.outbox.domain.entity.CrudOperationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResolveIndexStrategyRegistry {
    private final List<ResolveIndexStrategy> resolveIndexStrategyList;

    public void resolveIndexStrategy(String jsonPayload, CrudOperationType operationType, String objectName) {
        resolveIndexStrategyList.stream()
                .filter(resolveIndexStrategy -> resolveIndexStrategy.isProcessingSupported(objectName))
                .findFirst()
                .ifPresentOrElse(strategy -> strategy.indexObject(jsonPayload, operationType),
                        () -> {
                            throw new IllegalArgumentException("There is no handler for this " +
                                    objectName + " object in bean registry");
                        });
    }
}
