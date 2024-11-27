package com.hazelcast.cache;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HazelcastCustomCacheManager implements CacheManager {

    private final HazelcastInstance hazelcastInstance;

    public HazelcastCustomCacheManager(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();


    @Override
    public Cache getCache(String name) {
        Cache cache = caches.get(name);
        if(cache == null){
            IMap<Object, Object> map = hazelcastInstance.getMap(name);
            cache = new HazelcastCustomCache(map);
        //    long cacheTimeout = calculateCacheReadTimeout(name);
        //    ((HazelcastCache) cache).setReadTimeout(cacheTimeout);
            Cache currentCache = caches.putIfAbsent(name, cache);
            if (currentCache != null) {
                cache = currentCache;
            }
        }
        return cache;
    }

    @Override
    public @NotNull Collection<String> getCacheNames() {
        Set<String> cacheNames = new HashSet<String>();
        Collection<DistributedObject> distributedObjects = hazelcastInstance.getDistributedObjects();
        for (DistributedObject distributedObject : distributedObjects) {
            if (distributedObject instanceof IMap<?, ?> map) {
                //IMap instance add
                cacheNames.add(map.getName());
            }
        }
        return cacheNames;
    }

}
