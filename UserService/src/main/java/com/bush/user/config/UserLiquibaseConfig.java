package com.bush.user.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;

@Configuration
public class UserLiquibaseConfig {
    @Bean("userLiquibase")
    public SpringLiquibase userLiquibase(@Value("${liquibase.user.change-log}") String changeLog,
                                         @Autowired @Qualifier("userDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLog);
        liquibase.setShouldRun(true);
        liquibase.setResourceLoader(new DefaultResourceLoader());
        liquibase.setContexts("user");
        return liquibase;
    }
}
