package com.bush.user.config;

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
        basePackages = "com.bush.user.repository",
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager"
)
public class UserDbConfig {
    @Autowired
    private Environment environment;
    @Bean("userDataSource")
    @ConfigurationProperties("spring.datasource.user")
    public DataSource userDataSource(@Value("${spring.datasource.user.url}") String url,
                                     @Value("${spring.datasource.user.username}") String username,
                                     @Value("${spring.datasource.user.password}") String password,
                                     @Value("${spring.datasource.user.driver-class-name}") String driverName) {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverName)
                .build();
    }

    @Bean("userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    userEntityManagerFactory(@Autowired @Qualifier("userDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.bush.user.entity");
        entityManagerFactoryBean.setPersistenceUnitName("user");

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        HashMap<String, Object> jpaPropertiesMap = new HashMap<>();
        jpaPropertiesMap.put("hibernate.hbm2ddl.auto",
                environment.getProperty("spring.datasource.jpa.properties-hibernate.hbm2ddl-auto"));
        entityManagerFactoryBean.setJpaPropertyMap(jpaPropertiesMap);
        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("userEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }
}
