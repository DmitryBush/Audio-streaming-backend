package com.bush.playlist.config;

import com.bush.user.config.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                               @Value("${spring.cache.default-TTL}") Duration defaultTTL) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(defaultTTL)
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public RedisTemplate<String, String> blackListTokenRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> blackListTokenRedisTemplate = new RedisTemplate<>();
        blackListTokenRedisTemplate.setConnectionFactory(redisConnectionFactory);

        blackListTokenRedisTemplate.setKeySerializer(new StringRedisSerializer());
        blackListTokenRedisTemplate.setValueSerializer(new StringRedisSerializer());

        blackListTokenRedisTemplate.afterPropertiesSet();
        return blackListTokenRedisTemplate;
    }
}
