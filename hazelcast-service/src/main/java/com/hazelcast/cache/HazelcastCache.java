package com.hazelcast.cache;

import com.hazelcast.map.IMap;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class HazelcastCache implements Cache {

    private final IMap<String, Object> map;

    public HazelcastCache(IMap<String, Object> map) {
        this.map = map;
    }

    @Override
    public String getName() {
        return map.getName();
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public ValueWrapper get(Object key) {
        return map.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return map.get(key.toString());
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        map.putIfAbsent(key.toString(),value,10000, TimeUnit.SECONDS);
    }

    @Override
    public void evict(Object key) {

    }

    @Override
    public void clear() {

    }
}
