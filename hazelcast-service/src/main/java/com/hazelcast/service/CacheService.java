package com.hazelcast.service;

import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.exception.ErrorType;
import com.hazelcast.exception.HazelCastServiceException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final CustomService customService;

    public CacheService(CustomService customService){
        this.customService = customService;
    }

    @Cacheable(value = "save-dto", key = "#dto.uuid")
    public SaveRequestDto cacheableSave(SaveRequestDto dto) {
        try {
            customService.save(dto);
            return dto;
        } catch (Exception e) {
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
    }

    @Cacheable(value = "save-dto",  key = "#dto.uuid")
    public SaveRequestDto cacheableFindByUUID(String uuid){
        try {
            return customService.findByUUId(uuid);
        } catch (Exception e) {
            throw new HazelCastServiceException(ErrorType.INTERNAL_ERROR);
        }
    }

}
