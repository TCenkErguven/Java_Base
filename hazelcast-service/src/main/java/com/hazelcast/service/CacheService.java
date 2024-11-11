package com.hazelcast.service;

import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final CacheManager cacheManager;
    private final CustomService customService;

    public CacheService(CacheManager cacheManager, CustomService customService){
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
     * @param dto
     * @return
     */
    @Cacheable(value = "save-dto", key = "#dto.uuid", sync = true)
    public SaveRequestDto cacheableSave(SaveRequestDto dto) {
        try {
            customService.save(dto);
            Thread.sleep(5000);
            return dto;
        } catch (Exception e) {
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
    }

    @Cacheable(value = "save-dto", key = "#dto.uuid", sync = true)
    public SaveRequestDto cacheableSaveWithDB(SaveRequestDto dto) {
        try {
            Thread.sleep(5000);
            return dto;
        } catch (Exception e) {
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
    }

    /*
    @Scheduled(fixedRate = 600000)
    @CacheEvict(value ="save-dto", allEntries = true)
    public void evictHazelCastDtoCache() {
        System.out.println("Caches cleared");
    }
    */
    /**
     * This method use IMap interface from Hazelcast and put the data if
     * there is no existing value there
     * @param dto
     * @return
     */
    /*
    public synchronized SaveRequestDto hazelCastSave(SaveRequestDto dto){
        try{
            IMap<String, SaveRequestDto> map = hazelcastInstance.getMap(cacheName);
            map.putIfAbsent(dto.getId(),dto,10000, TimeUnit.SECONDS);
            return dto;
        } catch (Exception e){
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
    }
     */

}
