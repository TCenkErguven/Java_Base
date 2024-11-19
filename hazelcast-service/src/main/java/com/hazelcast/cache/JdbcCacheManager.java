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
        IMap<String, Object> map = hazelcastInstance.getMap(name);
        // Return the cache by name, or create it if it doesn't exist

        return new HazelcastCache(map);
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
