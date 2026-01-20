package com.bush.search.service.connector;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebeziumConnectorInitializerService {
    private final DebeziumConnectorService debeziumConnectorService;
    private final ObjectMapper objectMapper;

    @Value("${spring.debezium.request-body-path}")
    private String requestBodyPath;

    @Value("${spring.datasource.metadata.name}")
    private String metadataDatabaseName;
    @Value("${spring.datasource.metadata.username}")
    private String metadataDatabaseUsername;
    @Value("${spring.datasource.metadata.password}")
    private String metadataDatabasePassword;

    @Value("${spring.datasource.playlist.name}")
    private String playlistDatabaseName;
    @Value("${spring.datasource.playlist.username}")
    private String playlistDatabaseUsername;
    @Value("${spring.datasource.playlist.password}")
    private String playlistDatabasePassword;

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
            debeziumConnectorService.registerConnector(getJsonStringConnectorConfiguration(jsonResource));
            log.info("Registered connector {}", connectorName);
        }
    }

    private String getJsonStringConnectorConfiguration(Resource jsonResource) throws IOException {
        String resourceContentString = jsonResource.getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> jsonConnectorConfigObjectMap = objectMapper.readValue(resourceContentString,
                new TypeReference<>() {});

        Map<String, String> connectorConfigurationMap = objectMapper.convertValue(jsonConnectorConfigObjectMap.get("config"),
                new TypeReference<>() {});
        String databaseName = connectorConfigurationMap.get("database.dbname");
        if (metadataDatabaseName.equals(databaseName)) {
            resourceContentString = resourceContentString.replace("$POSTGRES_USERNAME", metadataDatabaseUsername);
            return resourceContentString.replace("$POSTGRES_PASSWORD", metadataDatabasePassword);
        } else if (playlistDatabaseName.equals(databaseName)) {
            resourceContentString = resourceContentString.replace("$POSTGRES_USERNAME", playlistDatabaseUsername);
            return resourceContentString.replace("$POSTGRES_PASSWORD", playlistDatabasePassword);
        }
        log.error("Unknown database name: {}", databaseName);
        throw new IllegalArgumentException("Unknown database name");
    }
}
