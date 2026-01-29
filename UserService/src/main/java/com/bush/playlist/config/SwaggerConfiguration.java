package com.bush.playlist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI(BuildProperties buildProperties) {
        return new OpenAPI()
                .info(new Info().title("User service").version(buildProperties.getVersion()))
                .servers(List.of(new Server().url("http://localhost:8081").description("local server")));
    }
}
