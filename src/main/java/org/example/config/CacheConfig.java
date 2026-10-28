package org.example.config;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/** Configuração de cache local e Redis por perfil. */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Profile("!redis")
    @ConditionalOnMissingBean(CacheManager.class)
    public CacheManager localCacheManager() {
        return new ConcurrentMapCacheManager(
            "priceAnalytics",
            "priceComparisons",
            "products",
            "stores",
            "dashboardSummary"
        );
    }

    @Bean
    @Profile("redis")
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(30));
        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(configuration)
            .build();
    }
}


