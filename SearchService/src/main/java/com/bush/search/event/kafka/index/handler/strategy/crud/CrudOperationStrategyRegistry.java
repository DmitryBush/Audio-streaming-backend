package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.outbox.domain.entity.CrudOperationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrudOperationStrategyRegistry {
    private final List<CrudOperationStrategy> crudOperationStrategies;

    public <T, S> void processStrategy(T payload, CrudOperationType operationType, Class<S> requiredService) {
        crudOperationStrategies.stream()
                .filter(crudOperationStrategy ->
                        crudOperationStrategy.isProcessingSupport(operationType, requiredService, payload.getClass()))
                .findFirst()
                .ifPresentOrElse(crudOperationStrategy -> crudOperationStrategy.process(payload),
                        () -> {
                            log.error("There is no handler for {} and  {} operation type", requiredService, operationType);
                            throw new IllegalArgumentException("There is no handler for " + requiredService
                                    + "and " + operationType + " operation type");
                        });
    }
}
