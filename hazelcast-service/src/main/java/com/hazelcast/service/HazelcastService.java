package com.hazelcast.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.dto.SaveRequestDto;
import com.hazelcast.server.proto.SaveRequest;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.stereotype.Service;

@Service
public class HazelcastService {
    private final CustomService customService;
    private final CacheService cacheService;
    private final ObjectMapper objectMapper;
    private final HazelcastInstance  cacheManager;

    public HazelcastService(CustomService customService,
                            CacheService cacheService,
                            ObjectMapper objectMapper){
        this.customService = customService;
        this.cacheService = cacheService;
        this.objectMapper = objectMapper;
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

    public SaveRequestDto save(SaveRequest dto) throws JsonProcessingException {
        SaveRequestDto existingDto = new SaveRequestDto();
        existingDto.setMessage(objectMapper.writeValueAsString(dto.getCustom()));
        existingDto.setUuid(dto.getUuid());
        try {
            return cacheService.cacheableSave(existingDto);
        } catch (Exception e) {
            return customService.save(existingDto);
        }
    }

}
