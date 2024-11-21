package com.hazelcast.cache;

import com.hazelcast.repository.CustomRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JdbcCacheManager implements CacheManager {

    private final CustomRepository customRepository;
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<>();

    public JdbcCacheManager(CustomRepository customRepository) {
        this.customRepository = customRepository;
    }

    @Override
    public Cache getCache(String name) {
        return caches.computeIfAbsent(name, this::createCache);
    }

    private Cache createCache(String name) {
        return new JdbcCache(name, customRepository);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(caches.keySet());
    }
}