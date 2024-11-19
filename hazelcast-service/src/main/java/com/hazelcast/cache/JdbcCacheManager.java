package com.hazelcast.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

public class JdbcCacheManager implements CacheManager {
    private final HazelcastInstance hazelcastInstance;

    public JdbcCacheManager(HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public Cache getCache(@NotNull String name) {
        return new HazelcastCache(hazelcastInstance);
    }

    @Override
    public @NotNull Collection<String> getCacheNames() {
        return hazelcastInstance.getDistributedObjects()
                .stream()
                .filter(d -> d instanceof IMap)
                .map(d -> ((IMap<?, ?>) d).getName())
                .toList();
    }
}
