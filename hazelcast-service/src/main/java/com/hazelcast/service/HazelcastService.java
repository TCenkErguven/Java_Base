package com.hazelcast.service;

import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceSaveException;
import com.hazelcast.exception.HazelCastServiceUpdateException;
import com.hazelcast.model.Custom;
import com.hazelcast.server.proto.SaveRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class HazelcastService {
    private final CustomService customService;
    private final CacheService cacheService;
    private final String cacheName;

    public HazelcastService(CustomService customService, CacheService cacheService, @Value("${cache.cache-name}") String cacheName) {
        this.customService = customService;
        this.cacheService = cacheService;
        this.cacheName = cacheName;
    }

    // #TODO Logger will be added instead of system.out and Impl file configuration will be added soon...
    // #TODO Unit test will be added
    // #TODO Integration tests will be added
    // #TODO Load tests will be added
    // #TODO CacheManager null ise hazelcast-client ı initialize dene

    public void validateRequestUUID(SaveRequest request) throws InterruptedException {
        Custom existingCustom = findByTransactionalUUID(request.getUuid());

        if (existingCustom == null) {
            Custom custom = new Custom();
            custom.setTransactionUUID(request.getUuid());
            custom.setMessage(request.getMessage());
            save(custom);
        }
    }


    public Custom findByTransactionalUUID(String uuid) throws InterruptedException {
        Custom existingCustom = null;
        Cache cache = cacheService.getCache(cacheName);
        // Cache control for dto
        if (cache != null) {
            try {
                existingCustom = cache.get(uuid, Custom.class);
            } catch (Exception e) {
                System.out.println("Cache Error");
            }
        }

        // DB control for dto
        if (existingCustom == null) {
            existingCustom = customService.findByUUIdAndReturnDto(uuid);
        }

        if (existingCustom != null && !existingCustom.getIsProgressCompleted()){
            Thread.sleep(1000);
            findByTransactionalUUID(uuid);
        }

        return existingCustom;
    }


    /**
     * Save method has a @Retryable mechanism and @Recover mechanism
     * in case if caching fails. Fallback situation let it save on the
     * DB which fallback requests will be saved.
     *
     * @param custom
     * @return
     */


    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100, maxDelay = 1000), retryFor = HazelCastServiceSaveException.class)
    public Custom save(Custom custom) {
        /**
         * Dto will be saved by the cache first if it fails, it throws a RunTimeException
         * if this fail process continues 3 times in a row than @Recover will be activated
         * and will save the data to our DB
         */
        try {
            return cacheService.cacheableSave(custom);
        } catch (Exception e) {
            // Throw a RuntimeException or another exception to trigger retries
            throw new HazelCastServiceSaveException(ErrorType.INTERNAL_ERROR);
        }
    }

    @Recover
    public Custom fallbackSave(HazelCastServiceSaveException e, Custom custom) {
        return customService.save(custom);
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 100, maxDelay = 1000), retryFor = HazelCastServiceUpdateException.class)
    public Custom update(SaveRequest request) {
        try {
            Custom custom = findByTransactionalUUID(request.getUuid());
            return cacheService.cacheableUpdate(custom);
        } catch (Exception e) {
            throw new HazelCastServiceUpdateException(ErrorType.INTERNAL_ERROR);
        }
    }

    @Recover
    public Custom fallbackUpdate(HazelCastServiceUpdateException e, Custom custom) {
        return customService.update(custom);
    }

}
