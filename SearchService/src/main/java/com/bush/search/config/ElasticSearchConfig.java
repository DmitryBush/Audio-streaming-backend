package com.bush.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.bush.search.repository")
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    @Value("spring.elastic.url")
    private String elasticUrl;
    @Value("spring.elastic.username")
    private String username;
    @Value("spring.elastic.password")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticUrl)
                .withBasicAuth(username, password)
                .build();
    }
}
