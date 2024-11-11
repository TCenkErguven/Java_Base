package org.hazelcast.service;

import org.hazelcast.dto.SaveRequestDto;
import org.hazelcast.exception.ErrorType;
import org.hazelcast.exception.HazelCastServiceException;
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

    public HazelcastService(CustomService customService, CacheService cacheService, @Value("${cache.cache-name}") String cacheName){
        this.customService = customService;
        this.cacheService = cacheService;
        this.cacheName = cacheName;
    }

    // #TODO Logger will be added instead of system.out and Impl file configuration will be added soon...
    // #TODO Unit test will be added
    // #TODO Integration tests will be added
    // #TODO Load tests will be added
    // #TODO CacheManager null ise hazelcast-client ı initialize dene

    public SaveRequestDto findResponseByUUID(String uuid){
        Cache cache = cacheService.getCache(cacheName);
        SaveRequestDto existingDto = null;

        // Cache control for dto
        if(cache != null){
            try{
                existingDto = cache.get(uuid, SaveRequestDto.class);
            } catch (Exception e) {
                System.out.println("Cache Error");
            }
        }

        // DB control for dto
        if(existingDto == null){
            existingDto = customService.findByUUId(uuid);
        }

        if( existingDto == null) {
            throw new HazelCastServiceException(ErrorType.NOT_FOUND);
        }

        return existingDto;
    }


    /**
     * Save method has a @Retryable mechanism and @Recover mechanism
     * in case if caching fails. Fallback situation let it save on the
     * DB which fallback requests will be saved.
     * @param dto
     * @return
     */


    @Retryable(maxAttempts = 3, backoff=@Backoff(delay=100, maxDelay=1000), retryFor = HazelCastServiceException.class)
    public SaveRequestDto save(SaveRequestDto dto) {
        /**
         * Dto will be saved by the cache first if it fails, it throws a RunTimeException
         * if this fail process continues 3 times in a row than @Recover will be activated
         * and will save the data to our DB
         */
        try {
            return cacheService.cacheableSave(dto);
        } catch (Exception e) {
            // Throw a RuntimeException or another exception to trigger retries
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
    }

    @Recover
    public SaveRequestDto fallbackSave(HazelCastServiceException e, SaveRequestDto dto){
        return customService.save(dto);
    }

    /*
    @Retryable(maxAttempts = 3, backoff=@Backoff(delay=100, maxDelay=1000), retryFor = HazelCastServiceException.class)
    public SaveRequestDto saveWithTTL(SaveRequestDto dto) {
        /**
         * Dto will be saved by the cache first if it fails, it throws a Exception
         * if this fail process continues 3 times in a row than @Recover will be activated
         * and will save the data to our DB
         */
        /*
        try{
            return cacheService.hazelCastSave(dto);
        } catch(Exception e){
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
       }
        */



}
