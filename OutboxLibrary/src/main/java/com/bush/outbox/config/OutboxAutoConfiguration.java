package com.bush.outbox.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Objects;

@Configuration
@AutoConfigurationPackage(basePackages = "com.bush.outbox")
@ComponentScan(basePackages = "com.bush.outbox")
public class OutboxAutoConfiguration {
    @Autowired
    private Environment environment;

    @Bean(value = "outboxTransactionManager", defaultCandidate = false)
    @ConditionalOnMissingBean(name = "outboxTransactionManager")
    public PlatformTransactionManager outboxTransactionManager(BeanFactory beanFactory) {
        if (environment.containsProperty("outbox.transaction-manager-name")) {
            String transactionManagerName = Objects.requireNonNull(
                    environment.getProperty("outbox.transaction-manager-name"));
            return beanFactory.getBean(transactionManagerName, PlatformTransactionManager.class);
        }
        return beanFactory.getBean("transactionManager", PlatformTransactionManager.class);
    }
}
