package com.hazelcast.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class HazelcastCache implements Cache {

    private final HazelcastInstance hazelcastInstance;

    public HazelcastCache(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public @NotNull String getName() {
        return hazelcastInstance.getMap("custom-entity").getName();
    }

    @Override
    public Object getNativeCache() {
        return  hazelcastInstance.getMap("custom-entity");
    }

    @Override
    public ValueWrapper get(@NotNull Object key) {
        return (ValueWrapper) hazelcastInstance.getMap("custom-entity").get(key.toString());
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Object value = hazelcastInstance.getMap("custom-entity").get(key.toString());
        if(value == null){
            return null;
        }
        return type.cast(value);
    }

    @Override
    public <T> T get(@NotNull Object key, @NotNull Callable<T> valueLoader) {
        Object value = hazelcastInstance.getMap("custom-entity").get(key.toString());

        if (value == null) {
            try {
                value = valueLoader.call();
                IMap<String, Object> map = hazelcastInstance.getMap("custom-entity");
                map.putIfAbsent(key.toString(), value, 10000, TimeUnit.SECONDS);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load value for key: " + key, e);
            }
        }

         return (T) value;
    }

    @Override
    public void put(Object key, Object value) {
        hazelcastInstance.getMap("custom-entity").putIfAbsent(key.toString(),value);
    }

    @Override
    public void evict(Object key) {
        hazelcastInstance.getMap("custom-entity").evict(key.toString());
    }

    @Override
    public void clear() {
        hazelcastInstance.getMap("custom-entity").evictAll();
    }

}
