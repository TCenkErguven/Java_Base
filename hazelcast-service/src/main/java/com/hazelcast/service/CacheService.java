package com.hazelcast.service;

import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceSaveException;
import com.hazelcast.model.Custom;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final CacheManager cacheManager;
    private final JdbcService customService;

    public CacheService(CacheManager cacheManager, JdbcService customService){
        this.cacheManager = cacheManager;
        this.customService = customService;
    }

    public Cache getCache(String cacheName){
        try{
            return cacheManager.getCache(cacheName);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Saves data with spring cacheManger without TTL because Spring cache manager
     * doesn't have any TTL configuration entities
     * @param custom
     * @return
     */
    @Cacheable(value = "custom-entity", key = "#custom.transactionUUID")
    public Custom cacheableSave(Custom custom) {
        try {
            return customService.save(custom);
        } catch (Exception e) {
            throw new HazelCastServiceSaveException(ErrorType.INTERNAL_ERROR);
        }
    }

    @Cacheable(value = "custom-entity", key = "#custom.transactionUUID")
    public Custom cacheableFindByUUID(String uuid){
        try{
            return customService.findByUUId(uuid);
        }catch (Exception e){
            throw new HazelCastServiceSaveException(ErrorType.INTERNAL_ERROR);
        }
    }

    @CachePut(value = "custom-entity", key = "#custom.transactionUUID")
    public synchronized Custom cacheableUpdate(Custom custom){
        try {
            customService.update(custom);
            return custom;
        } catch (Exception e) {
            throw new HazelCastServiceSaveException(ErrorType.INTERNAL_ERROR);
        }
    }

}
