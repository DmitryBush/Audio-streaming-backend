package com.bush.playlist.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;

@Configuration
public class PlaylistLiquibaseConfig {
    @Bean
    public SpringLiquibase playlistLiquibase(@Value("${liquibase.playlist.change-log}") String changelog,
                                             @Autowired @Qualifier("playlistDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        System.out.println(dataSource);

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changelog);
        liquibase.setShouldRun(true);
        liquibase.setResourceLoader(new DefaultResourceLoader());
        liquibase.setContexts("playlist");

        return liquibase;
    }
}
