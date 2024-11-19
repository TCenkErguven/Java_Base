package com.hazelcast.service;

import com.hazelcast.dto.SaveRequestDto;
import org.springframework.stereotype.Service;

@Service
public class HazelcastService {
    private final CustomService customService;
    private final CacheService cacheService;

    public HazelcastService(CustomService customService, CacheService cacheService){
        this.customService = customService;
        this.cacheService = cacheService;
    }

    // #TODO Logger will be added instead of system.out and Impl file configuration will be added soon...
    // #TODO Unit test will be added
    // #TODO Integration tests will be added
    // #TODO Load tests will be added
    // #TODO CacheManager null ise hazelcast-client ı initialize dene

    public SaveRequestDto findResponseByUUID(String uuid){
        SaveRequestDto existingDto = null;
        try{
            existingDto = cacheService.cacheableFindByUUID(uuid);
        }catch (Exception e){
            existingDto = customService.findByUUId(uuid);
        }

        return existingDto;
    }

    public SaveRequestDto save(SaveRequestDto dto) {
        try {
            return cacheService.cacheableSave(dto);
        } catch (Exception e) {
            return customService.save(dto);
        }
    }

}
