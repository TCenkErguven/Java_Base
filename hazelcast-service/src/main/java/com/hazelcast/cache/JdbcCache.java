package com.hazelcast.cache;

import com.hazelcast.model.Custom;
import com.hazelcast.repository.CustomRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;

public class JdbcCache implements Cache {

    private final String name;
    private final CustomRepository customRepository;

    public JdbcCache(String name, CustomRepository customRepository) {
        this.name = name;
        this.customRepository = customRepository;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull Object getNativeCache() {
        return customRepository;
    }

    @Override
    public ValueWrapper get(@NotNull Object key) {
        return customRepository.findByUUId((String) key)
                .map(SimpleValueWrapper::new)
                .orElse(null);
    }

    @Override
    public <T> T get(@NotNull Object key, Class<T> type) {
        return customRepository.findByUUId((String) key)
                .map(custom -> type.cast(custom.getMessage()))
                .orElse(null);
    }

    @Override
    public <T> T get(@NotNull Object key, @NotNull Callable<T> valueLoader) {
        return customRepository.findByUUId((String) key)
                .map(custom -> {
                    try {
                        return valueLoader.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(null);
    }

    @Override
    public void put(@NotNull Object key, Object value) {
        Custom custom = new Custom();
        custom.setTransactionUUID((String) key);
        custom.setMessage(value);
        try{
            customRepository.save(custom);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void evict(@NotNull Object key) {
        customRepository.findByUUId((String) key).ifPresent(customRepository::delete);
    }

    @Override
    public void clear() {
        customRepository.deleteAll();
    }
}