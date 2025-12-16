package com.bush.playlist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.bush.playlist.repository",
        entityManagerFactoryRef = "playlistEntityManagerFactory",
        transactionManagerRef = "playlistTransactionManager"
)
public class PlaylistDbConfig {
    @Autowired
    private Environment environment;
    @Bean("playlistDataSource")
    @ConfigurationProperties("spring.datasource.playlist")
    public DataSource playlistDataSource(@Value("${spring.datasource.playlist.url}") String url,
                                         @Value("${spring.datasource.playlist.username}") String username,
                                         @Value("${spring.datasource.playlist.password}") String password,
                                         @Value("${spring.datasource.playlist.driver-class-name}") String driverName) {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverName)
                .build();
    }

    @Bean("playlistEntityManagerFactory")
    @ConfigurationProperties("spring.datasource.jpa")
    public LocalContainerEntityManagerFactoryBean
    playlistEntityManagerFactory(@Autowired @Qualifier("playlistDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.bush.playlist.entity");
        entityManagerFactoryBean.setPersistenceUnitName("playlist");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                environment.getProperty("spring.datasource.jpa.properties-hibernate.hbm2ddl-auto"));
        entityManagerFactoryBean.setJpaPropertyMap(properties);
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager playlistTransactionManager(
            @Qualifier("playlistEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryBean.getObject()));
    }
}
