package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.search.domain.index.service.CrudOperationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrudOperationStrategyRegistry {
    private final List<CrudOperationStrategy> crudOperationStrategies;

    public <T, S> void processStrategy(T payload, CrudOperationConstants crudOperationConstantsType, Class<S> requiredService) {
        crudOperationStrategies.stream()
                .filter(crudOperationStrategy ->
                        crudOperationStrategy.isProcessingSupport(crudOperationConstantsType, requiredService, payload.getClass()))
                .findFirst()
                .ifPresentOrElse(crudOperationStrategy -> crudOperationStrategy.process(payload),
                        () -> {
                            log.error("There is no handler for {} and  {} operation type", requiredService, crudOperationConstantsType);
                            throw new IllegalArgumentException("There is no handler for " + requiredService
                                    + "and " + crudOperationConstantsType + " operation type");
                        });
    }
}
