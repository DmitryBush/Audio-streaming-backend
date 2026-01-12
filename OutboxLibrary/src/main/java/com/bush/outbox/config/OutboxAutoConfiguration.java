package com.bush.outbox.config;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigurationPackage(basePackages = "com.bush.outbox")
@ComponentScan(basePackages = "com.bush.outbox")
public class OutboxAutoConfiguration {
}
