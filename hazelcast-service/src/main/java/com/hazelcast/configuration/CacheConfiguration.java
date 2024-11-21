package com.hazelcast.configuration;

import com.hazelcast.cache.JdbcCacheManager;
import com.hazelcast.repository.CustomRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final CustomRepository customRepository;

    public CacheConfiguration(CustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    @Bean
    public CacheManager cacheManager() {
        return new JdbcCacheManager(customRepository);
    }

}
