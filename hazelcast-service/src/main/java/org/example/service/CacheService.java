package org.example.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.example.dto.SaveRequestDto;
import org.example.exception.ErrorType;
import org.example.exception.HazelCastServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    private final CacheManager cacheManager;
    private final String cacheName;
    private final HazelcastInstance hazelcastInstance;

    public CacheService(CacheManager cacheManager, HazelcastInstance hazelcastInstance, @Value("${cache.cache-name}") String cacheName){
        this.cacheManager = cacheManager;
        this.cacheName = cacheName;
        this.hazelcastInstance = hazelcastInstance;
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
     * @param dto
     * @return
     */
    @Cacheable(value = "save-dto", key = "#dto.id", sync = true)
    public SaveRequestDto cacheableSave(SaveRequestDto dto) {
        try {
            Thread.sleep(5000);
            return dto;
        } catch (Exception e) {
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
    }

    @Scheduled(fixedRate = 600000)
    @CacheEvict(value ="save-dto", allEntries = true)
    public void evictHazelCastDtoCache() {
        System.out.println("Caches cleared");
    }

    /**
     * This method use IMap interface from Hazelcast and put the data if
     * there is no existing value there
     * @param dto
     * @return
     */
    public synchronized SaveRequestDto hazelCastSave(SaveRequestDto dto){
        try{
            IMap<String, SaveRequestDto> map = hazelcastInstance.getMap(cacheName);
            map.putIfAbsent(dto.getId(),dto,10000, TimeUnit.SECONDS);
            return dto;
        } catch (Exception e){
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }

    }

}
