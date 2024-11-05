package org.example.service;

import org.example.dto.SaveRequestDto;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager){
        this.cacheManager = cacheManager;
    }

    public Cache getCache(String cacheName){
        try{
            Cache cache = cacheManager.getCache(cacheName);
            return cache;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    @Cacheable(value = "save-dto", key = "#dto.id", sync = true)
    public SaveRequestDto cacheableSave(SaveRequestDto dto) {
        try {
            Thread.sleep(5000);
            return dto;
        } catch (InterruptedException e) {
            System.out.println(e);
            throw new RuntimeException("Error during processing", e);
        }
    }

}
