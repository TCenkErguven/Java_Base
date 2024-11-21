package com.hazelcast.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JdbcCacheManager implements CacheManager {
    private HazelcastInstance hazelcastInstance;

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();


    public JdbcCacheManager() {
    }

    public JdbcCacheManager(HazelcastInstance hazelcastInstance){
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public Cache getCache(@NotNull String name) {
        Cache cache = caches.get("name");
        if (cache == null) {
            IMap<Object, Object> map = hazelcastInstance.getMap(name);
            cache = new HazelcastCache(name);
            long cacheTimeout = calculateCacheReadTimeout(name);
            if (cacheTimeout > 0) {
                cache = new HazelcastCache(name, map, cacheTimeout);
            }


        }
    }



        private long calculateCacheReadTimeout(String name) {
            Long timeout = getReadTimeoutMap().get(name);
            return timeout == null ? defaultReadTimeout : timeout;
        }

}
