package org.example.service;

import org.example.dto.SaveRequestDto;
import org.example.exception.ErrorType;
import org.example.exception.HazelCastServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class HazelcastService {
    private final DBService dbService;
    private final CacheService cacheService;
    private final String cacheName;

    public HazelcastService(DBService dbService, CacheService cacheService, @Value("${cache.cache-name}") String cacheName){
        this.dbService = dbService;
        this.cacheService = cacheService;
        this.cacheName = cacheName;
    }

    // #TODO Logger will be added instead of system.out and Impl file configuration will be added soon...
    // #TODO Unit test will be added
    // #TODO Integration tests will be added
    // #TODO Load tests will be added

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
            existingDto = dbService.findByUUId(uuid);
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


    @Retryable(maxAttempts = 3, backoff=@Backoff(delay=100, maxDelay=1000), retryFor = Exception.class)
    public SaveRequestDto save(SaveRequestDto dto) {

        /**
         * Dto will be saved by the cache first if it fails, it throws a RunTimeException
         * if this fail process continues 3 times in a row than @Recover will be activated
         * and will save the data to our DB
         */

        return cacheService.cacheableSave(dto);
    }

    @Recover
    public String fallbackSave(SaveRequestDto dto){
        return dbService.save(dto);
    }

    @Retryable(maxAttempts = 3, backoff=@Backoff(delay=100, maxDelay=1000), retryFor = HazelCastServiceException.class)
    public SaveRequestDto saveWithTTL(SaveRequestDto dto) {

        /**
         * Dto will be saved by the cache first if it fails, it throws a HazelCastServiceException
         * if this fail process continues 3 times in a row than @Recover will be activated
         * and will save the data to our DB
         */

        return cacheService.hazelCastSave(dto);
    }

}
