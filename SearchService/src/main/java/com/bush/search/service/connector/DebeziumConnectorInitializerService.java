package com.bush.search.service.connector;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebeziumConnectorInitializerService {
    private final DebeziumConnectorService debeziumConnectorService;

    @Value("${spring.debezium.request-body-path}")
    private String requestBodyPath;

    @EventListener(ApplicationReadyEvent.class)
    protected void initializeConnectorsAfterBoot() throws IOException {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] jsonRequestResources = patternResolver.getResources(requestBodyPath);
        for (Resource resource : jsonRequestResources) {
            File file = resource.getFile();
            if (resource.getFile().getName().endsWith(".json")) {
                createConnector(file.getName().substring(0, file.getName().indexOf('.')), resource);
            }
        }
    }

    private void createConnector(String connectorName, Resource jsonResource) throws IOException {
        if (!debeziumConnectorService.isConnectorExist(connectorName)) {
            debeziumConnectorService.registerConnector(jsonResource.getContentAsString(StandardCharsets.UTF_8));
            log.info("Registered connector {}", connectorName);
        }
    }
}
