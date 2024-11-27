package com.hazelcast.cache;

import com.hazelcast.core.OperationTimeoutException;
import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceException;
import com.hazelcast.internal.util.ExceptionUtil;
import com.hazelcast.map.IMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HazelcastCustomCache implements Cache {

    private final IMap<Object, Object> map;

    @Setter
    @Getter
    private long readTimeout;

    public HazelcastCustomCache(IMap<Object, Object> map) {
        this.map = map;
    }

    @Override
    public String getName() {
        return map.getName();
    }

    @Override
    public Object getNativeCache() {
        return map;
    }

    @Override
    public ValueWrapper get(Object key) {
        if (key == null) {
            return null;
        }
        Object value = lookup(key);
        return value != null ? new SimpleValueWrapper(value) : null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        Object value = lookup(key);
        if (type != null && value != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = lookup(key);
        if (value != null) {
            return (T) value;
        } else {
            this.map.lock(key);
            try {
                value = lookup(key);
                if (value != null) {
                    return (T) value;
                } else {
                    return loadValue(key, valueLoader);
                }
            } finally {
                this.map.unlock(key);
            }
        }
    }

    private <T> T loadValue(Object key, Callable<T> valueLoader) {
        T value;
        try {
            value = valueLoader.call();
        } catch (Exception ex) {
            throw new HazelCastServiceException(ErrorType.NOT_FOUND);
        }
        put(key, value);
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        if (key != null || value != null) {
            map.putIfAbsent(key, value,1000, TimeUnit.SECONDS);
        }
    }

    @Override
    public void evict(Object key) {
        if(key != null){
            map.delete(key);
        }
    }

    @Override
    public void clear() {
        map.clear();
    }

    private Object lookup(Object key) {
        if (readTimeout > 0) {
            try {
                return this.map.getAsync(key).toCompletableFuture().get(readTimeout, TimeUnit.MILLISECONDS);
            } catch (TimeoutException te) {
                throw new OperationTimeoutException(te.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw ExceptionUtil.rethrow(e);
            } catch (Exception e) {
                throw ExceptionUtil.rethrow(e);
            }
        }
        return this.map.get(key);
    }

}
